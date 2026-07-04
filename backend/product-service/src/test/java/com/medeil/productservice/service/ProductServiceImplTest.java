package com.medeil.productservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medeil.productservice.dto.ProductDTO;
import com.medeil.productservice.entity.Product;
import com.medeil.productservice.exception.ResourceNotFoundException;
import com.medeil.productservice.mapper.ProductMapper;
import com.medeil.productservice.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productrep;
	
	@Spy
	private ProductMapper mapper=Mappers.getMapper(ProductMapper.class);
	
	@InjectMocks
	private ProductServiceImpl productser;
	
	@Test
	void shouldSaveProduct() {

	    ProductDTO dto = new ProductDTO();

	    dto.setProductCode("PCM001");
	    dto.setProductName("Paracetamol");

	    Product entity = mapper.toEntity(dto);

	    when(productrep.save(any(Product.class)))
	            .thenReturn(entity);
	    
	    when(productrep.existsByProductCode("PCM001"))
        .thenReturn(false);

	    ProductDTO saved = productser.save(dto);

	    assertNotNull(saved);

	    assertEquals("PCM001", saved.getProductCode());
	    
	    
	    verify(productrep, times(1)).save(any(Product.class));

	}
	
	
	@Test
	void shouldReturnProductById() {

	    Product product = Product.builder()

	            .id(1L)

	            .productCode("PCM001")

	            .productName("Paracetamol")

	            .build();

	    when(productrep.findById(1L))

	            .thenReturn(Optional.of(product));

	    ProductDTO dto = productser.getById(1L);

	    assertEquals("PCM001", dto.getProductCode());

	}
	
	@Test
	void shouldThrowExceptionWhenProductNotFound() {

	    when(productrep.findById(100L))

	            .thenReturn(Optional.empty());

	    assertThrows(

	            ResourceNotFoundException.class,

	            () -> productser.getById(100L)

	    );

	}
	
	@Test
	void shouldDeleteProduct() {

	    Product product = Product.builder()

	            .id(1L)

	            .build();

	    when(productrep.findById(1L))

	            .thenReturn(Optional.of(product));

	    productser.delete(1L);

	    verify(productrep).delete(product);

	}
	
}
