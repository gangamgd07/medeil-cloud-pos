package com.medeil.salesservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDashboardDTO {

	private Long todaySales;

    private BigDecimal todayRevenue;

    private BigDecimal monthlyRevenue;

    private Long cancelledSales;

    private Long totalCustomers;
}
