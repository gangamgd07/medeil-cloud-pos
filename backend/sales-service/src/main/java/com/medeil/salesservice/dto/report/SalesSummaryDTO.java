package com.medeil.salesservice.dto.report;



import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesSummaryDTO {

	    private Long totalSales;

	    private BigDecimal grossAmount;

	    private BigDecimal discount;

	    private BigDecimal taxAmount;

	    private BigDecimal netAmount;
	    
	
}
