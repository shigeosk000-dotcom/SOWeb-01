package com.aromatripnippon.config;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.AuditLog;
import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.ExperienceProgram;
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
import com.aromatripnippon.repository.FragranceRecipeRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import com.aromatripnippon.repository.InventoryTransactionRepository;
import com.aromatripnippon.repository.ProductCategoryRepository;
import com.aromatripnippon.repository.ProductRepository;
import com.aromatripnippon.repository.ReservationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(name = "app.data-initializer.enabled", havingValue = "true")
public class DataInitializer implements CommandLineRunner {
  private final AdminUserRepository admins;
  private final ExperienceProgramRepository programs;
  private final InventoryItemRepository inventory;
  private final InventoryTransactionRepository transactions;
  private final ProductCategoryRepository productCategories;
  private final ProductRepository products;
  private final CustomerRepository customers;
  private final ReservationRepository reservations;
  private final FragranceRecipeRepository recipes;
  private final AuditLogRepository auditLogs;
  private final PasswordEncoder encoder;

  public DataInitializer(AdminUserRepository admins, ExperienceProgramRepository programs,
      InventoryItemRepository inventory, InventoryTransactionRepository transactions,
      ProductCategoryRepository productCategories, ProductRepository products,
      CustomerRepository customers, ReservationRepository reservations, FragranceRecipeRepository recipes,
      AuditLogRepository auditLogs, PasswordEncoder encoder) {
    this.admins = admins;
    this.programs = programs;
    this.inventory = inventory;
    this.transactions = transactions;
    this.productCategories = productCategories;
    this.products = products;
    this.customers = customers;
    this.reservations = reservations;
    this.recipes = recipes;
    this.auditLogs = auditLogs;
    this.encoder = encoder;
  }

  @Override
  @Transactional
  public void run(String... args) {
    AdminUser admin = admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue("AromaTripAdm01").orElseGet(() -> {
      AdminUser seed = new AdminUser();
      seed.setLoginId("AromaTripAdm01");
      seed.setDisplayName("AromaTrip Manager");
      seed.setEmail("admin@aromatripnippon.local");
      seed.setPasswordHash(encoder.encode("password"));
      seed.setRole("ADMIN");
      seed.setActive(true);
      return admins.save(seed);
    });

    ExperienceProgram workshop = programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElseGet(() -> {
      ExperienceProgram seed = new ExperienceProgram();
      seed.setName("Aroma Journey Workshop");
      seed.setDescription("Create a personal fragrance using Japanese botanical materials.");
      seed.setDurationMinutes(90);
      seed.setPrice(new BigDecimal("8800"));
      seed.setMaterialsSummary("Yuzu, hiba wood, mint, glass bottle");
      seed.setActive(true);
      return programs.save(seed);
    });

    Customer emily = ensureCustomer("Emily Chen", "Taiwan", "emily.chen@example.com", "+886-900-000-001",
        "English", "Travel experience", "Booked a fragrance workshop.");
    Customer lucas = ensureCustomer("Lucas Smith", "United States", "lucas.smith@example.com", "+1-202-555-0102",
        "English", "Gift purchase", "Interested in a take-home gift.");

    InventoryItem yuzu = ensureInventory("柚子精油", "Yuzu essential oil", "素材", "ml", "Shelf A",
        new BigDecimal("500"), new BigDecimal("100"), "Kyushu Botanicals");
    InventoryItem hiba = ensureInventory("青森ひば精油", "Aomori hiba essential oil", "素材", "ml", "Shelf A",
        new BigDecimal("420"), new BigDecimal("80"), "Aomori Wood Lab");
    InventoryItem mint = ensureInventory("和薄荷精油", "Japanese mint oil", "素材", "ml", "Shelf A",
        new BigDecimal("360"), new BigDecimal("80"), "Hokkaido Herbs");
    InventoryItem bottle = ensureInventory("ガラスボトル 30ml", "Glass bottle 30ml", "容器", "pcs", "Shelf B",
        new BigDecimal("120"), new BigDecimal("30"), "Tokyo Bottle Works");

    ensureProductCategory("製品", 1);
    ProductCategory material = ensureProductCategory("素材", 2);
    ProductCategory container = ensureProductCategory("容器", 3);

    if (products.findByDeletedAtIsNullOrderByIdDesc().isEmpty()) {
      products.save(product(yuzu, material, new BigDecimal("1200"), "Japanese yuzu fragrance material."));
      products.save(product(hiba, material, new BigDecimal("1400"), "Aomori hiba wood fragrance material."));
      products.save(product(mint, material, new BigDecimal("1100"), "Japanese mint fragrance material."));
      products.save(product(bottle, container, new BigDecimal("300"), "Glass bottle for workshop blends."));
    }

    if (reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc().isEmpty()) {
      reservations.save(reservation(emily, workshop, LocalDate.now().plusDays(7), "14:00", 2,
          "English guidance requested."));
      reservations.save(reservation(lucas, workshop, LocalDate.now().plusDays(9), "10:00", 2,
          "Considering gift purchase."));
    }

    if (recipes.findByDeletedAtIsNullOrderByIdDesc().isEmpty()) {
      recipes.save(recipe(emily, "Yuzu and Hiba Blend", "Fresh citrus with calm wood notes.", yuzu, hiba));
      recipes.save(recipe(lucas, "Mint and Yuzu Refresh", "Light, cool fragrance for a gift.", mint, yuzu));
    }

    if (transactions.findByDeletedAtIsNullOrderByIdDesc().isEmpty()) {
      InventoryTransaction t1 = transactions.save(transaction(yuzu, admin, "IN", new BigDecimal("500"), "Initial stock"));
      yuzu.setStockQuantity(yuzu.getStockQuantity().add(t1.getQuantity()));
      inventory.save(yuzu);

      InventoryTransaction t2 = transactions.save(transaction(hiba, admin, "IN", new BigDecimal("420"), "Initial stock"));
      hiba.setStockQuantity(hiba.getStockQuantity().add(t2.getQuantity()));
      inventory.save(hiba);

      InventoryTransaction t3 = transactions.save(transaction(bottle, admin, "IN", new BigDecimal("120"), "Initial stock"));
      bottle.setStockQuantity(bottle.getStockQuantity().add(t3.getQuantity()));
      inventory.save(bottle);
    }

    if (auditLogs.findByDeletedAtIsNullOrderByIdDesc().isEmpty()) {
      AuditLog log = new AuditLog();
      log.setAdminUser(admin);
      log.setActionType("SEED");
      log.setTargetTable("phase1_master_data");
      log.setDetail("Inserted Phase1 sample data.");
      auditLogs.save(log);
    }

    normalizeJapaneseDisplayNames();
    normalizeReservationValuesForManagementForm();
  }

  private void normalizeJapaneseDisplayNames() {
    for (InventoryItem item : inventory.findByDeletedAtIsNullOrderByIdDesc()) {
      String translated = japaneseNameForEnglish(item.getEnglishName());
      if (translated != null && (item.getItemName() == null || item.getItemName().isBlank()
          || item.getItemName().equals(item.getEnglishName()))) {
        item.setItemName(translated);
        inventory.save(item);
      }
    }
    for (Product product : products.findByDeletedAtIsNullOrderByIdDesc()) {
      String translated = japaneseNameForEnglish(product.getEnglishName());
      if (translated != null && (product.getProductName() == null || product.getProductName().isBlank()
          || product.getProductName().equals(product.getEnglishName()))) {
        product.setProductName(translated);
        products.save(product);
      }
    }
  }

  private String japaneseNameForEnglish(String englishName) {
    if (englishName == null) {
      return null;
    }
    return switch (englishName) {
      case "Yuzu essential oil" -> "柚子精油";
      case "Aomori hiba essential oil" -> "青森ひば精油";
      case "Japanese mint oil" -> "和薄荷精油";
      case "Sakura absolute" -> "桜アブソリュート";
      case "Hinoki wood oil" -> "ひのき木部精油";
      case "Matcha green tea extract" -> "抹茶エキス";
      case "White cedar resin" -> "白杉レジン";
      case "Glass bottle 30ml" -> "ガラスボトル 30ml";
      case "Aluminum tube 50ml" -> "アルミチューブ 50ml";
      case "Yuzu Hand Cream" -> "柚子ハンドクリーム";
      case "Hiba Hand Cream" -> "ひばハンドクリーム";
      case "Matcha Hand Cream" -> "抹茶ハンドクリーム";
      case "Sakura Perfume" -> "桜パフューム";
      case "Hinoki Perfume" -> "ひのきパフューム";
      case "Cedar Perfume" -> "シダーパフューム";
      default -> null;
    };
  }

  private void normalizeReservationValuesForManagementForm() {
    Set<String> allowedSlots = Set.of("10:00", "14:00", "16:00");
    LocalDate fallbackDate = LocalDate.now().plusDays(1);
    List<Reservation> activeReservations = reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc();
    for (Reservation reservation : activeReservations) {
      boolean changed = false;
      if (reservation.getVisitDate() == null) {
        reservation.setVisitDate(fallbackDate);
        changed = true;
      }
      String slot = reservation.getTimeSlot();
      String normalizedSlot = normalizeReservationSlot(slot);
      if (slot == null || slot.isBlank() || !allowedSlots.contains(slot) || !slot.equals(normalizedSlot)) {
        reservation.setTimeSlot(normalizedSlot);
        changed = true;
      }
      if (changed) {
        reservations.save(reservation);
      }
    }
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

  private Customer ensureCustomer(String name, String nationality, String email, String phone, String language,
      String purpose, String note) {
    return customers.findAll().stream()
        .filter(customer -> email.equalsIgnoreCase(customer.getEmail()))
        .findFirst()
        .orElseGet(() -> {
          Customer seed = new Customer();
          seed.setName(name);
          seed.setNationality(nationality);
          seed.setEmail(email);
          seed.setPhone(phone);
          seed.setPreferredLanguage(language);
          seed.setPurpose(purpose);
          seed.setNotes(note);
          return customers.save(seed);
        });
  }

  private InventoryItem ensureInventory(String name, String englishName, String category, String unit, String location,
      BigDecimal stock, BigDecimal threshold, String supplier) {
    return inventory.findByDeletedAtIsNullAndItemNameContainingIgnoreCaseOrderByIdDesc(name).stream()
        .filter(item -> name.equalsIgnoreCase(item.getItemName()))
        .findFirst()
        .orElseGet(() -> {
          InventoryItem seed = new InventoryItem();
          seed.setItemName(name);
          seed.setEnglishName(englishName);
          seed.setCategory(category);
          seed.setUnit(unit);
          seed.setStorageLocation(location);
          seed.setStockQuantity(stock);
          seed.setThresholdQuantity(threshold);
          seed.setSupplier(supplier);
          seed.setLastReceivedDate(LocalDate.now());
          return inventory.save(seed);
        });
  }

  private ProductCategory ensureProductCategory(String name, int displayOrder) {
    return productCategories.findByCategoryNameAndDeletedAtIsNull(name)
        .orElseGet(() -> {
          ProductCategory seed = new ProductCategory();
          seed.setCategoryName(name);
          seed.setDisplayOrder(displayOrder);
          seed.setActive(true);
          return productCategories.save(seed);
        });
  }

  private Reservation reservation(Customer customer, ExperienceProgram program, LocalDate date, String time,
      Integer people, String note) {
    Reservation seed = new Reservation();
    seed.setCustomer(customer);
    seed.setExperienceProgram(program);
    seed.setVisitDate(date);
    seed.setTimeSlot(time);
    seed.setGuestCount(people);
    seed.setPreferredLanguage(customer.getPreferredLanguage());
    seed.setRequestNote(note);
    seed.setStatus("RESERVED");
    return seed;
  }

  private FragranceRecipe recipe(Customer customer, String name, String concept, InventoryItem first,
      InventoryItem second) {
    FragranceRecipe seed = new FragranceRecipe();
    seed.setCustomer(customer);
    seed.setRecipeName(name);
    seed.setConcept(concept);
    seed.setTotalAmount(new BigDecimal("100.00"));
    seed.getMaterials().add(material(seed, first, new BigDecimal("60.00"), 1));
    seed.getMaterials().add(material(seed, second, new BigDecimal("40.00"), 2));
    return seed;
  }

  private Product product(InventoryItem item, ProductCategory category, BigDecimal price, String description) {
    Product seed = new Product();
    seed.setProductName(item.getItemName());
    seed.setEnglishName(item.getEnglishName());
    seed.setCategory(category);
    seed.setPrice(price);
    seed.setDescription(description);
    seed.setActive(true);
    return seed;
  }

  private FragranceRecipeMaterial material(FragranceRecipe recipe, InventoryItem item, BigDecimal ratio, int order) {
    FragranceRecipeMaterial seed = new FragranceRecipeMaterial();
    seed.setFragranceRecipe(recipe);
    seed.setInventoryItem(item);
    seed.setMaterialNameSnapshot(item.getItemName());
    seed.setBlendRatio(ratio);
    seed.setAmount(ratio);
    seed.setDisplayOrder(order);
    return seed;
  }

  private InventoryTransaction transaction(InventoryItem item, AdminUser admin, String type, BigDecimal quantity,
      String reason) {
    InventoryTransaction seed = new InventoryTransaction();
    seed.setInventoryItem(item);
    seed.setAdminUser(admin);
    seed.setTransactionType(type);
    seed.setQuantity(quantity);
    seed.setReason(reason);
    seed.setTransactionDate(LocalDate.now());
    return seed;
  }
}
