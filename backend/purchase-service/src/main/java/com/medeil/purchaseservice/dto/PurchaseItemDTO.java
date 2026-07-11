package com.medeil.purchaseservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseItemDTO {
	
	private Long purchaseItemId;

	@NotNull(message = "Product Id is required")
	private Long productId;

	@NotBlank(message = "Batch Number is required")
	private String batchNumber;

	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

	@NotNull(message = "Purchase Price is required")
	@PositiveOrZero(message = "Purchase Price cannot be negative")
	private BigDecimal purchasePrice;

	@NotNull(message = "Selling Price is required")
	@PositiveOrZero(message = "Selling Price cannot be negative")
	private BigDecimal sellingPrice;

	@NotNull(message = "MRP is required")
	@PositiveOrZero(message = "MRP cannot be negative")
	private BigDecimal mrp;

	@NotNull(message = "GST is required")
	@PositiveOrZero(message = "GST cannot be negative")
	private Double gst;

	@NotNull(message = "Manufacture Date is required")
	private LocalDate manufactureDate;

	@NotNull(message = "Expiry Date is required")
	private LocalDate expiryDate;

}
