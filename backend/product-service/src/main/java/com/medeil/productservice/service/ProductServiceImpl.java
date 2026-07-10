package com.medeil.productservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.medeil.productservice.dto.ProductDTO;
import com.medeil.productservice.dto.feign.InventoryResponse;
import com.medeil.productservice.entity.Product;
import com.medeil.productservice.exception.DuplicateResourceException;
import com.medeil.productservice.exception.ResourceNotFoundException;
import com.medeil.productservice.feign.InventoryClient;
import com.medeil.productservice.mapper.ProductMapper;
import com.medeil.productservice.repository.ProductRepository;
import com.medeil.productservice.specification.ProductSpecification;
import com.medeil.productservice.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	 private final ProductRepository productrep;
	 
	 private final ProductMapper mapper;
	 
	 private final InventoryClient inventoryClient;

	@Override
	public ProductDTO save(ProductDTO dto) {
		log.info("Creating product with code: {}", dto.getProductCode());
		Product product=mapper.toEntity(dto);
		
		if(productrep.existsByProductCode(dto.getProductCode())){

		    throw new DuplicateResourceException("Product Code Already Exists");

		}
		
		Product savedProduct=productrep.save(product);
		log.info("Product created successfully with ID: {}", savedProduct.getId());
		return mapper.toDTO(savedProduct);
	}

	@Override
	@Cacheable(value="products", key="#id")
	public ProductDTO getById(Long id) {
		 log.info("Fetching Product From Database...");

		    Product product = productrep.findById(id)
		            .orElseThrow(() ->
		                    new ResourceNotFoundException("Product not found"));

		    // Convert Entity -> DTO
		    ProductDTO dto = mapper.toDTO(product);

		    // Call Inventory Service
		    ApiResponse<List<InventoryResponse>> inventoryResponse =
		            inventoryClient.getInventoryByProductId(id);

		    // Set Inventory List
		    if (inventoryResponse != null && inventoryResponse.getData() != null) {
		        dto.setInventories(inventoryResponse.getData());
		    }

		    return dto;
	}

	/*@Override
	public List<ProductDTO> getAll() {
		 log.info("Fetching all products");
		 List<ProductDTO> products = productrep.findAll()
		            .stream()
		            .map(mapper::toDTO)
		            .toList();

		    log.info("Total products found: {}", products.size());

		    return products;
	}*/
	
	@Override
	public Page<ProductDTO> getAll(Pageable pageable) {
		log.info("Fetching all products");
		Page<Product> page=productrep.findAll(pageable);
		log.info("Total products found: {}", page.getSize());
		return page.map(mapper::toDTO);
	}
	
	@Override
	@CachePut(value = "products", key = "#id")
	public ProductDTO update(Long id, ProductDTO dto) {
		 log.info("Updating product with ID: {}", id);
		Product product=productrep.findById(id)
				.orElseThrow(()->{
					log.error("Product not found with ID: {}", id);
	                return new ResourceNotFoundException("Product not found with id: " + id);
				});
		
		    product.setProductCode(dto.getProductCode());
	        product.setProductName(dto.getProductName());
	        product.setGenericName(dto.getGenericName());
	        product.setBrand(dto.getBrand());
	        product.setCategory(dto.getCategory());
	        product.setManufacturer(dto.getManufacturer());
	        product.setPurchasePrice(dto.getPurchasePrice());
	        product.setSellingPrice(dto.getSellingPrice());
	        product.setMrp(dto.getMrp());
	        product.setGst(dto.getGst());
	        product.setBarcode(dto.getBarcode());
	        product.setStatus(dto.getStatus());

	        Product updatedProduct = productrep.save(product);
	        
	        log.info("Product updated successfully with ID: {}", updatedProduct.getId());

	        return mapper.toDTO(updatedProduct);
	}

	@Override
	@CacheEvict(value = "products", key = "#id")
	public void delete(Long id) {
		log.info("Deleting product with ID: {}", id);
		Product product=productrep.findById(id)
				.orElseThrow(()->{
					log.error("Product not found with ID: {}", id);
	                return new ResourceNotFoundException("Product not found with id: " + id);
				});
		productrep.delete(product);
		log.info("Product deleted successfully with ID: {}", id);
	}

	@Override
	public List<ProductDTO> searchByName(String name) {

	    log.info("Searching products with name: {}", name);

	    List<Product> products = productrep.findByProductNameContainingIgnoreCase(name);

	    if (products.isEmpty()) {
	        log.error("No products found with name: {}", name);
	        throw new ResourceNotFoundException(
	                "No products found with name: " + name);
	    }

	    log.info("Found {} product(s) with name: {}", products.size(), name);

	    return products.stream()
	            .map(mapper::toDTO)
	            .toList();
	}

	@Override
	public List<ProductDTO> getByCategory(String category) {
		log.info("Searing products with category : {}", category);
		
		List<Product> products = productrep.findByCategoryIgnoreCase(category);
		
		if (products.isEmpty()) {
		    throw new ResourceNotFoundException(
		            "No products found in category: " + category);
		}
		
		log.info("Found {} product(s) with category: {}", products.size(), category);
		
		return products.stream().map(mapper::toDTO).toList();
	}

	@Override
	public List<ProductDTO> getByBrand(String brand) {
		log.info("Searing products with brand : {}", brand);
		
		List<Product> products = productrep.findByBrandIgnoreCase(brand);
		
		if (products.isEmpty()) {
		    throw new ResourceNotFoundException(
		            "No products found for brand: " + brand);
		}
		
		log.info("Found {} product(s) with brand: {}", products.size(), brand);
		
		return products.stream().map(mapper::toDTO).toList();
	}

	@Override
	public List<ProductDTO> getByStatus(Boolean status) {
		log.info("Searing products with status : {}", status);
		
		List<Product> products = productrep.findByStatus(status);
		
		if (products.isEmpty()) {
		    throw new ResourceNotFoundException(
		            "No products found with status: " + status);
		}
		
		log.info("Found {} product(s) with status: {}", products.size(), status);
		
		return products.stream().map(mapper::toDTO).toList();
	}
	@Override
	public ProductDTO getByBarcode(String barcode) {

	    log.info("Searching product with barcode: {}", barcode);

	    Product product = productrep.findByBarcode(barcode)
	            .orElseThrow(() -> {
	                log.error("Product not found with barcode: {}", barcode);
	                return new ResourceNotFoundException(
	                        "Product not found with barcode: " + barcode);
	            });

	    return mapper.toDTO(product);
	}

	@Override
	public ProductDTO getByProductCode(String productCode) {

	    log.info("Searching product with code: {}", productCode);

	    Product product = productrep.findByProductCode(productCode)
	            .orElseThrow(() -> {
	                log.error("Product not found with code: {}", productCode);
	                return new ResourceNotFoundException(
	                        "Product not found with code: " + productCode);
	            });

	    return mapper.toDTO(product);
	}

	

	/*@Override
	public List<ProductDTO> filterProducts(String category, String brand, 
			Boolean status, BigDecimal minPrice, BigDecimal maxPrice) {
		
		Specification<Product> specification =
		        Specification
		                .where(ProductSpecification.hasCategory(category))
		                .and(ProductSpecification.hasBrand(brand))
		                .and(ProductSpecification.hasStatus(status))
		                .and(ProductSpecification.minPrice(minPrice))
		                .and(ProductSpecification.maxPrice(maxPrice));

		return productrep.findAll(specification)
		                 .stream()
		                 .map(mapper::toDTO)
		                 .toList();
	}*/

	@Override
	public Page<ProductDTO> filterProducts(String category, String brand, 
			Boolean status, BigDecimal minPrice,
			BigDecimal maxPrice, Pageable pageable) {
		
		Specification<Product> specification =
		        Specification.where(ProductSpecification.hasCategory(category))
		                .and(ProductSpecification.hasBrand(brand))
		                .and(ProductSpecification.hasStatus(status))
		                .and(ProductSpecification.minPrice(minPrice))
		                .and(ProductSpecification.maxPrice(maxPrice));

		Page<Product> pageResult =
		        productrep.findAll(specification, pageable);

		return pageResult.map(mapper::toDTO);
		
	}

}
