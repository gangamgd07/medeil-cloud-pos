package com.medeil.productservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {
	
	    private Long id;

	    @NotBlank(message = "Product code is required")
	    private String productCode;

	    @NotBlank(message = "Product name is required")
	    private String productName;

	    private String genericName;

	    private String brand;

	    private String category;

	    private String manufacturer;

	    @NotNull(message = "Purchase price is required")
	    @DecimalMin(value = "0.0")
	    private BigDecimal purchasePrice;

	    @NotNull(message = "Selling price is required")
	    @DecimalMin(value = "0.0")
	    private BigDecimal sellingPrice;

	    private BigDecimal mrp;

	    private Double gst;

	    private String barcode;

	    private Boolean status;
	    @CreatedDate
	    private LocalDateTime createdDate;
	    @LastModifiedDate
	    private LocalDateTime updatedDate;

}
