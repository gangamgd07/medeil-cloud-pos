package com.medeil.productservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medeil.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	 Optional<Product> findByProductCode(String productCode);

	 boolean existsByProductCode(String productCode);

}
