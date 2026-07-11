package com.medeil.purchaseservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medeil.purchaseservice.feign.dto.InventoryRequest;
import com.medeil.purchaseservice.feign.dto.InventoryResponse;
import com.medeil.purchaseservice.util.ApiResponse;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
	
	@GetMapping("/api/inventory/product/{productId}")
	ApiResponse<List<InventoryResponse>> getInventoryByProductId(
	        @PathVariable("productId") Long productId);
	
	@PostMapping("/api/inventory/stock/add")
	ApiResponse<InventoryResponse> addStock(
	        @RequestBody InventoryRequest request);

}
