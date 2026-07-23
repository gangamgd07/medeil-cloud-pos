package com.medeil.salesservice.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopSellingProductDTO {
	
	 private Long productId;

	 private Long totalQuantity;

}
