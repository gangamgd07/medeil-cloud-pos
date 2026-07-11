package com.medeil.purchaseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medeil.purchaseservice.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
