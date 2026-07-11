package com.medeil.purchaseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medeil.purchaseservice.entity.PurchaseItem;

public interface PurchaseItemRespository extends JpaRepository<PurchaseItem, Long>{

}
