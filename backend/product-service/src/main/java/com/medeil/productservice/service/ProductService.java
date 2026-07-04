package com.medeil.productservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.medeil.productservice.dto.ProductDTO;

public interface ProductService {

	ProductDTO save(ProductDTO dto);

    ProductDTO getById(Long id);

   // List<ProductDTO> getAll();
    
    Page<ProductDTO> getAll(Pageable pageable);

    ProductDTO update(Long id, ProductDTO dto);

    void delete(Long id);
    
    List<ProductDTO> searchByName(String name);

    List<ProductDTO> getByCategory(String category);

    List<ProductDTO> getByBrand(String brand);

    List<ProductDTO> getByStatus(Boolean status);

    ProductDTO getByBarcode(String barcode);

    ProductDTO getByProductCode(String productCode);
    
   /* List<ProductDTO> filterProducts(

            String category,

            String brand,

            Boolean status,

            BigDecimal minPrice,

            BigDecimal maxPrice);*/
    
    Page<ProductDTO> filterProducts(
            String category,
            String brand,
            Boolean status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable);
    
    
    
}
