package com.medeil.purchaseservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medeil.purchaseservice.feign.dto.ProductResponse;
import com.medeil.purchaseservice.util.ApiResponse;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ApiResponse<ProductResponse> getProduct(@PathVariable("id") Long id);

}
