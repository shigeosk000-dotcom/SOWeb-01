package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private InventoryItem inventoryItem;
  @NotBlank
  private String productName;
  @NotBlank
  private String category;
  @NotNull
  private BigDecimal price;
  private String description;
  private String imagePath;
  private Boolean published = false;

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
  public Boolean getPublished() { return published; }
  public void setPublished(Boolean published) { this.published = published; }
}
