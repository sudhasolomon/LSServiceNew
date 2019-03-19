package com.financyear.dao;

import java.util.List;

import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;

public interface FinancyearDao extends GenericDao<Customer,Integer>{

	public List<CustomerDto> getCustomerDetailsByType(String viewType);

}
