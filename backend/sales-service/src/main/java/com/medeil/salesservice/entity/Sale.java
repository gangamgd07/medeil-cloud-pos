package com.medeil.salesservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.medeil.salesservice.enums.SaleStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long saleId;
	
	private String invoiceNumber;
	
	private Long customerId;
	
	private LocalDate salesDate;
	
	private BigDecimal totalAmount;
	
	private BigDecimal discount;
	
	private BigDecimal taxAmount;
	
	private BigDecimal netAmount;
	
	private String paymentMethod;
	
	private String remarks;
	
	//private Boolean status;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SaleStatus status;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	@OneToMany(
		    mappedBy = "sale",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	private List<SaleItem> saleItems = new ArrayList<>();
	
	    @PrePersist
	    public void prePersist() {
	        createdDate = LocalDateTime.now();
	        updatedDate = LocalDateTime.now();
	    }

	    @PreUpdate
	    public void preUpdate() {
	        updatedDate = LocalDateTime.now();
	    }
	

}
