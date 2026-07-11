package com.medeil.purchaseservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medeil.purchaseservice.dto.PurchaseDTO;
import com.medeil.purchaseservice.dto.PurchaseItemDTO;
import com.medeil.purchaseservice.entity.Purchase;
import com.medeil.purchaseservice.entity.PurchaseItem;
import com.medeil.purchaseservice.exception.ResourceNotFoundException;
import com.medeil.purchaseservice.feign.InventoryClient;
import com.medeil.purchaseservice.feign.ProductClient;
import com.medeil.purchaseservice.feign.dto.InventoryRequest;
import com.medeil.purchaseservice.feign.dto.ProductResponse;
import com.medeil.purchaseservice.mapper.PurchaseMapper;
import com.medeil.purchaseservice.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService{
	
	private final PurchaseRepository purchaseRepository;
	
	private final PurchaseMapper purchaseMapper;

    private final ProductClient productClient;
    
    private final InventoryClient inventoryClient;;

	@Override
	public PurchaseDTO createPurchase(PurchaseDTO dto) {
		  // Step 1 : Validate Products
        for (PurchaseItemDTO item : dto.getPurchaseItems()) {

            ProductResponse product = productClient
                    .getProduct(item.getProductId())
                    .getData();

            if (product == null) {
                throw new ResourceNotFoundException(
                        "Product not found : " + item.getProductId());
            }
        }

        // Step 2 : DTO -> Entity
        Purchase purchase = purchaseMapper.toEntity(dto);

        // Step 3 : Set Parent Reference
        purchase.getPurchaseItems().forEach(item -> item.setPurchase(purchase));

        // Step 4 : Save Purchase
        Purchase savedPurchase = purchaseRepository.save(purchase);
        
        for (PurchaseItem item : savedPurchase.getPurchaseItems()) {

        	InventoryRequest inventory = new InventoryRequest();

        	inventory.setProductId(item.getProductId());
        	inventory.setBatchNumber(item.getBatchNumber());
        	inventory.setQuantity(item.getQuantity());
        	inventory.setMinimumStock(20);
        	inventory.setManufactureDate(item.getManufactureDate());
        	inventory.setExpiryDate(item.getExpiryDate());
        	inventory.setWarehouseLocation("Warehouse-A");
        	inventory.setStatus(true);

        	inventoryClient.addStock(inventory);
        }

        // Step 5 : Return DTO
        return purchaseMapper.toDTO(savedPurchase);
	}

	@Override
	@Transactional(readOnly = true)
	public PurchaseDTO getPurchase(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseDTO> getAllPurchases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseDTO updatePurchase(Long id, PurchaseDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePurchase(Long id) {
		// TODO Auto-generated method stub
		
	}

}
