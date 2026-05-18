package com.aromatripnippon.repository;

import com.aromatripnippon.entity.FragranceRecipeMaterial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FragranceRecipeMaterialRepository extends JpaRepository<FragranceRecipeMaterial, Long> {
  List<FragranceRecipeMaterial> findByFragranceRecipeIdOrderByDisplayOrderAsc(Long fragranceRecipeId);
  boolean existsByDeletedAtIsNullAndFragranceRecipeDeletedAtIsNullAndInventoryItemId(Long inventoryItemId);
}
