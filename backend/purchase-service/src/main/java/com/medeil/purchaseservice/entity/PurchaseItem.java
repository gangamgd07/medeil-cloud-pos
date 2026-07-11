package com.medeil.purchaseservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name="purchase_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseItemId;

	private Long productId;

	private String batchNumber;

	private Integer quantity;

	private BigDecimal purchasePrice;

	private BigDecimal sellingPrice;

	private BigDecimal mrp;

	private Double gst;

	private LocalDate manufactureDate;

	private LocalDate expiryDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;

}
