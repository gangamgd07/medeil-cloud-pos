package com.medeil.purchaseservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name="purchase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseId;

	private String purchaseNumber;

	private Long supplierId;

	private LocalDate purchaseDate;

	private BigDecimal totalAmount;

	private BigDecimal discount;

	private BigDecimal taxAmount;

	private BigDecimal netAmount;

	private String remarks;

	private Boolean status;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;
	
	@OneToMany(
		    mappedBy = "purchase",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	private List<PurchaseItem> purchaseItems = new ArrayList<>();
	
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
