package com.aromatripnippon.repository;

import com.aromatripnippon.entity.InventoryItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
  Optional<InventoryItem> findByIdAndDeletedAtIsNull(Long id);
  List<InventoryItem> findByDeletedAtIsNullOrderByIdDesc();
  List<InventoryItem> findByDeletedAtIsNullAndItemNameContainingIgnoreCaseOrderByIdDesc(String itemName);
}
