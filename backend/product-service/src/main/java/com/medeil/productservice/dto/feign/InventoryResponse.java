package com.medeil.productservice.dto.feign;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

	private Long inventoryId;
	private Long productId;
	private String batchNumber;
	private Integer quantity;
	private Integer minimumStock;
	private LocalDate manufactureDate;
	private LocalDate expiryDate;
	private String warehouseLocation;
	private Boolean status;
}
