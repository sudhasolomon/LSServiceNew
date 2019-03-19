package com.financyear.service;

import java.util.Date;
import java.util.List;

import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;

public interface CustomerService {
	
	public List<CustomerDto> getCustomer();

	public Customer saveCustomer(Customer customer);
	
	public Customer getCustomerById(Integer customerId);

	public void updateCustomer(Customer customer);

	public List<CustomerDto> getCustomerByMonth(Date startDate, Date endDate, String type);

	public CustomerDto getCustomerAccountsByMonth(Date startDate,
			Date endDate, String type);

	public CustomerDto getCustomerAccounts();
}
