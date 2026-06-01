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
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private ProductCategory category;
  @NotBlank
  @Size(max = 255)
  @Column(name = "product_name", nullable = false)
  private String productName;
  @Column(name = "english_name")
  @Size(max = 255)
  private String englishName;
  @NotNull
  @Positive
  private BigDecimal price;
  @Column(length = 1000)
  @Size(max = 1000)
  private String description;
  @Column(name = "is_active")
  private Boolean active = false;

  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }
  public String getEnglishName() { return englishName; }
  public void setEnglishName(String englishName) { this.englishName = englishName; }
  public ProductCategory getCategory() { return category; }
  public void setCategory(ProductCategory category) { this.category = category; }
  public String getCategoryName() { return category != null ? category.getCategoryName() : null; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Boolean getPublished() { return active; }
  public void setPublished(Boolean published) { this.active = published; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
}
