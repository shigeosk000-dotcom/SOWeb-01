package com.aromatripnippon.repository;

import com.aromatripnippon.entity.InventoryTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
  List<InventoryTransaction> findByDeletedAtIsNullOrderByIdDesc();
  List<InventoryTransaction> findByDeletedAtIsNullAndInventoryItemIdOrderByTransactionDateDescIdDesc(Long inventoryItemId);
}
