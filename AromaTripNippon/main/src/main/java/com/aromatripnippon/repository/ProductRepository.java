package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findByIdAndDeletedAtIsNull(Long id);
  @EntityGraph(attributePaths = "inventoryItem")
  Optional<Product> findWithInventoryItemByIdAndDeletedAtIsNull(Long id);
  @EntityGraph(attributePaths = "inventoryItem")
  List<Product> findByDeletedAtIsNullOrderByIdDesc();
  @EntityGraph(attributePaths = "inventoryItem")
  List<Product> findByDeletedAtIsNullAndProductNameContainingIgnoreCaseOrderByIdDesc(String productName);
}
