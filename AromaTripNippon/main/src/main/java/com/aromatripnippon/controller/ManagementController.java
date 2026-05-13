package com.aromatripnippon.controller;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.FragranceRecipe;
import com.aromatripnippon.entity.FragranceRecipeMaterial;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.entity.InventoryTransaction;
import com.aromatripnippon.entity.Product;
import com.aromatripnippon.entity.Reservation;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.AuditLogRepository;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.FragranceRecipeRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import com.aromatripnippon.repository.InventoryTransactionRepository;
import com.aromatripnippon.repository.ProductRepository;
import com.aromatripnippon.repository.ReservationRepository;
import com.aromatripnippon.service.AuditService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  private final InventoryItemRepository inventory;
  private final InventoryTransactionRepository inventoryTransactions;
  private final ProductRepository products;
  private final AdminUserRepository admins;
  private final AuditLogRepository auditLogs;
  private final AuditService audit;
  private final PasswordEncoder encoder;

  public ManagementController(ReservationRepository reservations, CustomerRepository customers,
      ExperienceProgramRepository programs, FragranceRecipeRepository recipes, InventoryItemRepository inventory,
      InventoryTransactionRepository inventoryTransactions, ProductRepository products, AdminUserRepository admins,
      AuditLogRepository auditLogs, AuditService audit, PasswordEncoder encoder) {
    this.reservations = reservations;
    this.customers = customers;
    this.programs = programs;
    this.recipes = recipes;
    this.inventory = inventory;
    this.inventoryTransactions = inventoryTransactions;
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
    model.addAttribute("reservations", reservations.findByDeletedAtIsNullOrderByVisitDateDescTimeSlotAsc());
    model.addAttribute("q", q);
    return "management/reservation-list";
  }

  @GetMapping("/management/reservations/new")
  public String reservationNew(Model model) {
    model.addAttribute("reservation", new Reservation());
    model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("programs", programs.findAll());
    return "management/reservation-form";
  }

  @PostMapping("/management/reservations")
  public String reservationCreate(@ModelAttribute Reservation reservation, @RequestParam Long customerId,
      @RequestParam Long programId, Principal principal) {
    reservation.setCustomer(customers.findById(customerId).orElseThrow());
    reservation.setExperienceProgram(programs.findById(programId).orElseThrow());
    Reservation saved = reservations.save(reservation);
    audit.record(principal, "CREATE", "reservations", saved.getId(), "予約を登録");
    return "redirect:/management/reservations/" + saved.getId();
  }

  @GetMapping("/management/reservations/{id}")
  public String reservationDetail(@PathVariable Long id, Model model) {
    model.addAttribute("reservation", reservations.findById(id).orElseThrow());
    return "management/reservation-detail";
  }

  @GetMapping("/management/reservations/{id}/edit")
  public String reservationEdit(@PathVariable Long id, Model model) {
    model.addAttribute("reservation", reservations.findById(id).orElseThrow());
    model.addAttribute("customers", customers.findByDeletedAtIsNullOrderByIdDesc());
    model.addAttribute("programs", programs.findAll());
    return "management/reservation-form";
  }

  @PostMapping("/management/reservations/{id}")
  public String reservationUpdate(@PathVariable Long id, @ModelAttribute Reservation form, @RequestParam Long customerId,
      @RequestParam Long programId, Principal principal) {
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
  public String reservationDelete(@PathVariable Long id, Principal principal) {
    Reservation reservation = reservations.findById(id).orElseThrow();
    reservation.softDelete();
    reservations.save(reservation);
    audit.record(principal, "DELETE", "reservations", id, "予約を論理削除");
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
  public String customerCreate(@ModelAttribute Customer customer, Principal principal) {
    Customer saved = customers.save(customer);
    audit.record(principal, "CREATE", "customers", saved.getId(), "顧客を登録");
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
  public String customerUpdate(@PathVariable Long id, @ModelAttribute Customer form, Principal principal) {
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
  public String customerDelete(@PathVariable Long id, Principal principal) {
    Customer customer = customers.findById(id).orElseThrow();
    customer.softDelete();
    customers.save(customer);
    audit.record(principal, "DELETE", "customers", id, "顧客を論理削除");
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
    return "management/recipe-form";
  }

  @PostMapping("/management/recipes")
  @Transactional
  public String recipeCreate(@ModelAttribute FragranceRecipe recipe, @RequestParam Long customerId,
      @RequestParam(name = "materialId", required = false) List<Long> materialIds,
      @RequestParam(name = "blendRatio", required = false) List<BigDecimal> blendRatios, Principal principal,
      RedirectAttributes redirectAttributes) {
    recipe.setCustomer(customers.findById(customerId).orElseThrow());
    try {
      recipe.getMaterials().addAll(buildRecipeMaterials(recipe, materialIds, blendRatios));
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
      return "redirect:/management/recipes/new";
    }
    FragranceRecipe saved = recipes.save(recipe);
    audit.record(principal, "CREATE", "fragrance_recipes", saved.getId(), "香りレシピを登録");
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
    return "management/recipe-form";
  }

  @PostMapping("/management/recipes/{id}")
  @Transactional
  public String recipeUpdate(@PathVariable Long id, @ModelAttribute FragranceRecipe form,
      @RequestParam Long customerId,
      @RequestParam(name = "materialId", required = false) List<Long> materialIds,
      @RequestParam(name = "blendRatio", required = false) List<BigDecimal> blendRatios, Principal principal,
      RedirectAttributes redirectAttributes, Model model) {
    FragranceRecipe recipe = recipes.findById(id).orElseThrow();
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
    audit.record(principal, "DELETE", "fragrance_recipes", id, "香りレシピを論理削除");
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
    model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
    return "management/product-form";
  }

  @PostMapping("/management/products")
  public String productCreate(@ModelAttribute Product product, @RequestParam Long inventoryItemId, Principal principal) {
    product.setInventoryItem(inventory.findById(inventoryItemId).orElseThrow());
    Product saved = products.save(product);
    audit.record(principal, "CREATE", "products", saved.getId(), "商品を登録");
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
    model.addAttribute("items", inventory.findByDeletedAtIsNullOrderByIdDesc());
    return "management/product-form";
  }

  @PostMapping("/management/products/{id}")
  public String productUpdate(@PathVariable Long id, @ModelAttribute Product form,
      @RequestParam Long inventoryItemId, Principal principal) {
    Product product = products.findById(id).orElseThrow();
    product.setProductName(form.getProductName());
    product.setCategory(form.getCategory());
    product.setPrice(form.getPrice());
    product.setDescription(form.getDescription());
    product.setImagePath(form.getImagePath());
    product.setPublished(form.getPublished() != null && form.getPublished());
    product.setInventoryItem(inventory.findById(inventoryItemId).orElseThrow());
    products.save(product);
    audit.record(principal, "UPDATE", "products", id, "商品を更新");
    return "redirect:/management/products/" + id;
  }

  @PostMapping("/management/products/{id}/delete")
  public String productDelete(@PathVariable Long id, Principal principal) {
    Product product = products.findById(id).orElseThrow();
    product.softDelete();
    products.save(product);
    audit.record(principal, "DELETE", "products", id, "商品を論理削除");
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
  public String inventoryCreate(@ModelAttribute InventoryItem item, Principal principal) {
    InventoryItem saved = inventory.save(item);
    audit.record(principal, "CREATE", "inventory_items", saved.getId(), "在庫品目を登録");
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
  public String inventoryUpdate(@PathVariable Long id, @ModelAttribute InventoryItem form, Principal principal) {
    InventoryItem item = inventory.findById(id).orElseThrow();
    item.setItemName(form.getItemName());
    item.setCategory(form.getCategory());
    item.setStockQuantity(form.getStockQuantity());
    item.setUnit(form.getUnit());
    item.setThresholdQuantity(form.getThresholdQuantity());
    item.setStorageLocation(form.getStorageLocation());
    item.setSupplier(form.getSupplier());
    item.setLastReceivedDate(form.getLastReceivedDate());
    item.setMemo(form.getMemo());
    inventory.save(item);
    audit.record(principal, "UPDATE", "inventory_items", id, "在庫品目を更新");
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
    audit.record(principal, "UPDATE", "inventory_transactions", transaction.getId(), "在庫を更新");
    return "redirect:/management/inventory/" + id;
  }

  @PostMapping("/management/inventory/{id}/delete")
  public String inventoryDelete(@PathVariable Long id, Principal principal) {
    InventoryItem item = inventory.findById(id).orElseThrow();
    item.softDelete();
    inventory.save(item);
    audit.record(principal, "DELETE", "inventory_items", id, "在庫品目を論理削除");
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
    audit.record(principal, "UPDATE", "admin_users", admin.getId(), "アカウント設定を更新");
    return "redirect:/management/account?updated";
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
      throw new IllegalArgumentException("素材を1件以上入力してください");
    }
    if (totalBlendRatio.compareTo(new BigDecimal("100")) != 0) {
      throw new IllegalArgumentException("配合率は合計１００になるようにしてください");
    }
    return materials;
  }
}
