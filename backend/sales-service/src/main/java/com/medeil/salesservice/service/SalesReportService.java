package com.medeil.salesservice.service;

import java.time.LocalDate;
import java.util.List;

import com.medeil.salesservice.dto.report.DailySalesReportDTO;
import com.medeil.salesservice.dto.report.SalesSummaryDTO;
import com.medeil.salesservice.dto.report.TopSellingProductDTO;

public interface SalesReportService {
	
	 DailySalesReportDTO getDailyReport(
	            LocalDate date);


	    SalesSummaryDTO getSalesSummary(
	            LocalDate from,
	            LocalDate to);
	    
	    List<TopSellingProductDTO> getTopSellingProducts(int limit);
}
