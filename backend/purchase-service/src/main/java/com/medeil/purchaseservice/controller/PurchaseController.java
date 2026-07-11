package com.medeil.purchaseservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medeil.purchaseservice.dto.PurchaseDTO;
import com.medeil.purchaseservice.service.PurchaseService;
import com.medeil.purchaseservice.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
	
	private final PurchaseService purchaseser;
	
	private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PurchaseDTO>>  saveProduct(
            @Valid @RequestBody PurchaseDTO dto) {
       
		PurchaseDTO saved = purchaseser.createPurchase(dto);

		return new ResponseEntity<>(

		        new ApiResponse<>(

		                true,

		                "Purchase Created Successfully",

		                saved

		        ),

		        HttpStatus.CREATED

		);
    }

}
