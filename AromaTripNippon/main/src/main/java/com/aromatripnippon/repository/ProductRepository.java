package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @EntityGraph(attributePaths = "category")
  Optional<Product> findByIdAndDeletedAtIsNull(Long id);
  @EntityGraph(attributePaths = "category")
  List<Product> findByDeletedAtIsNullOrderByIdDesc();
  @EntityGraph(attributePaths = "category")
  List<Product> findByDeletedAtIsNullAndActiveTrueOrderByIdAsc();
  @EntityGraph(attributePaths = "category")
  List<Product> findByDeletedAtIsNullAndProductNameContainingIgnoreCaseOrderByIdDesc(String productName);
}
