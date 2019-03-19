package com.financyear.service;

import java.util.List;

import com.financyear.dto.CustomerDto;

public interface FinancyearService {

	public	List<CustomerDto> getCustomerDetailsByType(String viewType);

}
