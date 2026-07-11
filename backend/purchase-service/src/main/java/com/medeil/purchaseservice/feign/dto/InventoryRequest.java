package com.medeil.purchaseservice.feign.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {

	private Long productId;

	private String batchNumber;

	private Integer quantity;

	private Integer minimumStock;

	private LocalDate manufactureDate;

	private LocalDate expiryDate;

	private String warehouseLocation;

	private Boolean status;
}
