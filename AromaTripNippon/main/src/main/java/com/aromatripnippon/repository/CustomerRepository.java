package com.aromatripnippon.repository;

import com.aromatripnippon.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  List<Customer> findByDeletedAtIsNullOrderByIdDesc();
  List<Customer> findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByIdDesc(String name);
}
