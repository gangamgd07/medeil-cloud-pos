package com.medeil.salesservice.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medeil.salesservice.dto.InvoiceDTO;
import com.medeil.salesservice.dto.SalesDTO;
import com.medeil.salesservice.dto.SalesDashboardDTO;
import com.medeil.salesservice.dto.report.DailySalesReportDTO;
import com.medeil.salesservice.dto.report.SalesSummaryDTO;
import com.medeil.salesservice.dto.report.TopSellingProductDTO;
import com.medeil.salesservice.enums.SaleStatus;
import com.medeil.salesservice.service.SalesReportService;
import com.medeil.salesservice.service.SalesService;
import com.medeil.salesservice.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
	
	private final SalesService salesService;
	
	private final SalesReportService reportService;
	
	   @PostMapping
	    public ResponseEntity<ApiResponse<SalesDTO>> createSale(
	            @Valid @RequestBody SalesDTO dto) {

	        SalesDTO saved = salesService.createSale(dto);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new ApiResponse<>(
	                        true,
	                        "Sale Created Successfully",
	                        saved));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponse<SalesDTO>> getSaleById(
	            @PathVariable("id") Long id) {

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sale Found",
	                        salesService.getSaleById(id)));
	    }

	    @GetMapping
	    public ResponseEntity<ApiResponse<Page<SalesDTO>>> getAllSales(

	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "2") int size,
	            @RequestParam(defaultValue = "saleId") String sortBy,
	            @RequestParam(defaultValue = "asc") String direction,
	            @RequestParam(required = false) SaleStatus status) {

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sales List",
	                        salesService.getAllSales(page, size, sortBy, direction, status)));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse<SalesDTO>> updateSale(
	            @PathVariable("id") Long id,
	            @Valid @RequestBody SalesDTO dto) {

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sale Updated Successfully",
	                        salesService.updateSale(id, dto)));
	    }

	    @PutMapping("/{id}/cancel")
	    public ResponseEntity<ApiResponse<SalesDTO>> cancelSale(
	            @PathVariable("id") Long id){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sale Cancelled Successfully",
	                        salesService.cancelSale(id)
	                )
	        );
	    }

	    
	    @GetMapping("/report/daily")
	    public ResponseEntity<DailySalesReportDTO> dailyReport(
	            @RequestParam LocalDate date){

	        return ResponseEntity.ok(
	                reportService.getDailyReport(date)
	        );

	    }


	    @GetMapping("/report/summary")
	    public ResponseEntity<SalesSummaryDTO> summary(
	            @RequestParam LocalDate from,
	            @RequestParam LocalDate to){

	        return ResponseEntity.ok(
	                reportService.getSalesSummary(
	                        from,
	                        to)
	        );

	    }
	    
	    @GetMapping("/report/top-selling")
	    public ResponseEntity<List<TopSellingProductDTO>> topSellingProducts(

	            @RequestParam(defaultValue = "10") int limit){

	        return ResponseEntity.ok(
	                reportService.getTopSellingProducts(limit));
	    }
	    
	    @GetMapping("/{id}/invoice")
	    public ResponseEntity<ApiResponse<InvoiceDTO>> getInvoice(
	            @PathVariable("id") Long id){

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Invoice fetched successfully",
	                        salesService.getInvoice(id)
	                )
	        );
	    }
	    
	    @GetMapping("/search/invoice/{invoiceNumber}")
	    public ResponseEntity<ApiResponse<SalesDTO>> searchInvoice(
	            @PathVariable("invoiceNumber") String invoiceNumber){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sale Found",
	                        salesService.getByInvoiceNumber(invoiceNumber)
	                )
	        );
	    }
	    
	    @GetMapping("/search/customer/{customerId}")
	    public ResponseEntity<ApiResponse<List<SalesDTO>>> searchCustomer(
	            @PathVariable("customerId") Long customerId){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sales Found",
	                        salesService.getByCustomer(customerId)
	                )
	        );
	    }
	    
	    @GetMapping("/search/date/{date}")
	    public ResponseEntity<ApiResponse<List<SalesDTO>>> searchDate(
	            @PathVariable("date") LocalDate date){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sales Found",
	                        salesService.getByDate(date)
	                )
	        );
	    }
	    
	    @GetMapping("/search/date-range")
	    public ResponseEntity<ApiResponse<List<SalesDTO>>> searchDateRange(
	            @RequestParam LocalDate from,
	            @RequestParam LocalDate to){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sales Found",
	                        salesService.getByDateRange(from,to)
	                )
	        );
	    }
	    
	    @GetMapping("/search/payment/{paymentMethod}")
	    public ResponseEntity<ApiResponse<List<SalesDTO>>> searchPayment(
	            @PathVariable("paymentMethod") String paymentMethod){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sales Found",
	                        salesService.getByPaymentMethod(paymentMethod)
	                )
	        );
	    }
	    
	    @GetMapping("/search/status/{status}")
	    public ResponseEntity<ApiResponse<List<SalesDTO>>> searchStatus(
	            @PathVariable("status") SaleStatus status){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Sales Found",
	                        salesService.getByStatus(status)
	                )
	        );
	    }
	    
	    
	    @GetMapping("/dashboard")
	    public ResponseEntity<ApiResponse<SalesDashboardDTO>> dashboard(){


	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Dashboard Data",
	                        salesService.getDashboard()
	                )
	        );

	    }
	    
	    @GetMapping("/dashboard/top-products")
	    public ResponseEntity<List<TopSellingProductDTO>> topSellingProductsdash(
	            @RequestParam(defaultValue = "5") int limit) {


	        return ResponseEntity.ok(
	                reportService.getTopSellingProducts(limit)
	        );

	    }

}
