package com.aromatripnippon.repository;

import com.aromatripnippon.entity.FragranceRecipe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FragranceRecipeRepository extends JpaRepository<FragranceRecipe, Long> {
  List<FragranceRecipe> findByDeletedAtIsNullOrderByIdDesc();
  List<FragranceRecipe> findByDeletedAtIsNullAndRecipeNameContainingIgnoreCaseOrderByIdDesc(String recipeName);
}
