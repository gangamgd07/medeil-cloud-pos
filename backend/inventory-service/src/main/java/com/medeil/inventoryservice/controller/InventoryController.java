package com.medeil.inventoryservice.controller;

import java.util.List;

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

import com.medeil.inventoryservice.dto.InventoryDTO;
import com.medeil.inventoryservice.service.InventoryService;
import com.medeil.inventoryservice.util.ApiResponse;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name="Bearer Authentication")
public class InventoryController {

	private final InventoryService inventoryService;
	
	    @PostMapping
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ApiResponse<InventoryDTO>> save(
	            @Valid @RequestBody InventoryDTO dto) {

	        return new ResponseEntity<>(
	                ApiResponse.success(
	                        "Inventory created successfully",
	                        inventoryService.save(dto)),
	                HttpStatus.CREATED);
	    }

	    @GetMapping("/{id}")
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public ResponseEntity<ApiResponse<InventoryDTO>> getById(
	            @PathVariable("id") Long id) {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Inventory fetched successfully",
	                        inventoryService.getById(id)));
	    }

	    @GetMapping
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getAll() {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Inventory List",
	                        inventoryService.getAll()));
	    }

	    @PutMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ApiResponse<InventoryDTO>> update(
	            @PathVariable("id") Long id,
	            @Valid @RequestBody InventoryDTO dto) {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Inventory Updated",
	                        inventoryService.update(id, dto)));
	    }

	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ApiResponse<String>> delete(
	            @PathVariable("id") Long id) {

	        inventoryService.delete(id);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Inventory Deleted",
	                        "Success"));
	    }

	    @GetMapping("/product/{productId}")
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getByProductId(
	            @PathVariable("productId") Long productId) {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Inventory fetched",
	                        inventoryService.getByProductId(productId)));
	    }

	    @GetMapping("/batch/{batchNumber}")
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public ResponseEntity<ApiResponse<InventoryDTO>> getByBatchNumber(
	            @PathVariable("batchNumber") String batchNumber) {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Inventory fetched",
	                        inventoryService.getByBatchNumber(batchNumber)));
	    }

	    @GetMapping("/low-stock")
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getLowStock(
	            @RequestParam Integer quantity) {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Low Stock Inventory",
	                        inventoryService.getLowStock(quantity)));
	    }

	    @GetMapping("/expired")
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public ResponseEntity<ApiResponse<List<InventoryDTO>>> getExpiredProducts() {

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Expired Products",
	                        inventoryService.getExpiredProducts()));
	    }
	    
	    @PostMapping("/stock/add")
	    public ApiResponse<InventoryDTO> addStock(
	            @RequestBody InventoryDTO dto) {

	        return ApiResponse.success(
	                "Stock Updated",
	                inventoryService.addStock(dto));
	    }

}
