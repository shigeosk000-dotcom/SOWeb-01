package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "fragrance_recipe_materials")
public class FragranceRecipeMaterial extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fragrance_recipe_id", nullable = false)
  @NotNull
  private FragranceRecipe fragranceRecipe;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_item_id", nullable = false)
  @NotNull
  private InventoryItem inventoryItem;
  @Column(name = "material_name_snapshot")
  private String materialNameSnapshot;
  @NotNull
  @Column(name = "blend_ratio", nullable = false)
  private BigDecimal blendRatio;
  @NotNull
  private BigDecimal amount = BigDecimal.ZERO;
  @Column(name = "display_order")
  private Integer displayOrder = 1;

  public FragranceRecipe getFragranceRecipe() { return fragranceRecipe; }
  public void setFragranceRecipe(FragranceRecipe fragranceRecipe) { this.fragranceRecipe = fragranceRecipe; }
  public InventoryItem getInventoryItem() { return inventoryItem; }
  public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }
  public String getMaterialNameSnapshot() { return materialNameSnapshot; }
  public void setMaterialNameSnapshot(String materialNameSnapshot) { this.materialNameSnapshot = materialNameSnapshot; }
  public BigDecimal getBlendRatio() { return blendRatio; }
  public void setBlendRatio(BigDecimal blendRatio) { this.blendRatio = blendRatio; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public Integer getDisplayOrder() { return displayOrder; }
  public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
