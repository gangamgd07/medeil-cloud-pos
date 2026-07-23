package com.medeil.salesservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medeil.salesservice.feign.dto.ProductResponse;
import com.medeil.salesservice.util.ApiResponse;


@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ApiResponse<ProductResponse> getProduct(@PathVariable("id") Long id);

}
