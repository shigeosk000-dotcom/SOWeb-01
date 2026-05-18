package com.aromatripnippon.repository;

import com.aromatripnippon.entity.ProductCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
  Optional<ProductCategory> findByCategoryNameAndDeletedAtIsNull(String categoryName);
  List<ProductCategory> findByDeletedAtIsNullAndActiveTrueOrderByDisplayOrderAscIdAsc();
}
