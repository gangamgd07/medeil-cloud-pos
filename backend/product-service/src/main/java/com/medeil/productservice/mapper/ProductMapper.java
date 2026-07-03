package com.medeil.productservice.mapper;

import org.mapstruct.Mapper;

import com.medeil.productservice.dto.ProductDTO;
import com.medeil.productservice.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductDTO toDTO(Product savedProduct);
	
	Product toEntity(ProductDTO dto);
}
