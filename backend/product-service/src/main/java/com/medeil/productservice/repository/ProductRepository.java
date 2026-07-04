package com.medeil.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.medeil.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
	
	 Optional<Product> findByProductCode(String productCode);

	 boolean existsByProductCode(String productCode);
	 
	 List<Product> findByProductNameContainingIgnoreCase(String productName);

	 List<Product> findByCategoryIgnoreCase(String category);

	 List<Product> findByBrandIgnoreCase(String brand);

	 List<Product> findByStatus(Boolean status);

	 Optional<Product> findByBarcode(String barcode);

}
