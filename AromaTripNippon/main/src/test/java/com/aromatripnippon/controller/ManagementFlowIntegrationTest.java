package com.aromatripnippon.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.aromatripnippon.AromaTripNipponApplication;
import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.ExperienceProgram;
import com.aromatripnippon.entity.FragranceRecipe;
import com.aromatripnippon.entity.FragranceRecipeMaterial;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.entity.Product;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.FragranceRecipeMaterialRepository;
import com.aromatripnippon.repository.FragranceRecipeRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import com.aromatripnippon.repository.InventoryTransactionRepository;
import com.aromatripnippon.repository.ProductCategoryRepository;
import com.aromatripnippon.repository.ProductRepository;
import com.aromatripnippon.repository.ReservationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.servlet.http.HttpSession;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(
        classes = AromaTripNipponApplication.class,
        properties = "app.data-initializer.enabled=true")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ManagementFlowIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdminUserRepository admins;
    @Autowired
    private CustomerRepository customers;
    @Autowired
    private ExperienceProgramRepository programs;
    @Autowired
    private ReservationRepository reservations;
    @Autowired
    private FragranceRecipeRepository recipes;
    @Autowired
    private FragranceRecipeMaterialRepository recipeMaterials;
    @Autowired
    private InventoryItemRepository inventoryItems;
    @Autowired
    private InventoryTransactionRepository inventoryTransactions;
    @Autowired
    private ProductCategoryRepository productCategories;
    @Autowired
    private ProductRepository products;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void managementPage_requiresAuthentication() throws Exception {
        mockMvc.perform(get("/management/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void dashboardStats_linkToEachManagementPage() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        mockMvc.perform(get("/management/dashboard").session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("href=\"/management/reservations\"")))
                .andExpect(content().string(Matchers.containsString("href=\"/management/customers\"")))
                .andExpect(content().string(Matchers.containsString("href=\"/management/recipes\"")))
                .andExpect(content().string(Matchers.containsString("href=\"/management/inventory\"")));
    }

    @Test
    void reservationList_canDeleteAndMarksExpiredRows() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        Customer customer = new Customer();
        customer.setName("Expired Reservation Customer");
        customer.setEmail("expired.reservation@example.com");
        customer = customers.save(customer);

        ExperienceProgram program = programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElseGet(() -> {
            ExperienceProgram seed = new ExperienceProgram();
            seed.setName("Expired Reservation Program");
            seed.setDescription("desc");
            seed.setDurationMinutes(60);
            seed.setPrice(new BigDecimal("3000"));
            seed.setMaterialsSummary("summary");
            seed.setActive(true);
            return programs.save(seed);
        });

        String note = "expired row for list test";
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update("""
                insert into reservations
                  (customer_id, experience_program_id, reservation_date, reservation_time, number_of_people,
                   preferred_language, request_note, status, created_at, updated_at)
                values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, customer.getId(), program.getId(), LocalDate.now().minusDays(1), "09:00", 2,
                "Japanese", note, "RESERVED", now, now);

        Reservation expiredReservation = reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc().stream()
                .filter(r -> note.equals(r.getRequestNote()))
                .findFirst()
                .orElseThrow();

        mockMvc.perform(get("/management/reservations").session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("reservation-expired")))
                .andExpect(content().string(Matchers.containsString(
                        "/management/reservations/" + expiredReservation.getId() + "/delete")))
                .andExpect(content().string(Matchers.containsString("削除")));

        mockMvc.perform(post("/management/reservations/{id}/delete", expiredReservation.getId())
                .session(authSession).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/reservations"));
        assertThat(reservations.findByIdAndDeletedAtIsNull(expiredReservation.getId())).isEmpty();
    }

    @Test
    void managementLists_canDeleteAndBlockParentDeletesWhenRelatedDataRemains() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        Customer deletableCustomer = new Customer();
        deletableCustomer.setName("Deletable Customer");
        deletableCustomer.setEmail("deletable.customer@example.com");
        deletableCustomer = customers.save(deletableCustomer);

        Customer guardedCustomer = new Customer();
        guardedCustomer.setName("Guarded Customer");
        guardedCustomer.setEmail("guarded.customer@example.com");
        guardedCustomer = customers.save(guardedCustomer);

        ExperienceProgram program = programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElseGet(() -> {
            ExperienceProgram seed = new ExperienceProgram();
            seed.setName("Delete Guard Program");
            seed.setDescription("desc");
            seed.setDurationMinutes(60);
            seed.setPrice(new BigDecimal("3000"));
            seed.setMaterialsSummary("summary");
            seed.setActive(true);
            return programs.save(seed);
        });

        Reservation reservation = new Reservation();
        reservation.setCustomer(guardedCustomer);
        reservation.setExperienceProgram(program);
        reservation.setVisitDate(LocalDate.now().plusDays(2));
        reservation.setTimeSlot("11:00");
        reservation.setGuestCount(2);
        reservation.setStatus("RESERVED");
        reservations.save(reservation);

        InventoryItem usedItem = new InventoryItem();
        usedItem.setItemName("Recipe Used Item");
        usedItem.setCategory("material");
        usedItem.setStockQuantity(new BigDecimal("20"));
        usedItem.setUnit("ml");
        usedItem = inventoryItems.save(usedItem);

        Product guardedProduct = new Product();
        guardedProduct.setProductName("Guarded Product");
        guardedProduct.setCategory(productCategories.findByCategoryNameAndDeletedAtIsNull("素材").orElseThrow());
        guardedProduct.setPrice(new BigDecimal("1200"));
        guardedProduct = products.save(guardedProduct);

        FragranceRecipe guardedRecipe = new FragranceRecipe();
        guardedRecipe.setCustomer(guardedCustomer);
        guardedRecipe.setRecipeName("Guarded Recipe");
        guardedRecipe = recipes.save(guardedRecipe);

        FragranceRecipeMaterial material = new FragranceRecipeMaterial();
        material.setFragranceRecipe(guardedRecipe);
        material.setInventoryItem(usedItem);
        material.setBlendRatio(new BigDecimal("100"));
        material.setAmount(BigDecimal.ONE);
        recipeMaterials.save(material);

        mockMvc.perform(get("/management/customers").session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(
                        "/management/customers/" + deletableCustomer.getId() + "/delete")));
        mockMvc.perform(get("/management/products").session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(
                        "/management/products/" + guardedProduct.getId() + "/delete")));
        mockMvc.perform(get("/management/recipes").session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(
                        "/management/recipes/" + guardedRecipe.getId() + "/delete")));

        mockMvc.perform(post("/management/customers/{id}/delete", guardedCustomer.getId())
                .session(authSession).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/customers"))
                .andExpect(flash().attribute("errorMessage", "予約または香りレシピが残っている顧客は削除できません。"));
        assertThat(customers.findByIdAndDeletedAtIsNull(guardedCustomer.getId())).isPresent();

        mockMvc.perform(post("/management/products/{id}/delete", guardedProduct.getId())
                .session(authSession).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/products"))
                .andExpect(flash().attribute("errorMessage", "香りレシピに使われている商品は削除できません。"));
        assertThat(products.findByIdAndDeletedAtIsNull(guardedProduct.getId())).isPresent();

        mockMvc.perform(post("/management/recipes/{id}/delete", guardedRecipe.getId())
                .session(authSession).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/recipes"));
        assertThat(recipes.findByIdAndDeletedAtIsNull(guardedRecipe.getId())).isEmpty();

        mockMvc.perform(post("/management/customers/{id}/delete", deletableCustomer.getId())
                .session(authSession).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/customers"));
        assertThat(customers.findByIdAndDeletedAtIsNull(deletableCustomer.getId())).isEmpty();
    }

    @Test
    void reservationList_rendersAndSeedLucasCustomerCannotBeDeletedWhenRecipeExists() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        mockMvc.perform(get("/management/reservations").session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("予約管理")));

        Customer lucas = customers.findByDeletedAtIsNullOrderByIdDesc().stream()
                .filter(c -> "lucas.smith@example.com".equalsIgnoreCase(c.getEmail()))
                .findFirst()
                .orElseThrow();
        assertThat(recipes.existsActiveByCustomerId(lucas.getId())).isTrue();

        mockMvc.perform(post("/management/customers/{id}/delete", lucas.getId())
                .session(authSession).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/customers"))
                .andExpect(flash().attribute("errorMessage", "予約または香りレシピが残っている顧客は削除できません。"));
        assertThat(customers.findByIdAndDeletedAtIsNull(lucas.getId())).isPresent();
    }

    @Test
    void managementCrudAndInventoryHistory_workAfterLogin() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/dashboard"))
                .andReturn();
        HttpSession session = loginResult.getRequest().getSession(false);
        assertThat(session).isNotNull();
        MockHttpSession authSession = (MockHttpSession) session;

        Customer customer = new Customer();
        customer.setName("Mgmt Customer");
        customer.setEmail("mgmt.customer@example.com");
        customer = customers.save(customer);

        ExperienceProgram program = programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElseGet(() -> {
            ExperienceProgram seed = new ExperienceProgram();
            seed.setName("Mgmt Program");
            seed.setDescription("desc");
            seed.setDurationMinutes(60);
            seed.setPrice(new BigDecimal("3000"));
            seed.setMaterialsSummary("summary");
            seed.setActive(true);
            return programs.save(seed);
        });

        mockMvc.perform(post("/management/reservations").session(authSession).with(csrf())
                .param("customerId", customer.getId().toString())
                .param("programId", program.getId().toString())
                .param("visitDate", LocalDate.now().plusDays(1).toString())
                .param("timeSlot", "10:00")
                .param("guestCount", "2")
                .param("preferredLanguage", "English")
                .param("requestNote", "mgmt")
                .param("status", "RESERVED"))
                .andExpect(status().is3xxRedirection());

        Reservation savedReservation = reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc().stream()
                .filter(r -> "mgmt".equals(r.getRequestNote()))
                .findFirst()
                .orElseThrow();
        assertThat(savedReservation.getCustomer().getId()).isEqualTo(customer.getId());

        mockMvc.perform(post("/management/customers/{id}", customer.getId()).session(authSession).with(csrf())
                .param("name", "Mgmt Customer Updated")
                .param("email", "mgmt.customer.updated@example.com")
                .param("phone", "03-1111-2222")
                .param("nationality", "Japan")
                .param("preferredLanguage", "Japanese")
                .param("purpose", "Repeat visit")
                .param("notes", "updated by integration test"))
                .andExpect(status().is3xxRedirection());
        Customer updatedCustomer = customers.findByIdAndDeletedAtIsNull(customer.getId()).orElseThrow();
        assertThat(updatedCustomer.getName()).isEqualTo("Mgmt Customer Updated");
        assertThat(updatedCustomer.getEmail()).isEqualTo("mgmt.customer.updated@example.com");

        FragranceRecipe recipe = new FragranceRecipe();
        recipe.setCustomer(updatedCustomer);
        recipe.setRecipeName("Initial Recipe");
        recipe.setConcept("initial");
        recipe.setMemo("initial");
        recipe.setTotalAmount(new BigDecimal("100.00"));
        recipe = recipes.save(recipe);

        InventoryItem item = new InventoryItem();
        item.setItemName("Mgmt Item");
        item.setCategory("material");
        item.setStockQuantity(new BigDecimal("20"));
        item.setUnit("ml");
        item.setThresholdQuantity(new BigDecimal("5"));
        item = inventoryItems.save(item);

        InventoryItem item2 = new InventoryItem();
        item2.setItemName("Mgmt Item 2");
        item2.setCategory("material");
        item2.setStockQuantity(new BigDecimal("20"));
        item2.setUnit("ml");
        item2.setThresholdQuantity(new BigDecimal("5"));
        item2 = inventoryItems.save(item2);

        InventoryItem item3 = new InventoryItem();
        item3.setItemName("Mgmt Item 3");
        item3.setCategory("material");
        item3.setStockQuantity(new BigDecimal("20"));
        item3.setUnit("ml");
        item3.setThresholdQuantity(new BigDecimal("5"));
        item3 = inventoryItems.save(item3);

        InventoryItem item4 = new InventoryItem();
        item4.setItemName("Mgmt Item 4");
        item4.setCategory("material");
        item4.setStockQuantity(new BigDecimal("20"));
        item4.setUnit("ml");
        item4.setThresholdQuantity(new BigDecimal("5"));
        item4 = inventoryItems.save(item4);

        InventoryItem item5 = new InventoryItem();
        item5.setItemName("Mgmt Item 5");
        item5.setCategory("material");
        item5.setStockQuantity(new BigDecimal("20"));
        item5.setUnit("ml");
        item5.setThresholdQuantity(new BigDecimal("5"));
        item5 = inventoryItems.save(item5);

        mockMvc.perform(post("/management/recipes/{id}", recipe.getId()).session(authSession).with(csrf())
                .param("customerId", updatedCustomer.getId().toString())
                .param("recipeName", "Updated Recipe Name")
                .param("concept", "updated concept")
                .param("memo", "updated memo")
                .param("materialId", item.getId().toString(), item2.getId().toString())
                .param("blendRatio", "60", "40"))
                .andExpect(status().is3xxRedirection());
        FragranceRecipe updatedRecipe = recipes.findByIdAndDeletedAtIsNull(recipe.getId()).orElseThrow();
        assertThat(updatedRecipe.getRecipeName()).isEqualTo("Updated Recipe Name");
        assertThat(updatedRecipe.getConcept()).isEqualTo("updated concept");
        assertThat(recipeMaterials.findByFragranceRecipeIdOrderByDisplayOrderAsc(recipe.getId())).hasSize(2);

        mockMvc.perform(post("/management/recipes").session(authSession).with(csrf())
                .param("customerId", updatedCustomer.getId().toString())
                .param("recipeName", "Five Materials Recipe")
                .param("concept", "multi material")
                .param("memo", "create with five materials")
                .param("materialId", item.getId().toString(), item2.getId().toString(), item3.getId().toString(),
                        item4.getId().toString(), item5.getId().toString())
                .param("blendRatio", "20", "20", "20", "20", "20"))
                .andExpect(status().is3xxRedirection());

        FragranceRecipe createdRecipe = recipes.findByDeletedAtIsNullAndRecipeNameContainingIgnoreCaseOrderByIdDesc(
                "Five Materials Recipe")
                .stream()
                .findFirst()
                .orElseThrow();
        assertThat(recipeMaterials.findByFragranceRecipeIdOrderByDisplayOrderAsc(createdRecipe.getId())).hasSize(5);

        mockMvc.perform(post("/management/inventory/{id}", item.getId()).session(authSession).with(csrf())
                .param("itemName", "Mgmt Item Updated")
                .param("category", "material")
                .param("stockQuantity", "25")
                .param("unit", "ml")
                .param("thresholdQuantity", "6")
                .param("storageLocation", "Rack B")
                .param("supplier", "Supplier A")
                .param("lastReceivedDate", LocalDate.now().toString())
                .param("memo", "updated inventory"))
                .andExpect(status().is3xxRedirection());
        InventoryItem updatedItem = inventoryItems.findByIdAndDeletedAtIsNull(item.getId()).orElseThrow();
        assertThat(updatedItem.getItemName()).isEqualTo("Mgmt Item Updated");
        assertThat(updatedItem.getStockQuantity()).isEqualByComparingTo(new BigDecimal("25"));

        mockMvc.perform(post("/management/inventory/{id}/transaction", item.getId()).session(authSession).with(csrf())
                .param("transactionType", "IN")
                .param("quantity", "5")
                .param("reason", "test"))
                .andExpect(status().is3xxRedirection());
        assertThat(inventoryTransactions
                .findByDeletedAtIsNullAndInventoryItemIdOrderByTransactionDateDescIdDesc(item.getId()))
                .isNotEmpty();

        Product product = new Product();
        product.setProductName("Mgmt Product");
        product.setCategory(productCategories.findByCategoryNameAndDeletedAtIsNull("製品").orElseThrow());
        product.setPrice(new BigDecimal("1000"));
        product.setDescription("desc");
        product.setActive(true);
        products.save(product);

        mockMvc.perform(post("/management/products/{id}", product.getId()).session(authSession).with(csrf())
                .param("productName", "Mgmt Product Updated")
                .param("categoryId", productCategories.findByCategoryNameAndDeletedAtIsNull("製品").orElseThrow()
                        .getId().toString())
                .param("price", "1200")
                .param("description", "updated")
                .param("published", "true"))
                .andExpect(status().is3xxRedirection());
        assertThat(products.findByIdAndDeletedAtIsNull(product.getId()).orElseThrow().getProductName())
                .isEqualTo("Mgmt Product Updated");
        assertThat(products.findByIdAndDeletedAtIsNull(product.getId()).orElseThrow().getCategory().getCategoryName())
                .isEqualTo("製品");
    }

    @Test
    void productForm_usesProductCategoryMasterOptions() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        assertThat(productCategories.findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc())
                .extracting("categoryName")
                .containsExactly("製品", "素材", "容器");

        mockMvc.perform(get("/management/products/new").session(authSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(content().string(Matchers.containsString("<select")))
                .andExpect(content().string(Matchers.containsString("name=\"categoryId\"")))
                .andExpect(content().string(Matchers.containsString("製品")))
                .andExpect(content().string(Matchers.containsString("素材")))
                .andExpect(content().string(Matchers.containsString("容器")));
    }

    @Test
    void recipeUpdate_rejectsWhenBlendRatioTotalIsNot100() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        Customer customer = new Customer();
        customer.setName("Ratio Customer");
        customer.setEmail("ratio.customer@example.com");
        customer = customers.save(customer);

        InventoryItem item = new InventoryItem();
        item.setItemName("Ratio Item");
        item.setCategory("material");
        item.setStockQuantity(new BigDecimal("20"));
        item.setUnit("ml");
        item.setThresholdQuantity(new BigDecimal("5"));
        item = inventoryItems.save(item);

        FragranceRecipe recipe = new FragranceRecipe();
        recipe.setCustomer(customer);
        recipe.setRecipeName("Ratio Recipe");
        recipe.setConcept("ratio");
        recipe.setMemo("ratio");
        recipe = recipes.save(recipe);

        mockMvc.perform(post("/management/recipes/{id}", recipe.getId()).session(authSession).with(csrf())
                .param("customerId", customer.getId().toString())
                .param("recipeName", "Ratio Recipe Updated")
                .param("concept", "ratio")
                .param("memo", "ratio")
                .param("materialId", item.getId().toString())
                .param("blendRatio", "90"))
                .andExpect(status().isOk())
                .andExpect(view().name("management/recipe-form"))
                .andExpect(model().attribute("errorMessage", "配合率は合計１００になるようにしてください"))
                .andExpect(model().attributeExists("materialIds"))
                .andExpect(model().attributeExists("blendRatios"));
    }

    @Test
    void managementAction_missingEntity_redirectsWithErrorMessage() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        mockMvc.perform(post("/management/products/{id}", 999999L).session(authSession).with(csrf())
                .param("productName", "missing")
                .param("categoryId", productCategories.findByCategoryNameAndDeletedAtIsNull("製品").orElseThrow()
                        .getId().toString())
                .param("price", "1200")
                .param("description", "updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/management/dashboard"))
                .andExpect(flash().attribute("errorMessage",
                        "対象データが見つかりませんでした。画面を更新して、一覧から再度操作してください。"));
    }

    @Test
    void sessionTimeout_afterInvalidation_requiresRelogin() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        authSession.invalidate();

        mockMvc.perform(get("/management/dashboard").session(new MockHttpSession()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void postWithoutCsrf_isRejected() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        mockMvc.perform(post("/management/products").session(authSession)
                .param("productName", "NoCsrf Product")
                .param("category", "製品")
                .param("price", "1000")
                .param("description", "no csrf"))
                .andExpect(status().isForbidden());
    }

    @Test
    void xssPayload_isEscapedOnRender() throws Exception {
        MvcResult loginResult = mockMvc.perform(formLogin("/management/login")
                .user("AromaTripAdm01")
                .password("password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpSession authSession = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertThat(authSession).isNotNull();

        String payload = "<script>alert('x')</script>";
        Customer customer = new Customer();
        customer.setName(payload);
        customer.setEmail("xss.customer@example.com");
        customer = customers.save(customer);

        mockMvc.perform(get("/management/customers/{id}", customer.getId()).session(authSession))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(Matchers.containsString(payload))))
                .andExpect(content().string(Matchers.containsString("&lt;script&gt;alert")));
    }
}
