package com.financyear.dao;

import java.util.Date;
import java.util.List;

import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;

public interface CustomerDao extends GenericDao<Customer, Integer> {
	public List<Customer> getCustomer();

	public Customer getCustomerById(Integer customerId);

	public List<Customer> getCustomerByMonth(Date startDate, Date endDate, String type);

}
