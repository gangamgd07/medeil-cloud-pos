package com.medeil.salesservice.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySalesReportDTO {

	    private LocalDate salesDate;

	    private Long totalSales;

	    private BigDecimal totalAmount;
	    

}
