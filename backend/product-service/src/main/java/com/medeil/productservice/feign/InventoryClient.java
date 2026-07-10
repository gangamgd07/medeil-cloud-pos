package com.medeil.productservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medeil.productservice.config.FeignConfig;
import com.medeil.productservice.dto.feign.InventoryResponse;
import com.medeil.productservice.util.ApiResponse;

@FeignClient(
	    name = "inventory-service",
	    configuration = FeignConfig.class
	)
public interface InventoryClient {

    @GetMapping("/api/inventory/product/{productId}")
    ApiResponse<List<InventoryResponse>> getInventoryByProductId(
            @PathVariable("productId") Long productId);

}
