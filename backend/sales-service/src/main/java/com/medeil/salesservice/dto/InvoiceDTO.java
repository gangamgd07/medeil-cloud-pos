package com.medeil.salesservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class InvoiceDTO {

	    private String invoiceNumber;

	    private Long customerId;

	    private LocalDate salesDate;

	    private String paymentMethod;

	    private String remarks;

	    private BigDecimal totalAmount;

	    private BigDecimal discount;

	    private BigDecimal taxAmount;

	    private BigDecimal netAmount;

	    private List<InvoiceItemDTO> items;
}
