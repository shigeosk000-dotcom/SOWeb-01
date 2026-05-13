package com.aromatripnippon.service;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.InventoryItem;
import com.aromatripnippon.entity.InventoryTransaction;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.InventoryItemRepository;
import com.aromatripnippon.repository.InventoryTransactionRepository;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class InventoryService {
  private final InventoryItemRepository inventoryItems;
  private final InventoryTransactionRepository transactions;
  private final AdminUserRepository admins;

  public InventoryService(InventoryItemRepository inventoryItems, InventoryTransactionRepository transactions,
      AdminUserRepository admins) {
    this.inventoryItems = inventoryItems;
    this.transactions = transactions;
    this.admins = admins;
  }

  public List<InventoryItem> search(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return inventoryItems.findByDeletedAtIsNullOrderByIdDesc();
    }
    return inventoryItems.findByDeletedAtIsNullAndItemNameContainingIgnoreCaseOrderByIdDesc(keyword);
  }

  public InventoryItem findActive(Long id) {
    return inventoryItems.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  public List<InventoryTransaction> findTransactions(Long itemId) {
    return transactions.findByDeletedAtIsNullAndInventoryItemIdOrderByTransactionDateDescIdDesc(itemId);
  }

  @Transactional
  public InventoryItem save(@Valid InventoryItem item) {
    return inventoryItems.save(item);
  }

  @Transactional
  public InventoryTransaction recordTransaction(Long itemId, String type, BigDecimal quantity, String reason,
      Principal principal) {
    InventoryItem item = findActive(itemId);
    BigDecimal signedQuantity = "OUT".equals(type) ? quantity.negate() : quantity;
    item.setStockQuantity(item.getStockQuantity().add(signedQuantity));
    if (!"OUT".equals(type)) {
      item.setLastReceivedDate(LocalDate.now());
    }

    InventoryTransaction transaction = new InventoryTransaction();
    transaction.setInventoryItem(item);
    transaction.setTransactionType(type);
    transaction.setQuantity(quantity);
    transaction.setReason(reason);
    if (principal != null) {
      admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(principal.getName()).ifPresent(transaction::setAdminUser);
    }
    inventoryItems.save(item);
    return transactions.save(transaction);
  }

  @Transactional
  public void softDelete(Long id) {
    InventoryItem item = findActive(id);
    item.softDelete();
    inventoryItems.save(item);
  }
}
