package com.medeil.productservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
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

   /* @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        return ResponseEntity.ok(productser.getAll());
    }*/
    
    /*@GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.getAll(pageable)
                ));

    }*/
    
    /*@GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.getAll(pageable)
                )
        );
    }*/

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductDTO dto) {

        return ResponseEntity.ok(
        		productser.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("id") Long id) {

    	productser.delete(id);

        return ResponseEntity.ok("Product deleted successfully");
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.searchByName(name)
                ));
    }
    
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getByCategory(
            @RequestParam String category) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.getByCategory(category)
                ));
    }
    
    @GetMapping("/brand")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getByBrand(
            @RequestParam String brand) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.getByBrand(brand)
                ));
    }
    
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getByStatus(
            @RequestParam Boolean status) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.getByStatus(status)
                ));
    }
    
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ApiResponse<ProductDTO>> getByBarcode(
            @PathVariable String barcode) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully",
                        productser.getByBarcode(barcode)
                ));
    }
    
    @GetMapping("/code/{productCode}")
    public ResponseEntity<ApiResponse<ProductDTO>> getByProductCode(
            @PathVariable String productCode) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully",
                        productser.getByProductCode(productCode)
                ));
    }
    
   /* @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> filterProducts(

            @RequestParam(required = false) String category,

            @RequestParam(required = false) String brand,

            @RequestParam(required = false) Boolean status,

            @RequestParam(required = false) BigDecimal minPrice,

            @RequestParam(required = false) BigDecimal maxPrice) {

        return ResponseEntity.ok(

                ApiResponse.success(

                        "Filtered products",

                        productser.filterProducts(

                                category,

                                brand,

                                status,

                                minPrice,

                                maxPrice)

                )

        );

    }*/
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productser.filterProducts(
                                category,
                                brand,
                                status,
                                minPrice,
                                maxPrice,
                                pageable)));
    }

}
