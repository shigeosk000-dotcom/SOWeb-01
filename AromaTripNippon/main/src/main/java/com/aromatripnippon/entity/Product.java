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

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_item_id")
  private InventoryItem inventoryItem;
  @NotBlank
  @Column(name = "product_name", nullable = false)
  private String productName;
  @NotBlank
  private String category;
  @NotNull
  @Positive
  private BigDecimal price;
  @Column(length = 1000)
  private String description;
  @Column(name = "image_path")
  private String imagePath;
  @Column(name = "is_active")
  private Boolean active = false;

  public InventoryItem getInventoryItem() { return inventoryItem; }
  public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }
  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getImagePath() { return imagePath; }
  public void setImagePath(String imagePath) { this.imagePath = imagePath; }
  public Boolean getPublished() { return active; }
  public void setPublished(Boolean published) { this.active = published; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
}
