package com.aromatripnippon.controller;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.FragranceRecipe;
import com.aromatripnippon.entity.FragranceRecipeMaterial;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.entity.InventoryTransaction;
import com.aromatripnippon.entity.Product;
import com.aromatripnippon.entity.ProductCategory;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.AuditLogRepository;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.FragranceRecipeMaterialRepository;
import com.aromatripnippon.repository.FragranceRecipeRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import com.aromatripnippon.repository.InventoryTransactionRepository;
import com.aromatripnippon.repository.ProductCategoryRepository;
import com.aromatripnippon.repository.ProductRepository;
import com.aromatripnippon.repository.ReservationRepository;
import com.aromatripnippon.service.AuditService;
import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ManagementController {
  private final ReservationRepository reservations;
  private final CustomerRepository customers;
  private final ExperienceProgramRepository programs;
  private final FragranceRecipeRepository recipes;
  private final FragranceRecipeMaterialRepository recipeMaterials;
  private final InventoryItemRepository inventory;
  private final InventoryTransactionRepository inventoryTransactions;
  private final ProductCategoryRepository productCategories;
  private final ProductRepository products;
  private final AdminUserRepository admins;
  private final AuditLogRepository auditLogs;
  private final AuditService audit;
  private final PasswordEncoder encoder;

  public ManagementController(ReservationRepository reservations, CustomerRepository customers,
      ExperienceProgramRepository programs, FragranceRecipeRepository recipes,
      FragranceRecipeMaterialRepository recipeMaterials, InventoryItemRepository inventory,
      InventoryTransactionRepository inventoryTransactions, ProductCategoryRepository productCategories,
      ProductRepository products, AdminUserRepository admins, AuditLogRepository auditLogs, AuditService audit,
      PasswordEncoder encoder) {
    this.reservations = reservations;
    this.customers = customers;
    this.programs = programs;
    this.recipes = recipes;
    this.recipeMaterials = recipeMaterials;
    this.inventory = inventory;
    this.inventoryTransactions = inventoryTransactions;
    this.productCategories = productCategories;
    this.products = products;
    this.admins = admins;
    this.auditLogs = auditLogs;
    this.audit = audit;
    this.encoder = encoder;
  }

  @GetMapping("/management/dashboard")
  public String dashboard(Model model) {
    model.addAttribute("reservationCount", reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc().size());
    model.addAttribute("customerCount", customers.findByDeletedAtIsNullOrderByIdDesc().size());
    model.addAttribute("recipeCount", recipes.findByDeletedAtIsNullOrderByIdDesc().size());
    model.addAttribute("inventoryCount", inventory.findByDeletedAtIsNullOrderByIdDesc().size());
    model.addAttribute("logs", auditLogs.findByDeletedAtIsNullOrderByIdDesc().stream().limit(5).toList());
    return "management/dashboard";
  }

  @GetMapping("/management/reservations")
  public String reservationList(@RequestParam(required = false) String q, Model model) {
    model.addAttribute("reservations", q == null || q.isBlank()
        ? reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc()
        : reservations.findByDeletedAtIsNullAndCustomerNameContainingIgnoreCaseOrderByVisitDateDescTimeSlotAsc(q));
    model.addAttribute("today", LocalDate.now());
    model.addAttribute("q", q);
    return "management/reservation-list";
  }

  @GetMapping("/management/reservations/new")
  public String reservationNew(Model model) {
    populateReservationFormModel(model, new Reservation());
    return "management/reservation-form";
  }

  @PostMapping("/management/reservations")
  public String reservationCreate(@Valid @ModelAttribute Reservation reservation, BindingResult errors,
      @RequestParam Long customerId, @RequestParam Long programId, Principal principal, Model model) {
    if (errors.hasErrors()) {
      populateReservationFormModel(model, reservation);
      return "management/reservation-form";
    }
    reservation.setCustomer(customers.findById(customerId).orElseThrow());
    reservation.setExperienceProgram(programs.findById(programId).orElseThrow());
    Reservation saved = reservations.save(reservation);
    audit.record(principal, "CREATE", "reservations", saved.getId(), "予約を作成");
    return "redirect:/management/reservations/" + saved.getId();
  }

  @GetMapping("/management/reservations/{id}")
  public String reservationDetail(@PathVariable Long id, Model model) {
    model.addAttribute("reservation", reservations.findById(id).orElseThrow());
    return "management/reservation-detail";
  }

  @GetMapping("/management/reservations/{id}/edit")
  public String reservationEdit(@PathVariable Long id, Model model) {
    populateReservationFormModel(model, reservations.findById(id).orElseThrow());
    return "management/reservation-form";
  }

  @PostMapping("/management/reservations/{id}")
  public String reservationUpdate(@PathVariable Long id, @Valid @ModelAttribute Reservation form, BindingResult errors,
      @RequestParam Long customerId, @RequestParam Long programId, Principal principal, Model model) {
    if (errors.hasErrors()) {
      populateReservationFormModel(model, form);
      return "management/reservation-form";
    }
    Reservation reservation = reservations.findById(id).orElseThrow();
    reservation.setVisitDate(form.getVisitDate());
    reservation.setTimeSlot(form.getTimeSlot());
    reservation.setGuestCount(form.getGuestCount());
    reservation.setPreferredLanguage(form.getPreferredLanguage());
    reservation.setRequestNote(form.getRequestNote());
    reservation.setStatus(form.getStatus());
    reservation.setCustomer(customers.findById(customerId).orElseThrow());
    reservation.setExperienceProgram(programs.findById(programId).orElseThrow());
    reservations.save(reservation);
    audit.record(principal, "UPDATE", "reservations", id, "予約を更新");
    return "redirect:/management/reservations/" + id;
  }

  @PostMapping("/management/reservations/{id}/delete")
  @Transactional
  public String reservationDelete(@PathVariable Long id, Principal principal) {
    Reservation reservation = reservations.findById(id).orElseThrow();
    reservations.softDeleteById(id, LocalDateTime.now());
    audit.record(principal, "DELETE", "reservations", id, "予約を削除");
    return "redirect:/management/reservations";
  }

  @GetMapping("/management/customers")
  public String customerList(@RequestParam(required = false) String q, Model model) {
    model.addAttribute("customers", q == null || q.isBlank()
        ? customers.findByDeletedAtIsNullOrderByIdDesc()
        : customers.findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByIdDesc(q));
    model.addAttribute("q", q);
    return "management/customer-list";
  }

  @GetMapping("/management/customers/new")
  public String customerNew(Model model) {
    model.addAttribute("customer", new Customer());
    return "management/customer-form";
  }

  @PostMapping("/management/customers")
  public String customerCreate(@Valid @ModelAttribute Customer customer, BindingResult errors, Principal principal) {
    if (errors.hasErrors()) {
      return "management/customer-form";
    }
    Customer saved = customers.save(customer);
    audit.record(principal, "CREATE", "customers", saved.getId(), "顧客を作成");
    return "redirect:/management/customers/" + saved.getId();
  }

  @GetMapping("/management/customers/{id}")
  public String customerDetail(@PathVariable Long id, Model model) {
    model.addAttribute("customer", customers.findById(id).orElseThrow());
    return "management/customer-detail";
  }

  @GetMapping("/management/customers/{id}/edit")
  public String customerEdit(@PathVariable Long id, Model model) {
    model.addAttribute("customer", customers.findById(id).orElseThrow());
    return "management/customer-form";
  }

  @PostMapping("/management/customers/{id}")
  public String customerUpdate(@PathVariable Long id, @Valid @ModelAttribute Customer form, BindingResult errors,
      Principal principal) {
    if (errors.hasErrors()) {
      return "management/customer-form";
    }
    Customer customer = customers.findById(id).orElseThrow();
    customer.setName(form.getName());
    customer.setEmail(form.getEmail());
    customer.setPhone(form.getPhone());
    customer.setNationality(form.getNationality());
    customer.setPreferredLanguage(form.getPreferredLanguage());
    customer.setPurpose(form.getPurpose());
    customer.setNotes(form.getNotes());
    customers.save(customer);
    audit.record(principal, "UPDATE", "customers", id, "顧客を更新");
    return "redirect:/management/customers/" + id;
  }

  @PostMapping("/management/customers/{id}/delete")
  public String customerDelete(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
    Customer customer = customers.findById(id).orElseThrow();
    if (reservations.existsActiveByCustomerId(id)
        || recipes.existsActiveByCustomerId(id)) {
      redirectAttributes.addFlashAttribute("errorMessage", "予約または香りレシピが残っている顧客は削除できません。");
      return "redirect:/management/customers";
    }
    customer.softDelete();
    customers.save(customer);
    audit.record(principal, "DELETE", "customers", id, "顧客を削除");
    return "redirect:/management/customers";
  }

  @GetMapping("/management/recipes")
  public String recipeList(@RequestParam(required = false) String q, Model model) {
    model.addAttribute("recipes", q == null || q.isBlank()
        ? recipes.findByDeletedAtIsNullOrderByIdDesc()
        : recipes.findByDeletedAtIsNullAndRecipeNameContainingIgnoreCaseOrderByIdDesc(q));
    model.addAttribute("q", q);
    return "management/recipe-list";
  }

  @GetMapping("/management/recipes/new")
  public String recipeNew(Model model) {
    model.addAttribute("recipe", new FragranceRecipe());
    model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("productOptions", recipeProductOptions());
    return "management/recipe-form";
  }

  @PostMapping("/management/recipes")
  @Transactional
  public String recipeCreate(@Valid @ModelAttribute FragranceRecipe recipe, BindingResult errors,
      @RequestParam Long customerId,
      @RequestParam(name = "materialId", required = false) List<Long> materialIds,
      @RequestParam(name = "blendRatio", required = false) List<BigDecimal> blendRatios, Principal principal,
      RedirectAttributes redirectAttributes, Model model) {
    if (errors.hasErrors()) {
      model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
      model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
      model.addAttribute("productOptions", recipeProductOptions());
      model.addAttribute("materialIds", materialIds);
      model.addAttribute("blendRatios", blendRatios);
      return "management/recipe-form";
    }
    recipe.setCustomer(customers.findById(customerId).orElseThrow());
    try {
      recipe.getMaterials().addAll(buildRecipeMaterials(recipe, materialIds, blendRatios));
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      return "redirect:/management/recipes/new";
    }
    FragranceRecipe saved = recipes.save(recipe);
    audit.record(principal, "CREATE", "fragrance_recipes", saved.getId(), "香りレシピを作成");
    return "redirect:/management/recipes/" + saved.getId();
  }

  @GetMapping("/management/recipes/{id}")
  public String recipeDetail(@PathVariable Long id, Model model) {
    model.addAttribute("recipe", recipes.findById(id).orElseThrow());
    return "management/recipe-detail";
  }

  @GetMapping("/management/recipes/{id}/edit")
  public String recipeEdit(@PathVariable Long id, Model model) {
    model.addAttribute("recipe", recipes.findById(id).orElseThrow());
    model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("productOptions", recipeProductOptions());
    return "management/recipe-form";
  }

  @PostMapping("/management/recipes/{id}")
  @Transactional
  public String recipeUpdate(@PathVariable Long id, @Valid @ModelAttribute FragranceRecipe form, BindingResult errors,
      @RequestParam Long customerId,
      @RequestParam(name = "materialId", required = false) List<Long> materialIds,
      @RequestParam(name = "blendRatio", required = false) List<BigDecimal> blendRatios, Principal principal,
      RedirectAttributes redirectAttributes, Model model) {
    FragranceRecipe recipe = recipes.findById(id).orElseThrow();
    if (errors.hasErrors()) {
      form.setCustomer(customers.findById(customerId).orElseThrow());
      model.addAttribute("recipe", form);
      model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
      model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
      model.addAttribute("productOptions", recipeProductOptions());
      model.addAttribute("materialIds", materialIds);
      model.addAttribute("blendRatios", blendRatios);
      return "management/recipe-form";
    }
    recipe.setRecipeName(form.getRecipeName());
    recipe.setConcept(form.getConcept());
    recipe.setMemo(form.getMemo());
    recipe.setCustomer(customers.findById(customerId).orElseThrow());
    List<FragranceRecipeMaterial> newMaterials;
    try {
      newMaterials = buildRecipeMaterials(recipe, materialIds, blendRatios);
    } catch (IllegalArgumentException ex) {
      model.addAttribute("errorMessage", ex.getMessage());
      model.addAttribute("recipe", recipe);
      model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
      model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
      model.addAttribute("productOptions", recipeProductOptions());
      model.addAttribute("materialIds", materialIds);
      model.addAttribute("blendRatios", blendRatios);
      return "management/recipe-form";
    }
    recipe.getMaterials().clear();
    recipe.getMaterials().addAll(newMaterials);
    recipes.save(recipe);
    audit.record(principal, "UPDATE", "fragrance_recipes", id, "香りレシピを更新");
    return "redirect:/management/recipes/" + id;
  }

  @PostMapping("/management/recipes/{id}/delete")
  public String recipeDelete(@PathVariable Long id, Principal principal) {
    FragranceRecipe recipe = recipes.findById(id).orElseThrow();
    recipe.softDelete();
    recipes.save(recipe);
    audit.record(principal, "DELETE", "fragrance_recipes", id, "香りレシピを削除");
    return "redirect:/management/recipes";
  }

  @GetMapping("/management/products")
  public String productList(@RequestParam(required = false) String q, Model model) {
    model.addAttribute("products", q == null || q.isBlank()
        ? products.findByDeletedAtIsNullOrderByIdDesc()
        : products.findByDeletedAtIsNullAndProductNameContainingIgnoreCaseOrderByIdDesc(q));
    model.addAttribute("q", q);
    return "management/product-list";
  }

  @GetMapping("/management/products/new")
  public String productNew(Model model) {
    model.addAttribute("product", new Product());
    model.addAttribute("categories", productCategories.findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc());
    return "management/product-form";
  }

  @PostMapping("/management/products")
  @Transactional
  public String productCreate(@Valid @ModelAttribute Product product, BindingResult errors,
      @RequestParam Long categoryId, Principal principal, Model model) {
    if (errors.hasErrors()) {
      model.addAttribute("categories", productCategories.findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc());
      return "management/product-form";
    }
    ProductCategory category = productCategories.findById(categoryId).orElseThrow();
    InventoryItem item = inventoryFromProduct(product, category);
    inventory.save(item);
    product.setCategory(category);
    Product saved = products.save(product);
    audit.record(principal, "CREATE", "products", saved.getId(), "商品を作成");
    return "redirect:/management/products/" + saved.getId();
  }

  @GetMapping("/management/products/{id}")
  public String productDetail(@PathVariable Long id, Model model) {
    model.addAttribute("product", products.findById(id).orElseThrow());
    return "management/product-detail";
  }

  @GetMapping("/management/products/{id}/edit")
  public String productEdit(@PathVariable Long id, Model model) {
    model.addAttribute("product", products.findById(id).orElseThrow());
    model.addAttribute("categories", productCategories.findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc());
    return "management/product-form";
  }

  @PostMapping("/management/products/{id}")
  @Transactional
  public String productUpdate(@PathVariable Long id, @Valid @ModelAttribute Product form, BindingResult errors,
      @RequestParam Long categoryId, Principal principal, Model model) {
    if (errors.hasErrors()) {
      model.addAttribute("categories", productCategories.findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc());
      return "management/product-form";
    }
    Product product = products.findById(id).orElseThrow();
    ProductCategory category = productCategories.findById(categoryId).orElseThrow();
    product.setProductName(form.getProductName());
    product.setEnglishName(form.getEnglishName());
    product.setCategory(category);
    product.setPrice(form.getPrice());
    product.setDescription(form.getDescription());
    product.setPublished(form.getPublished() != null && form.getPublished());
    InventoryItem item = inventory.findById(id).orElseGet(() -> inventoryFromProduct(product, category));
    item.setItemName(product.getProductName());
    item.setEnglishName(product.getEnglishName());
    item.setCategory(category.getCategoryName());
    inventory.save(item);
    products.save(product);
    audit.record(principal, "UPDATE", "products", id, "商品を更新");
    return "redirect:/management/products/" + id;
  }

  @PostMapping("/management/products/{id}/delete")
  public String productDelete(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
    Product product = products.findByIdAndDeletedAtIsNull(id).orElseThrow();
    if (recipeMaterials.existsByDeletedAtIsNullAndFragranceRecipeDeletedAtIsNullAndInventoryItemId(id)) {
      redirectAttributes.addFlashAttribute("errorMessage", "香りレシピに使われている商品は削除できません。");
      return "redirect:/management/products";
    }
    InventoryItem item = inventory.findByIdAndDeletedAtIsNull(id).orElse(null);
    if (item != null && item.getStockQuantity() != null && item.getStockQuantity().compareTo(BigDecimal.ZERO) > 0) {
      redirectAttributes.addFlashAttribute("errorMessage", "在庫が残っている商品は削除できません。先に在庫数を0にしてください。");
      return "redirect:/management/products";
    }
    product.softDelete();
    products.save(product);
    if (item != null) {
      item.softDelete();
      inventory.save(item);
    }
    audit.record(principal, "DELETE", "products", id, "商品を削除");
    if (item != null) {
      audit.record(principal, "DELETE", "inventory_items", id, "商品削除に伴い在庫を連動削除");
    }
    return "redirect:/management/products";
  }

  @GetMapping("/management/inventory")
  public String inventoryList(@RequestParam(required = false) String q, Model model) {
    model.addAttribute("items", q == null || q.isBlank()
        ? inventory.findByDeletedAtIsNullOrderByIdDesc()
        : inventory.findByDeletedAtIsNullAndItemNameContainingIgnoreCaseOrderByIdDesc(q));
    model.addAttribute("q", q);
    return "management/inventory-list";
  }

  @GetMapping("/management/inventory/new")
  public String inventoryNew(Model model) {
    model.addAttribute("item", new InventoryItem());
    return "management/inventory-form";
  }

  @PostMapping("/management/inventory")
  @Transactional
  public String inventoryCreate(@Valid @ModelAttribute InventoryItem item, BindingResult errors, Principal principal) {
    if (errors.hasErrors()) {
      return "management/inventory-form";
    }
    InventoryItem saved = inventory.save(item);
    syncProductFromInventory(saved);
    audit.record(principal, "CREATE", "inventory_items", saved.getId(), "在庫を作成");
    return "redirect:/management/inventory/" + saved.getId();
  }

  @GetMapping("/management/inventory/{id}")
  public String inventoryDetail(@PathVariable Long id, Model model) {
    model.addAttribute("item", inventory.findById(id).orElseThrow());
    model.addAttribute("transactions", inventoryTransactions.findByDeletedAtIsNullOrderByIdDesc());
    return "management/inventory-detail";
  }

  @GetMapping("/management/inventory/{id}/edit")
  public String inventoryEdit(@PathVariable Long id, Model model) {
    model.addAttribute("item", inventory.findById(id).orElseThrow());
    return "management/inventory-form";
  }

  @PostMapping("/management/inventory/{id}")
  @Transactional
  public String inventoryUpdate(@PathVariable Long id, @Valid @ModelAttribute InventoryItem form, BindingResult errors,
      Principal principal) {
    if (errors.hasErrors()) {
      return "management/inventory-form";
    }
    InventoryItem item = inventory.findById(id).orElseThrow();
    item.setItemName(form.getItemName());
    item.setEnglishName(form.getEnglishName());
    item.setCategory(form.getCategory());
    item.setStockQuantity(form.getStockQuantity());
    item.setUnit(form.getUnit());
    item.setThresholdQuantity(form.getThresholdQuantity());
    item.setStorageLocation(form.getStorageLocation());
    item.setSupplier(form.getSupplier());
    item.setLastReceivedDate(form.getLastReceivedDate());
    item.setMemo(form.getMemo());
    item = inventory.save(item);
    syncProductFromInventory(item);
    audit.record(principal, "UPDATE", "inventory_items", id, "在庫を更新");
    return "redirect:/management/inventory/" + id;
  }

  @PostMapping("/management/inventory/{id}/transaction")
  public String inventoryTransaction(@PathVariable Long id, @RequestParam String transactionType,
      @RequestParam BigDecimal quantity, @RequestParam(required = false) String reason, Principal principal) {
    InventoryItem item = inventory.findById(id).orElseThrow();
    BigDecimal signedQuantity = "OUT".equals(transactionType) ? quantity.negate() : quantity;
    item.setStockQuantity(item.getStockQuantity().add(signedQuantity));
    InventoryTransaction transaction = new InventoryTransaction();
    transaction.setInventoryItem(item);
    transaction.setTransactionType(transactionType);
    transaction.setQuantity(quantity);
    transaction.setReason(reason);
    inventoryTransactions.save(transaction);
    inventory.save(item);
    audit.record(principal, "UPDATE", "inventory_transactions", transaction.getId(), "在庫取引を更新");
    return "redirect:/management/inventory/" + id;
  }

  @PostMapping("/management/inventory/{id}/delete")
  public String inventoryDelete(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
    if (recipeMaterials.existsByDeletedAtIsNullAndFragranceRecipeDeletedAtIsNullAndInventoryItemId(id)) {
      redirectAttributes.addFlashAttribute("errorMessage", "香りレシピで使用中の在庫は削除できません。");
      return "redirect:/management/inventory";
    }
    InventoryItem item = inventory.findByIdAndDeletedAtIsNull(id).orElseThrow();
    item.softDelete();
    inventory.save(item);
    Product product = products.findByIdAndDeletedAtIsNull(id).orElse(null);
    if (product != null) {
      product.softDelete();
      products.save(product);
      audit.record(principal, "DELETE", "products", id, "在庫削除に伴い商品を連動削除");
    }
    audit.record(principal, "DELETE", "inventory_items", id, "在庫を論理削除");
    return "redirect:/management/inventory";
  }

  @GetMapping("/management/account")
  public String account(Principal principal, Model model) {
    model.addAttribute("admin", admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(principal.getName()).orElseThrow());
    return "management/account";
  }

  @PostMapping("/management/account")
  public String accountUpdate(@ModelAttribute AdminUser form, @RequestParam(required = false) String newPassword,
      Principal principal) {
    AdminUser admin = admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(principal.getName()).orElseThrow();
    admin.setDisplayName(form.getDisplayName());
    admin.setEmail(form.getEmail());
    admin.setNotificationSetting(form.getNotificationSetting());
    admin.setDisplayLanguage(form.getDisplayLanguage());
    if (newPassword != null && !newPassword.isBlank()) {
      admin.setPasswordHash(encoder.encode(newPassword));
    }
    admins.save(admin);
    audit.record(principal, "UPDATE", "admin_users", admin.getId(), "管理者アカウント情報を更新");
    return "redirect:/management/account?updated";
  }

  private void populateReservationFormModel(Model model, Reservation reservation) {
    model.addAttribute("reservation", reservation);
    model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("programs", programs.findAll());
    model.addAttribute("timeSlotOptions", reservationTimeSlotOptions(reservation));
  }

  private List<String> reservationTimeSlotOptions(Reservation reservation) {
    List<String> options = new ArrayList<>(List.of("10:00", "14:00", "16:00"));
    String current = reservation.getTimeSlot();
    if (current != null && !current.isBlank() && !options.contains(current)) {
      String normalized = normalizeReservationSlot(current);
      if (!options.contains(normalized)) {
        options.add(normalized);
      }
    }
    return options;
  }

  private String normalizeReservationSlot(String slot) {
    if (slot == null || slot.isBlank()) {
      return "10:00";
    }
    if (slot.startsWith("10") || slot.startsWith("11") || slot.startsWith("12")) {
      return "10:00";
    }
    if (slot.startsWith("13") || slot.startsWith("14")) {
      return "14:00";
    }
    return "16:00";
  }

  private List<Product> recipeProductOptions() {
    return products.findByDeletedAtIsNullAndActiveTrueOrderByIdAsc().stream()
        .filter(product -> product.getCategory() != null && "原料".equals(product.getCategory().getCategoryName()))
        .toList();
  }

  private InventoryItem inventoryFromProduct(Product product, ProductCategory category) {
    InventoryItem item = new InventoryItem();
    item.setItemName(product.getProductName());
    item.setEnglishName(product.getEnglishName());
    item.setCategory(category.getCategoryName());
    item.setUnit(defaultUnit(category.getCategoryName()));
    item.setStockQuantity(BigDecimal.ZERO);
    item.setThresholdQuantity(BigDecimal.ZERO);
    return item;
  }

  private void syncProductFromInventory(InventoryItem item) {
    ProductCategory category = categoryForInventory(item.getCategory());
    Product product = products.findByIdAndDeletedAtIsNull(item.getId()).orElseGet(() -> {
      Product seed = new Product();
      seed.setPrice(BigDecimal.ONE);
      seed.setActive(true);
      return seed;
    });
    product.setProductName(item.getItemName());
    product.setEnglishName(item.getEnglishName());
    product.setCategory(category);
    if (product.getPrice() == null) {
      product.setPrice(BigDecimal.ONE);
    }
    products.save(product);
  }

  private ProductCategory categoryForInventory(String inventoryCategory) {
    return productCategories.findByCategoryNameAndDeletedAtIsNull(inventoryCategory)
        .or(() -> productCategories.findByCategoryNameAndDeletedAtIsNull(normalizedProductCategoryName(inventoryCategory)))
        .orElseGet(() -> productCategories.findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc().stream()
            .findFirst()
            .orElseThrow());
  }

  private String normalizedProductCategoryName(String inventoryCategory) {
    if (inventoryCategory == null) {
      return "商品";
    }
    String normalized = inventoryCategory.toLowerCase();
    if (inventoryCategory.contains("原料") || normalized.contains("material") || normalized.contains("fragrance")) {
      return "原料";
    }
    if (inventoryCategory.contains("容器") || normalized.contains("container")) {
      return "容器";
    }
    return "商品";
  }

  private String defaultUnit(String categoryName) {
    return "原料".equals(categoryName) ? "ml" : "pcs";
  }

  private List<FragranceRecipeMaterial> buildRecipeMaterials(FragranceRecipe recipe, List<Long> materialIds,
      List<BigDecimal> blendRatios) {
    List<FragranceRecipeMaterial> materials = new ArrayList<>();
    BigDecimal totalBlendRatio = BigDecimal.ZERO;
    if (materialIds != null && blendRatios != null) {
      int count = Math.min(Math.min(materialIds.size(), blendRatios.size()), 5);
      for (int i = 0; i < count; i++) {
        Long materialId = materialIds.get(i);
        BigDecimal blendRatio = blendRatios.get(i);
        if (materialId == null || blendRatio == null) {
          continue;
        }
        if (blendRatio.compareTo(BigDecimal.ZERO) <= 0) {
          throw new IllegalArgumentException("配合率は0より大きい値を入力してください");
        }
        if (blendRatio.stripTrailingZeros().scale() > 0) {
          throw new IllegalArgumentException("配合率は整数で入力してください");
        }
        if (blendRatio.remainder(new BigDecimal("5")).compareTo(BigDecimal.ZERO) != 0) {
          throw new IllegalArgumentException("配合率は5刻みで入力してください");
        }
        FragranceRecipeMaterial material = new FragranceRecipeMaterial();
        material.setFragranceRecipe(recipe);
        material.setInventoryItem(inventory.findById(materialId).orElseThrow());
        material.setBlendRatio(blendRatio);
        material.setDisplayOrder(i + 1);
        materials.add(material);
        totalBlendRatio = totalBlendRatio.add(blendRatio);
      }
    }
    if (materials.isEmpty()) {
      throw new IllegalArgumentException("原料を1つ以上入力してください");
    }
    if (totalBlendRatio.compareTo(new BigDecimal("100")) != 0) {
      throw new IllegalArgumentException("配合率は合計１００になるようにしてください");
    }
    return materials;
  }
}

