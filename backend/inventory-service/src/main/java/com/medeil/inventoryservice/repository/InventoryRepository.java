package com.medeil.inventoryservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medeil.inventoryservice.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByBatchNumber(String batchNumber);

    List<Inventory> findByProductId(Long productId);

    List<Inventory> findByStatus(Boolean status);

    List<Inventory> findByQuantityLessThanEqual(Integer quantity);

    List<Inventory> findByExpiryDateBefore(LocalDate date);

    boolean existsByBatchNumber(String batchNumber);
}