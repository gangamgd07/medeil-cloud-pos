package com.medeil.salesservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.medeil.salesservice.dto.InvoiceDTO;
import com.medeil.salesservice.dto.SalesDTO;
import com.medeil.salesservice.dto.SalesDashboardDTO;
import com.medeil.salesservice.dto.report.TopSellingProductDTO;
import com.medeil.salesservice.enums.SaleStatus;

public interface SalesService {
	
	SalesDTO createSale(SalesDTO dto);
	
	SalesDTO getSaleById(Long id);

	Page<SalesDTO> getAllSales(
	        int page,
	        int size,
	        String sortBy,
	        String direction,
	        SaleStatus status);

	SalesDTO updateSale(Long id, SalesDTO dto);

	SalesDTO cancelSale(Long id);
	
	InvoiceDTO getInvoice(Long saleId);
	
	SalesDTO getByInvoiceNumber(String invoiceNumber);

	List<SalesDTO> getByCustomer(Long customerId);

	List<SalesDTO> getByDate(LocalDate date);

	List<SalesDTO> getByDateRange(LocalDate from, LocalDate to);

	List<SalesDTO> getByPaymentMethod(String paymentMethod);

	List<SalesDTO> getByStatus(SaleStatus status);
	
	SalesDashboardDTO getDashboard();
	
	List<TopSellingProductDTO> getTopSellingProducts(int limit);

}
