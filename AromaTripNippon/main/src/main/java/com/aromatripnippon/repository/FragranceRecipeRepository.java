package com.aromatripnippon.repository;

import com.aromatripnippon.entity.FragranceRecipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FragranceRecipeRepository extends JpaRepository<FragranceRecipe, Long> {
  Optional<FragranceRecipe> findByIdAndDeletedAtIsNull(Long id);
  List<FragranceRecipe> findByDeletedAtIsNullOrderByIdDesc();
  List<FragranceRecipe> findByDeletedAtIsNullAndRecipeNameContainingIgnoreCaseOrderByIdDesc(String recipeName);
}
