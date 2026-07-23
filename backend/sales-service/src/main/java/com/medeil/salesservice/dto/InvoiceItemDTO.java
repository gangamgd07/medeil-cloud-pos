package com.medeil.salesservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InvoiceItemDTO {

	    private Long productId;

	    private String productName;

	    private String batchNumber;

	    private Integer quantity;

	    private BigDecimal sellingPrice;

	    private Double gst;

	    private BigDecimal total;
}
