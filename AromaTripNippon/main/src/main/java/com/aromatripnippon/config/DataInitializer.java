package com.aromatripnippon.config;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.ExperienceProgram;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.entity.Product;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import com.aromatripnippon.repository.ProductRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
  private final AdminUserRepository admins;
  private final ExperienceProgramRepository programs;
  private final InventoryItemRepository inventory;
  private final ProductRepository products;
  private final CustomerRepository customers;
  private final PasswordEncoder encoder;

  public DataInitializer(AdminUserRepository admins, ExperienceProgramRepository programs,
      InventoryItemRepository inventory, ProductRepository products, CustomerRepository customers,
      PasswordEncoder encoder) {
    this.admins = admins;
    this.programs = programs;
    this.inventory = inventory;
    this.products = products;
    this.customers = customers;
    this.encoder = encoder;
  }

  @Override
  public void run(String... args) {
    admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue("Adm01").orElseGet(() -> {
      AdminUser admin = new AdminUser();
      admin.setLoginId("Adm01");
      admin.setDisplayName("AromaTrip Manager");
      admin.setEmail("admin@aromatripnippon.local");
      admin.setPasswordHash(encoder.encode("password"));
      return admins.save(admin);
    });

    if (programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().isEmpty()) {
      ExperienceProgram program = new ExperienceProgram();
      program.setName("Aroma Journey Workshop");
      program.setDescription("日本各地の天然素材を選び、自分だけの香りを調合する体験。");
      program.setDurationMinutes(90);
      program.setPrice(new BigDecimal("8800"));
      program.setMaterialsSummary("柚子、青森ヒバ、薄荷、和精油");
      programs.save(program);
    }

    if (inventory.count() == 0) {
      InventoryItem yuzu = item("柚子精油", "香料", "ml", "Shelf A", new BigDecimal("500"), new BigDecimal("100"));
      InventoryItem hiba = item("青森ヒバ精油", "香料", "ml", "Shelf A", new BigDecimal("420"), new BigDecimal("80"));
      InventoryItem bottle = item("ガラスボトル 30ml", "容器", "個", "Shelf B", new BigDecimal("120"), new BigDecimal("30"));
      inventory.save(yuzu);
      inventory.save(hiba);
      inventory.save(bottle);
      Product product = new Product();
      product.setProductName("Aroma Mist 30ml");
      product.setCategory("香水");
      product.setPrice(new BigDecimal("4200"));
      product.setDescription("管理画面のみで扱うPhase1商品サンプル。");
      product.setInventoryItem(bottle);
      product.setImagePath("/assets/images/material_yuzu.png");
      products.save(product);
    }

    if (customers.count() == 0) {
      Customer customer = new Customer();
      customer.setName("Sample Guest");
      customer.setEmail("guest@example.com");
      customer.setNationality("United States");
      customer.setPreferredLanguage("English");
      customer.setPurpose("Travel memory");
      customers.save(customer);
    }
  }

  private InventoryItem item(String name, String category, String unit, String location, BigDecimal stock,
      BigDecimal threshold) {
    InventoryItem item = new InventoryItem();
    item.setItemName(name);
    item.setCategory(category);
    item.setUnit(unit);
    item.setStorageLocation(location);
    item.setStockQuantity(stock);
    item.setThresholdQuantity(threshold);
    return item;
  }
}
