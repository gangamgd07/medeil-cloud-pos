package com.medeil.inventoryservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medeil.inventoryservice.dto.InventoryDTO;
import com.medeil.inventoryservice.entity.Inventory;
import com.medeil.inventoryservice.exception.ResourceNotFoundException;
import com.medeil.inventoryservice.mapper.InventoryMapper;
import com.medeil.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService{
	
	private final InventoryRepository repository;
    private final InventoryMapper mapper;

	@Override
	public InventoryDTO save(InventoryDTO dto) {
		 if(repository.existsByBatchNumber(dto.getBatchNumber())) {
		        throw new RuntimeException("Batch Number already exists");
		    }

		    Inventory inventory = mapper.toEntity(dto);

		    Inventory saved = repository.save(inventory);

		    return mapper.toDTO(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public InventoryDTO getById(Long id) {
		 Inventory inventory = repository.findById(id)
		            .orElseThrow(() ->
		                    new RuntimeException("Inventory not found"));

		    return mapper.toDTO(inventory);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryDTO> getAll() {
		return repository.findAll()
	            .stream()
	            .map(mapper::toDTO)
	            .toList();
	}

	@Override
	public InventoryDTO update(Long id, InventoryDTO dto) {
		Inventory inventory = repository.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Inventory not found"));

	    mapper.updateEntityFromDTO(dto, inventory);

	    Inventory updated = repository.save(inventory);

	    return mapper.toDTO(updated);
	}

	@Override
	public void delete(Long id) {
		 Inventory inventory = repository.findById(id)
		            .orElseThrow(() ->
		                    new RuntimeException("Inventory not found"));

		    repository.delete(inventory);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryDTO> getByProductId(Long productId) {
		return repository.findByProductId(productId)
	            .stream()
	            .map(mapper::toDTO)
	            .toList();
	}

	@Override
	@Transactional(readOnly = true)
	public InventoryDTO getByBatchNumber(String batchNumber) {
		Inventory inventory = repository.findByBatchNumber(batchNumber)
	            .orElseThrow(() ->
	                    new RuntimeException("Batch Number not found"));

	    return mapper.toDTO(inventory);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryDTO> getLowStock(Integer quantity) {
		return repository.findByQuantityLessThanEqual(quantity)
	            .stream()
	            .map(mapper::toDTO)
	            .toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<InventoryDTO> getExpiredProducts() {
		return repository.findByExpiryDateBefore(LocalDate.now())
	            .stream()
	            .map(mapper::toDTO)
	            .toList();
	}

	@Override
	@Transactional
	public InventoryDTO addStock(InventoryDTO dto) {
		 Optional<Inventory> optionalInventory =
		            repository.findByProductIdAndBatchNumber(
		                    dto.getProductId(),
		                    dto.getBatchNumber());

		    Inventory inventory;

		    if (optionalInventory.isPresent()) {

		        // Existing Batch → Increase Quantity
		        inventory = optionalInventory.get();

		        inventory.setQuantity(
		                inventory.getQuantity() + dto.getQuantity());

		    } else {

		        // New Batch → Create Inventory
		        inventory = mapper.toEntity(dto);

		    }

		    Inventory savedInventory = repository.save(inventory);

		    return mapper.toDTO(savedInventory);
	}

}
