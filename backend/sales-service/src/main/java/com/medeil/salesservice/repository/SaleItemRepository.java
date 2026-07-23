package com.medeil.salesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medeil.salesservice.entity.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long>{

}
