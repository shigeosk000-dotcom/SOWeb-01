package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "fragrance_recipe_materials")
public class FragranceRecipeMaterial extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private FragranceRecipe fragranceRecipe;
  @ManyToOne(fetch = FetchType.LAZY)
  private InventoryItem inventoryItem;
  @NotNull
  private BigDecimal blendRatio;

  public FragranceRecipe getFragranceRecipe() { return fragranceRecipe; }
  public void setFragranceRecipe(FragranceRecipe fragranceRecipe) { this.fragranceRecipe = fragranceRecipe; }
  public InventoryItem getInventoryItem() { return inventoryItem; }
  public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }
  public BigDecimal getBlendRatio() { return blendRatio; }
  public void setBlendRatio(BigDecimal blendRatio) { this.blendRatio = blendRatio; }
}
