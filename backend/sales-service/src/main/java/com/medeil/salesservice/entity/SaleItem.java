package com.medeil.salesservice.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sales_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long saleItemId;
	
	private Long productId;
	
	private String batchNumber;
	
	private Integer quantity;
	
	private BigDecimal sellingPrice;
	
	private Double gst;
	
	private BigDecimal total;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id")
	private Sale sale;

}
