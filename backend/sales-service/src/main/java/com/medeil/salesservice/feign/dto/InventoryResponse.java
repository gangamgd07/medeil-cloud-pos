package com.medeil.salesservice.feign.dto;

import java.math.BigDecimal;

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

	private String batchNumber;

	private Integer quantity;

	private BigDecimal purchasePrice;

	private BigDecimal sellingPrice;

	private BigDecimal mrp;

	private Double gst;

}
