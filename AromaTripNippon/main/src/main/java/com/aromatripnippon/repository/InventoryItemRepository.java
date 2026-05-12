package com.aromatripnippon.repository;

import com.aromatripnippon.entity.InventoryItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
  List<InventoryItem> findByDeletedAtIsNullOrderByIdDesc();
  List<InventoryItem> findByDeletedAtIsNullAndItemNameContainingIgnoreCaseOrderByIdDesc(String itemName);
}
