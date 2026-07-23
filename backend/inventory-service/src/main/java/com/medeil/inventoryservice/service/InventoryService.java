package com.medeil.inventoryservice.service;

import java.util.List;

import com.medeil.inventoryservice.dto.InventoryDTO;

public interface InventoryService {

    InventoryDTO save(InventoryDTO dto);

    InventoryDTO getById(Long id);

    List<InventoryDTO> getAll();

    InventoryDTO update(Long id, InventoryDTO dto);

    void delete(Long id);

    List<InventoryDTO> getByProductId(Long productId);

    InventoryDTO getByBatchNumber(String batchNumber);

    List<InventoryDTO> getLowStock(Integer quantity);

    List<InventoryDTO> getExpiredProducts();
    
    InventoryDTO addStock(InventoryDTO dto);
    
    InventoryDTO reduceStock(InventoryDTO dto);
}