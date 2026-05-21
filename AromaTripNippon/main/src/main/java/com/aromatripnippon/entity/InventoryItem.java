package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_items")
public class InventoryItem extends BaseEntity {
  @NotBlank
  @Column(name = "item_name", nullable = false)
  private String itemName;
  @Column(name = "english_name")
  private String englishName;
  @NotBlank
  private String category;
  @NotNull
  @PositiveOrZero
  @Column(name = "stock_quantity", nullable = false)
  private BigDecimal stockQuantity = BigDecimal.ZERO;
  @NotBlank
  private String unit;
  @PositiveOrZero
  @Column(name = "threshold_quantity")
  private BigDecimal thresholdQuantity = BigDecimal.ZERO;
  @Column(name = "storage_location")
  private String storageLocation;
  private String supplier;
  @Column(name = "last_stocked_date")
  private LocalDate lastReceivedDate;
  @Column(length = 1000)
  private String memo;

  public String getItemName() { return itemName; }
  public void setItemName(String itemName) { this.itemName = itemName; }
  public String getEnglishName() { return englishName; }
  public void setEnglishName(String englishName) { this.englishName = englishName; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public BigDecimal getStockQuantity() { return stockQuantity; }
  public void setStockQuantity(BigDecimal stockQuantity) { this.stockQuantity = stockQuantity; }
  public String getUnit() { return unit; }
  public void setUnit(String unit) { this.unit = unit; }
  public BigDecimal getThresholdQuantity() { return thresholdQuantity; }
  public void setThresholdQuantity(BigDecimal thresholdQuantity) { this.thresholdQuantity = thresholdQuantity; }
  public String getStorageLocation() { return storageLocation; }
  public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
  public String getSupplier() { return supplier; }
  public void setSupplier(String supplier) { this.supplier = supplier; }
  public LocalDate getLastReceivedDate() { return lastReceivedDate; }
  public void setLastReceivedDate(LocalDate lastReceivedDate) { this.lastReceivedDate = lastReceivedDate; }
  public String getMemo() { return memo; }
  public void setMemo(String memo) { this.memo = memo; }
}
