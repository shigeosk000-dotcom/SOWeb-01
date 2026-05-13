package com.aromatripnippon.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.entity.InventoryTransaction;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;

@DataJpaTest
@Import(InventoryService.class)
class InventoryServiceTest {
  @Autowired
  private InventoryService inventoryService;
  @Autowired
  private InventoryItemRepository inventoryItems;
  @Autowired
  private AdminUserRepository admins;

  @Test
  void recordTransaction_updatesStockAndKeepsHistory() {
    AdminUser admin = new AdminUser();
    admin.setLoginId("AdmTest");
    admin.setDisplayName("Admin Test");
    admin.setEmail("admin@test.local");
    admin.setPasswordHash("hashed");
    admins.save(admin);

    InventoryItem item = new InventoryItem();
    item.setItemName("Test Material");
    item.setCategory("material");
    item.setStockQuantity(new BigDecimal("100.0"));
    item.setUnit("ml");
    item.setThresholdQuantity(new BigDecimal("10.0"));
    item.setLastReceivedDate(LocalDate.now().minusDays(1));
    item = inventoryItems.save(item);

    InventoryTransaction tx = inventoryService.recordTransaction(
        item.getId(),
        "OUT",
        new BigDecimal("15.0"),
        "for recipe",
        new TestingAuthenticationToken("AdmTest", "n/a"));

    InventoryItem reloaded = inventoryService.findActive(item.getId());
    assertThat(reloaded.getStockQuantity()).isEqualByComparingTo(new BigDecimal("85.0"));
    assertThat(tx.getId()).isNotNull();
    assertThat(tx.getInventoryItem().getId()).isEqualTo(item.getId());
    assertThat(tx.getAdminUser()).isNotNull();
    assertThat(inventoryService.findTransactions(item.getId())).hasSize(1);
  }
}
