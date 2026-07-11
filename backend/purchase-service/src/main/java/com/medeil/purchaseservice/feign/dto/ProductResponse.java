package com.medeil.purchaseservice.feign.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
	
	private Long id;
	private String productCode;
	private String productName;
	private BigDecimal purchasePrice;
	private BigDecimal sellingPrice;
	private BigDecimal mrp;
	private Double gst;
	private Boolean status;

}
