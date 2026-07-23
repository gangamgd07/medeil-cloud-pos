package com.medeil.salesservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medeil.salesservice.feign.dto.InventoryResponse;
import com.medeil.salesservice.feign.dto.StockReductionRequest;
import com.medeil.salesservice.feign.dto.StockUpdateRequest;
import com.medeil.salesservice.util.ApiResponse;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/product/{productId}")
    ApiResponse<List<InventoryResponse>> getInventoryByProductId(
            @PathVariable("productId") Long productId);
    
    @PutMapping("/api/inventory/reduce")
    ApiResponse<InventoryResponse> reduceStock(
            @RequestBody StockReductionRequest request);
    
    
    @PostMapping("/api/inventory/stock/add")
    ApiResponse<InventoryResponse> addStock(
            @RequestBody StockUpdateRequest request);

}
