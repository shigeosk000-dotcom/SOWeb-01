package com.aromatripnippon.service;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.entity.FragranceRecipe;
import com.aromatripnippon.entity.FragranceRecipeMaterial;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.repository.CustomerRepository;
import com.aromatripnippon.repository.FragranceRecipeRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class FragranceRecipeService {
  private final FragranceRecipeRepository recipes;
  private final CustomerRepository customers;
  private final InventoryItemRepository inventoryItems;

  public FragranceRecipeService(FragranceRecipeRepository recipes, CustomerRepository customers,
      InventoryItemRepository inventoryItems) {
    this.recipes = recipes;
    this.customers = customers;
    this.inventoryItems = inventoryItems;
  }

  public List<FragranceRecipe> search(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return recipes.findByDeletedAtIsNullOrderByIdDesc();
    }
    return recipes.findByDeletedAtIsNullAndRecipeNameContainingIgnoreCaseOrderByIdDesc(keyword);
  }

  public FragranceRecipe findActive(Long id) {
    return recipes.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  @Transactional
  public FragranceRecipe create(FragranceRecipe recipe, Long customerId, Long materialId,
      BigDecimal blendRatio) {
    Customer customer = customers.findByIdAndDeletedAtIsNull(customerId).orElseThrow();
    InventoryItem item = inventoryItems.findByIdAndDeletedAtIsNull(materialId).orElseThrow();
    recipe.setCustomer(customer);
    recipe.setTotalAmount(BigDecimal.valueOf(100));

    FragranceRecipeMaterial material = new FragranceRecipeMaterial();
    material.setFragranceRecipe(recipe);
    material.setInventoryItem(item);
    material.setMaterialNameSnapshot(item.getItemName());
    material.setBlendRatio(blendRatio);
    material.setAmount(BigDecimal.valueOf(100));
    material.setDisplayOrder(1);
    recipe.getMaterials().add(material);
    return recipes.save(recipe);
  }

  @Transactional
  public FragranceRecipe save(@Valid FragranceRecipe recipe) {
    return recipes.save(recipe);
  }

  @Transactional
  public void softDelete(Long id) {
    FragranceRecipe recipe = findActive(id);
    recipe.softDelete();
    recipes.save(recipe);
  }
}
