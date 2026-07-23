package com.medeil.salesservice.dto;

import java.math.BigDecimal;

import com.medeil.salesservice.entity.Sale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleItemDTO {
	
    private Long saleItemId;
	
    @NotNull(message = "Product Id is required")
	private Long productId;
	
    @NotBlank(message = "Batch Number is required")
	private String batchNumber;
	
    @NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;
	
    @NotNull(message = "Selling Price is required")
	@PositiveOrZero(message = "Selling Price cannot be negative")
	private BigDecimal sellingPrice;
	
    @NotNull(message = "GST is required")
	@PositiveOrZero(message = "GST cannot be negative")
	private Double gst;
	
    @NotNull(message = "Total is required")
	@PositiveOrZero(message = "Total cannot be negative")
	private BigDecimal total;
	

}
