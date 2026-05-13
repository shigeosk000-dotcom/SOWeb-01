package com.aromatripnippon.service;

import com.aromatripnippon.entity.Customer;
import com.aromatripnippon.repository.CustomerRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CustomerService {
  private final CustomerRepository customers;

  public CustomerService(CustomerRepository customers) {
    this.customers = customers;
  }

  public List<Customer> search(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return customers.findByDeletedAtIsNullOrderByIdDesc();
    }
    return customers.findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByIdDesc(keyword);
  }

  public Customer findActive(Long id) {
    return customers.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  @Transactional
  public Customer save(@Valid Customer customer) {
    return customers.save(customer);
  }

  @Transactional
  public void softDelete(Long id) {
    Customer customer = findActive(id);
    customer.softDelete();
    customers.save(customer);
  }
}
