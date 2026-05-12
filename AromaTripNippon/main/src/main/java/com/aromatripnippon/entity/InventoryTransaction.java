package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory_transactions")
public class InventoryTransaction extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private InventoryItem inventoryItem;
  @ManyToOne(fetch = FetchType.LAZY)
  private AdminUser adminUser;
  @NotBlank
  private String transactionType;
  @NotNull
  private BigDecimal quantity;
  private String reason;

  public InventoryItem getInventoryItem() { return inventoryItem; }
  public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }
  public AdminUser getAdminUser() { return adminUser; }
  public void setAdminUser(AdminUser adminUser) { this.adminUser = adminUser; }
  public String getTransactionType() { return transactionType; }
  public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
  public BigDecimal getQuantity() { return quantity; }
  public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
  public String getReason() { return reason; }
  public void setReason(String reason) { this.reason = reason; }
}
