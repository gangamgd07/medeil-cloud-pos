package com.medeil.salesservice.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockReductionRequest {

	private Long productId;

	private String batchNumber;

	private Integer quantity;
}
