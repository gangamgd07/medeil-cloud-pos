package com.medeil.salesservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.medeil.salesservice.dto.report.DailySalesReportDTO;
import com.medeil.salesservice.dto.report.SalesSummaryDTO;
import com.medeil.salesservice.dto.report.TopSellingProductDTO;
import com.medeil.salesservice.repository.SalesRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {


    private final SalesRepository repository;

    @Override
    public DailySalesReportDTO getDailyReport(
            LocalDate date) {


    	return repository.getDailySales(date);
    }



    @Override
    public SalesSummaryDTO getSalesSummary(
            LocalDate from,
            LocalDate to) {

    	 return repository.getSalesSummary(from, to);
    }

	@Override
	public List<TopSellingProductDTO> getTopSellingProducts(int limit) {
		Pageable pageable =
	            PageRequest.of(0, limit);

	    return repository.getTopSellingProducts(pageable);
	}

}