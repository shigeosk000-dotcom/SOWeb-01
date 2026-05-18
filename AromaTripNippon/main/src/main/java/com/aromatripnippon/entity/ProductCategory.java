package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_categories")
public class ProductCategory extends BaseEntity {
  @NotBlank
  @Column(name = "category_name", nullable = false, unique = true)
  private String categoryName;
  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;
  @Column(name = "is_active")
  private Boolean active = true;

  public String getCategoryName() { return categoryName; }
  public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
  public Integer getDisplayOrder() { return displayOrder; }
  public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
}
