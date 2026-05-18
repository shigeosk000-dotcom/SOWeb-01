package com.aromatripnippon.repository;

import com.aromatripnippon.entity.FragranceRecipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FragranceRecipeRepository extends JpaRepository<FragranceRecipe, Long> {
  Optional<FragranceRecipe> findByIdAndDeletedAtIsNull(Long id);
  @EntityGraph(attributePaths = "customer")
  List<FragranceRecipe> findByDeletedAtIsNullOrderByIdDesc();
  @EntityGraph(attributePaths = "customer")
  List<FragranceRecipe> findByDeletedAtIsNullAndRecipeNameContainingIgnoreCaseOrderByIdDesc(String recipeName);
  @Query("select (count(r) > 0) from FragranceRecipe r where r.deletedAt is null and r.customer.id = :customerId")
  boolean existsActiveByCustomerId(@Param("customerId") Long customerId);
}
