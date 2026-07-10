package com.medeil.inventoryservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private Long inventoryId;

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotBlank(message = "Batch Number is required")
    private String batchNumber;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Minimum Stock is required")
    @Min(value = 0, message = "Minimum Stock cannot be negative")
    private Integer minimumStock;

    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    private String warehouseLocation;

    private Boolean status;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}