package com.medeil.salesservice.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medeil.salesservice.dto.report.DailySalesReportDTO;
import com.medeil.salesservice.dto.report.SalesSummaryDTO;
import com.medeil.salesservice.dto.report.TopSellingProductDTO;
import com.medeil.salesservice.entity.Sale;
import com.medeil.salesservice.enums.SaleStatus;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long>{

	boolean existsByInvoiceNumber(String invoiceNumber);

    Optional<Sale> findByInvoiceNumber(String invoiceNumber); 

    List<Sale> findByCustomerId(Long customerId);

    List<Sale> findBySalesDate(LocalDate salesDate);

    Page<Sale> findByStatus(SaleStatus status, Pageable pageable);
    
    List<Sale> findByStatus(SaleStatus status);
    
    List<Sale> findByPaymentMethod(String paymentMethod);
    
    List<Sale> findBySalesDateBetween(LocalDate from, LocalDate to);
    
    @Query("""
    		SELECT new com.medeil.salesservice.dto.report.DailySalesReportDTO(
    		    :date,
    		    COUNT(s),
    		    COALESCE(SUM(s.netAmount),0)
    		)
    		FROM Sale s
    		WHERE s.salesDate = :date
    		AND s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    		""")
    DailySalesReportDTO getDailySales(
    		        @Param("date") LocalDate date);



    @Query("""
    		SELECT new com.medeil.salesservice.dto.report.SalesSummaryDTO(
    		    COUNT(s),
    		    COALESCE(SUM(s.totalAmount),0),
    		    COALESCE(SUM(s.discount),0),
    		    COALESCE(SUM(s.taxAmount),0),
    		    COALESCE(SUM(s.netAmount),0)
    		)
    		FROM Sale s
    		WHERE s.salesDate BETWEEN :from AND :to
    		AND s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    		""")
    SalesSummaryDTO getSalesSummary(
    		        @Param("from") LocalDate from,
    		        @Param("to") LocalDate to);
    
    
    @Query("""
    		SELECT new com.medeil.salesservice.dto.report.TopSellingProductDTO(
    		        si.productId,
    		        SUM(si.quantity)
    		)
    		FROM Sale s
    		JOIN s.saleItems si
    		WHERE s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    		GROUP BY si.productId
    		ORDER BY SUM(si.quantity) DESC
    		""")
    List<TopSellingProductDTO> getTopSellingProducts(Pageable pageable);
    
    
    @Query("""
    	       SELECT COUNT(s)
    	       FROM Sale s
    	       WHERE s.salesDate = :date
    	       AND s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    	       """)
    Long countTodaySales(
    	        @Param("date") LocalDate date
    	);
    
    
    @Query("""
    	       SELECT COALESCE(SUM(s.netAmount),0)
    	       FROM Sale s
    	       WHERE s.salesDate = :date
    	       AND s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    	       """)
    BigDecimal getTodayRevenue(
    	        @Param("date") LocalDate date
    	);
    
    
    @Query("""
    	       SELECT COALESCE(SUM(s.netAmount),0)
    	       FROM Sale s
    	       WHERE s.salesDate BETWEEN :startDate AND :endDate
    	       AND s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    	       """)
    BigDecimal getMonthlyRevenue(
    	        @Param("startDate") LocalDate startDate,
    	        @Param("endDate") LocalDate endDate
    	);
    
    
    @Query("""
    	       SELECT COUNT(s)
    	       FROM Sale s
    	       WHERE s.status =
    	       com.medeil.salesservice.enums.SaleStatus.CANCELLED
    	       """)
    Long countCancelledSales();
    
    
    @Query("""
    	       SELECT COUNT(DISTINCT s.customerId)
    	       FROM Sale s
    	       """)
    Long countTotalCustomers();
    
    
    @Query("""
    	       SELECT 
    	          si.productId,
    	          SUM(si.quantity)
    	       FROM SaleItem si
    	       JOIN si.sale s
    	       WHERE s.status = com.medeil.salesservice.enums.SaleStatus.COMPLETED
    	       GROUP BY si.productId
    	       ORDER BY SUM(si.quantity) DESC
    	       """)
   List<Object[]> findTopSellingProducts(Pageable pageable);

}
