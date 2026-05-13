package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByIdAndDeletedAtIsNull(Long id);
  List<Customer> findByDeletedAtIsNullOrderByIdDesc();
  List<Customer> findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByIdDesc(String name);
}
