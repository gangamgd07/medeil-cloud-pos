package com.medeil.purchaseservice.service;

import java.util.List;

import com.medeil.purchaseservice.dto.PurchaseDTO;

public interface PurchaseService {

	PurchaseDTO createPurchase(PurchaseDTO dto);

    PurchaseDTO getPurchase(Long id);

    List<PurchaseDTO> getAllPurchases();

    PurchaseDTO updatePurchase(Long id, PurchaseDTO dto);

    void deletePurchase(Long id);
}
