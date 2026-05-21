package com.aromatripnippon.service;

import com.aromatripnippon.entity.Product;
import com.aromatripnippon.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {
  private final ProductRepository products;

  public ProductService(ProductRepository products) {
    this.products = products;
  }

  public List<Product> search(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return products.findByDeletedAtIsNullOrderByIdDesc();
    }
    return products.findByDeletedAtIsNullAndProductNameContainingIgnoreCaseOrderByIdDesc(keyword);
  }

  public Product findActive(Long id) {
    return products.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  @Transactional
  public Product save(@Valid Product product) {
    return products.save(product);
  }

  @Transactional
  public void softDelete(Long id) {
    Product product = findActive(id);
    product.softDelete();
    products.save(product);
  }
}
