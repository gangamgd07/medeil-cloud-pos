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
public class InventoryResponse {

	private Long inventoryId;
	private Long productId;
	private Integer quantity;
	private String batchNumber;
	private LocalDate manufactureDate;
	private LocalDate expiryDate;
	private String warehouseLocation;
	private Boolean status;
}
