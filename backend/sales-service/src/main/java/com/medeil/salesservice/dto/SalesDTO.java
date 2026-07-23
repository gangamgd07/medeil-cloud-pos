package com.medeil.salesservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.medeil.salesservice.enums.SaleStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDTO {
	
	private Long saleId;
	
	private String invoiceNumber;
	
	@NotNull(message = "Customer Id is required")
	private Long customerId;
	
	@NotNull(message = "Sale Date is required")
	private LocalDate salesDate;
	
	@PositiveOrZero(message = "Total Amount cannot be negative")
	private BigDecimal totalAmount;
	
	@PositiveOrZero(message = "Discount cannot be negative")
	private BigDecimal discount;
	
	@PositiveOrZero(message = "Tax Amount cannot be negative")
	private BigDecimal taxAmount;
	
	@PositiveOrZero(message = "Net Amount cannot be negative")
	private BigDecimal netAmount;
	
	@NotBlank(message = "Payment Method is required")
	private String paymentMethod;
	
	@Size(max = 500, message = "Remarks cannot exceed 500 characters")
	private String remarks;
	
	@NotNull(message = "Status is required")
	private SaleStatus status;
	
	@NotEmpty(message = "Sale Items cannot be empty")
	@Valid
	private List<SaleItemDTO> saleItems;

}
