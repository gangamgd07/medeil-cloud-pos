package com.medeil.productservice.service;

import java.util.List;

import com.medeil.productservice.dto.ProductDTO;

public interface ProductService {

	ProductDTO save(ProductDTO dto);

    ProductDTO getById(Long id);

    List<ProductDTO> getAll();

    ProductDTO update(Long id, ProductDTO dto);

    void delete(Long id);
}
