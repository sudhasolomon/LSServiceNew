package com.financyear.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.CustomerDao;
import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;
import com.financyear.service.CustomerService;
import com.financyear.utils.TransformEntityToDto;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	public List<CustomerDto> getCustomer() {
		List<Customer> customer = customerDao.getCustomer();
		
		return TransformEntityToDto.getCustomerDetails(customer);
	}

	public Customer saveCustomer(Customer customer) {
		Customer customerObj = customerDao.save(customer);
		return customerObj;
	}

	public Customer getCustomerById(Integer customerId) {
		 
		return customerDao.getCustomerById(customerId);
	}

	public void updateCustomer(Customer customer) {
		customerDao.update(customer);
		
	}

	public List<CustomerDto> getCustomerByMonth(Date startDate, Date endDate, String type) {
		
		List<Customer> customer = customerDao.getCustomerByMonth(startDate, endDate, type);
		
		return TransformEntityToDto.getCustomerDetails(customer);
	}

	public CustomerDto getCustomerAccountsByMonth(Date startDate,
			Date endDate, String type) {
		List<Customer> customer = customerDao.getCustomerByMonth(startDate, endDate, type);
		return TransformEntityToDto.getCustomerAccountsByMonth(customer);
	}

	public CustomerDto getCustomerAccounts() {
		List<Customer> customer = customerDao.getCustomer();
		
		return TransformEntityToDto.getCustomerAccountsByMonth(customer);
	}

}
