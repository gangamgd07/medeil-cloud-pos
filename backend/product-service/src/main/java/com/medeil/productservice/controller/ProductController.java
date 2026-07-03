package com.medeil.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medeil.productservice.dto.ProductDTO;
import com.medeil.productservice.service.ProductService;
import com.medeil.productservice.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

	@Autowired
	private ProductService productser;
	
	@PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>>  saveProduct(
            @Valid @RequestBody ProductDTO dto) {

        //return new ResponseEntity<>(productser.save(dto),HttpStatus.CREATED);
		ProductDTO saved = productser.save(dto);

		return new ResponseEntity<>(

		        new ApiResponse<>(

		                true,

		                "Product Created Successfully",

		                saved

		        ),

		        HttpStatus.CREATED

		);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(
            @PathVariable("id") Long id) {

        //return ResponseEntity.ok(productser.getById(id));
    	ProductDTO dto = productser.getById(id);

    	return ResponseEntity.ok(

    	        new ApiResponse<>(

    	                true,

    	                "Product Found",

    	                dto

    	        )

    	);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        return ResponseEntity.ok(productser.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductDTO dto) {

        return ResponseEntity.ok(
        		productser.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("id") Long id) {

    	productser.delete(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

}
