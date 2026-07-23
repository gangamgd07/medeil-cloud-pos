package com.medeil.salesservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medeil.salesservice.dto.InvoiceDTO;
import com.medeil.salesservice.dto.InvoiceItemDTO;
import com.medeil.salesservice.dto.SalesDTO;
import com.medeil.salesservice.dto.SalesDashboardDTO;
import com.medeil.salesservice.dto.report.TopSellingProductDTO;
import com.medeil.salesservice.entity.Sale;
import com.medeil.salesservice.entity.SaleItem;
import com.medeil.salesservice.enums.SaleStatus;
import com.medeil.salesservice.exception.InsufficientStockException;
import com.medeil.salesservice.exception.InvalidSaleStateException;
import com.medeil.salesservice.exception.ResourceNotFoundException;
import com.medeil.salesservice.feign.InventoryClient;
import com.medeil.salesservice.feign.ProductClient;
import com.medeil.salesservice.feign.dto.InventoryResponse;
import com.medeil.salesservice.feign.dto.ProductResponse;
import com.medeil.salesservice.feign.dto.StockReductionRequest;
import com.medeil.salesservice.feign.dto.StockUpdateRequest;
import com.medeil.salesservice.mapper.SalesMapper;
import com.medeil.salesservice.repository.SalesRepository;
import com.medeil.salesservice.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesServiceImpl implements SalesService{
	
	private final SalesRepository salesRepository;

	private final SalesMapper mapper;

	private final ProductClient productClient;

	private final InventoryClient inventoryClient;


	@Override
	@Transactional
	public SalesDTO createSale(SalesDTO dto) {

	    Sale sale = mapper.toEntity(dto);
	    
	    sale.setStatus(SaleStatus.COMPLETED);

	    sale.getSaleItems().forEach(item -> item.setSale(sale));

	    // Product Validation
	    for (SaleItem item : sale.getSaleItems()) {

	        ApiResponse<ProductResponse> response =
	                productClient.getProduct(item.getProductId());

	        if (response == null || response.getData() == null) {
	            throw new ResourceNotFoundException(
	                    "Product not found with ID : " + item.getProductId());
	        }
	    }

	    // Inventory Validation
	    for (SaleItem item : sale.getSaleItems()) {

	        ApiResponse<List<InventoryResponse>> response =
	                inventoryClient.getInventoryByProductId(item.getProductId());

	        if (response == null ||
	                response.getData() == null ||
	                response.getData().isEmpty()) {

	            throw new ResourceNotFoundException(
	                    "Inventory not found for Product ID : "
	                            + item.getProductId());
	        }

	        InventoryResponse inventory = response.getData()
	                .stream()
	                .filter(i -> i.getBatchNumber().equals(item.getBatchNumber()))
	                .findFirst()
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Batch not found : " + item.getBatchNumber()));

	        if (inventory.getQuantity() < item.getQuantity()) {

	            throw new InsufficientStockException(
	                    "Insufficient stock for Product ID : "
	                            + item.getProductId());
	        }
	    }

	    // Save Sale
	    Sale savedSale = salesRepository.save(sale);

	    // Generate Invoice Number
	    savedSale.setInvoiceNumber(
	            String.format("INV%05d", savedSale.getSaleId())
	    );

	    savedSale = salesRepository.save(savedSale);

	    // Reduce Inventory
	    for (SaleItem item : savedSale.getSaleItems()) {

	        StockReductionRequest request =
	                StockReductionRequest.builder()
	                        .productId(item.getProductId())
	                        .batchNumber(item.getBatchNumber())
	                        .quantity(item.getQuantity())
	                        .build();

	        log.info(
	        		"Reducing stock ProductId:{} Batch:{} Quantity:{}",
	        		request.getProductId(),
	        		request.getBatchNumber(),
	        		request.getQuantity()
	        		);
	        
	        inventoryClient.reduceStock(request);
	    }

	    return mapper.toDTO(savedSale);
	}


	@Override
	public SalesDTO getSaleById(Long id) {
		Sale sale = salesRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Sale not found with id : " + id));

	    return mapper.toDTO(sale);
	}


	@Override
	public Page<SalesDTO> getAllSales(int page, int size, String sortBy, String direction,SaleStatus status) {
		Sort sort = direction.equalsIgnoreCase("desc")
	            ? Sort.by(sortBy).descending()
	            : Sort.by(sortBy).ascending();


	    Pageable pageable =
	            PageRequest.of(page, size, sort);
	    
	    if(status != null){

	        return salesRepository
	                .findByStatus(status,pageable)
	                .map(mapper::toDTO);

	    }

			return salesRepository
			        .findAll(pageable)
			        .map(mapper::toDTO);
	}


	@Override
	public SalesDTO updateSale(Long id, SalesDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@Transactional
	public SalesDTO cancelSale(Long id) {

	    Sale sale = salesRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Sale not found with id : " + id));

	    if (sale.getStatus() == SaleStatus.CANCELLED) {
	        throw new InvalidSaleStateException(
	                "Sale is already cancelled.");
	    }

	    for (SaleItem item : sale.getSaleItems()) {

	        StockUpdateRequest request =
	        		StockUpdateRequest.builder()
	                        .productId(item.getProductId())
	                        .batchNumber(item.getBatchNumber())
	                        .quantity(item.getQuantity())
	                        .build();
	        
	        log.info(
	        	    "Restoring stock ProductId:{} Batch:{} Quantity:{}",
	        	    item.getProductId(),
	        	    item.getBatchNumber(),
	        	    item.getQuantity()
	        	);

	        inventoryClient.addStock(request);
	    }

	    sale.setStatus(SaleStatus.CANCELLED);

	    Sale updatedSale = salesRepository.save(sale);

	    return mapper.toDTO(updatedSale);
	}


	@Override
	@Transactional(readOnly = true)
	public InvoiceDTO getInvoice(Long saleId) {

	    Sale sale = salesRepository.findById(saleId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Sale not found with id : " + saleId));

	    InvoiceDTO dto = new InvoiceDTO();

	    dto.setInvoiceNumber(sale.getInvoiceNumber());
	    dto.setCustomerId(sale.getCustomerId());
	    dto.setSalesDate(sale.getSalesDate());
	    dto.setPaymentMethod(sale.getPaymentMethod());
	    dto.setRemarks(sale.getRemarks());
	    dto.setTotalAmount(sale.getTotalAmount());
	    dto.setDiscount(sale.getDiscount());
	    dto.setTaxAmount(sale.getTaxAmount());
	    dto.setNetAmount(sale.getNetAmount());

	    List<InvoiceItemDTO> items = new ArrayList<>();

	    for (SaleItem item : sale.getSaleItems()) {

	        ApiResponse<ProductResponse> response =
	                productClient.getProduct(item.getProductId());

	        if (response == null || response.getData() == null) {
	            throw new ResourceNotFoundException(
	                    "Product not found with ID : " + item.getProductId());
	        }

	        ProductResponse product = response.getData();

	        InvoiceItemDTO invoiceItem = new InvoiceItemDTO();

	        invoiceItem.setProductId(item.getProductId());
	        invoiceItem.setProductName(product.getProductName());
	        invoiceItem.setBatchNumber(item.getBatchNumber());
	        invoiceItem.setQuantity(item.getQuantity());
	        invoiceItem.setSellingPrice(item.getSellingPrice());
	        invoiceItem.setGst(item.getGst());
	        invoiceItem.setTotal(item.getTotal());

	        items.add(invoiceItem);
	    }

	    dto.setItems(items);

	    return dto;
	}


	@Override
	@Transactional(readOnly = true)
	public SalesDTO getByInvoiceNumber(String invoiceNumber) {

	    Sale sale = salesRepository.findByInvoiceNumber(invoiceNumber)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Invoice not found : " + invoiceNumber));

	    return mapper.toDTO(sale);
	}


	@Override
	@Transactional(readOnly = true)
	public List<SalesDTO> getByCustomer(Long customerId) {

	    List<Sale> sales =
	            salesRepository.findByCustomerId(customerId);

	    if(sales.isEmpty()) {

	        throw new ResourceNotFoundException(
	                "No sales found for customer : " + customerId);
	    }


	    return sales.stream()
	            .map(mapper::toDTO)
	            .toList();
	}


	@Override
	@Transactional(readOnly = true)
	public List<SalesDTO> getByDate(LocalDate date) {

	    List<Sale> sales =
	            salesRepository.findBySalesDate(date);


	    if(sales.isEmpty()) {

	        throw new ResourceNotFoundException(
	                "No sales found on date : " + date);
	    }


	    return sales.stream()
	            .map(mapper::toDTO)
	            .toList();
	}


	@Override
	@Transactional(readOnly = true)
	public List<SalesDTO> getByDateRange(
	        LocalDate from,
	        LocalDate to) {


	    List<Sale> sales =
	            salesRepository.findBySalesDateBetween(from,to);


	    if(sales.isEmpty()) {

	        throw new ResourceNotFoundException(
	                "No sales found between "
	                + from + " and " + to);
	    }


	    return sales.stream()
	            .map(mapper::toDTO)
	            .toList();
	}


	@Override
	@Transactional(readOnly = true)
	public List<SalesDTO> getByPaymentMethod(
	        String paymentMethod) {


	    List<Sale> sales =
	            salesRepository.findByPaymentMethod(paymentMethod);


	    if(sales.isEmpty()) {

	        throw new ResourceNotFoundException(
	                "No sales found with payment method : "
	                + paymentMethod);
	    }


	    return sales.stream()
	            .map(mapper::toDTO)
	            .toList();
	}


	@Override
	@Transactional(readOnly = true)
	public List<SalesDTO> getByStatus(
	        SaleStatus status) {


	    List<Sale> sales =
	            salesRepository.findByStatus(status);


	    if(sales.isEmpty()) {

	        throw new ResourceNotFoundException(
	                "No sales found with status : "
	                + status);
	    }


	    return sales.stream()
	            .map(mapper::toDTO)
	            .toList();
	}


	 @Override
	    public SalesDashboardDTO getDashboard() {


	        LocalDate today = LocalDate.now();


	        Long todaySales =
	        		salesRepository.countTodaySales(today);



	        BigDecimal todayRevenue =
	        		salesRepository.getTodayRevenue(today);



	        LocalDate startOfMonth =
	                today.withDayOfMonth(1);



	        BigDecimal monthlyRevenue =
	        		salesRepository.getMonthlyRevenue(
	                        startOfMonth,
	                        today
	                );



	        Long cancelledSales =
	        		salesRepository.countCancelledSales();



	        Long totalCustomers =
	        		salesRepository.countTotalCustomers();



	        return new SalesDashboardDTO(

	                todaySales,

	                todayRevenue,

	                monthlyRevenue,

	                cancelledSales,

	                totalCustomers

	        );

	    }


	 @Override
	 public List<TopSellingProductDTO> getTopSellingProducts(int limit) {


	     List<Object[]> result =
	    		 salesRepository.findTopSellingProducts(
	                     PageRequest.of(0, limit)
	             );


	     return result.stream()
	             .map(row ->
	                     new TopSellingProductDTO(
	                             ((Number)row[0]).longValue(),
	                             ((Number)row[1]).longValue()
	                     )
	             )
	             .toList();

	 }

	

}
