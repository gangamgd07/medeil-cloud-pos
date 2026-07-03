package com.medeil.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medeil.productservice.dto.ProductDTO;
import com.medeil.productservice.entity.Product;
import com.medeil.productservice.exception.DuplicateResourceException;
import com.medeil.productservice.exception.ResourceNotFoundException;
import com.medeil.productservice.mapper.ProductMapper;
import com.medeil.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productrep;
	
	@Autowired
	private ProductMapper mapper;
	
	    /*private final ProductRepository repository;
	    private final ProductMapper mapper;

	    public ProductServiceImpl(ProductRepository repository,
	                              ProductMapper mapper) {
	        this.repository = repository;
	        this.mapper = mapper;
	    }*/

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
	public ProductDTO getById(Long id) {
		log.info("Fetching product with ID: {}", id);
		Product product=productrep.findById(id)
							.orElseThrow(()->{
								log.error("Product not found with ID: {}", id);
								return new ResourceNotFoundException("Product not found with id: " + id);
								});
		return mapper.toDTO(product);
	}

	@Override
	public List<ProductDTO> getAll() {
		 log.info("Fetching all products");
		 List<ProductDTO> products = productrep.findAll()
		            .stream()
		            .map(mapper::toDTO)
		            .toList();

		    log.info("Total products found: {}", products.size());

		    return products;
	}
	
	@Override
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

}
