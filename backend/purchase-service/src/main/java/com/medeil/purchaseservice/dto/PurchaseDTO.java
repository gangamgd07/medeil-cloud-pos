package com.medeil.purchaseservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDTO {

	
	private Long purchaseId;

	private String purchaseNumber;

	@NotNull(message = "Supplier Id is required")
	private Long supplierId;

	@NotNull(message = "Purchase Date is required")
	private LocalDate purchaseDate;

	@PositiveOrZero(message = "Total Amount cannot be negative")
	private BigDecimal totalAmount;

	@PositiveOrZero(message = "Discount cannot be negative")
	private BigDecimal discount;

	@PositiveOrZero(message = "Tax Amount cannot be negative")
	private BigDecimal taxAmount;

	@PositiveOrZero(message = "Net Amount cannot be negative")
	private BigDecimal netAmount;

	@Size(max = 500, message = "Remarks cannot exceed 500 characters")
	private String remarks;

	@NotNull(message = "Status is required")
	private Boolean status;

	@Valid
	@NotEmpty(message = "Purchase must contain at least one item")
	private List<PurchaseItemDTO> purchaseItems;
	
}
