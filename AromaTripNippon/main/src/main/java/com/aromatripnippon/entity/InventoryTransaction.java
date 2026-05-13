package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_transactions")
public class InventoryTransaction extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_item_id", nullable = false)
  @NotNull
  private InventoryItem inventoryItem;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by")
  private AdminUser adminUser;
  @NotBlank
  @Column(name = "transaction_type", nullable = false)
  private String transactionType;
  @NotNull
  @Positive
  private BigDecimal quantity;
  @Column(length = 1000)
  private String reason;
  @Column(name = "transaction_date")
  private LocalDate transactionDate = LocalDate.now();

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
  public LocalDate getTransactionDate() { return transactionDate; }
  public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
}
