package com.financyear.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.CustomerAccountsDao;
import com.financyear.dto.CustomerAccountsDto;
import com.financyear.dto.CustomerDto;
import com.financyear.model.CustomerAccounts;
import com.financyear.service.CustomerAccountService;

@Service
@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {

	@Autowired
	private CustomerAccountsDao customerAcDao;
	
	
	public void saveCustomerAccounts(CustomerAccounts customerAccounts) {
		 customerAcDao.save(customerAccounts); 
	}

	public List<CustomerAccounts> getCustomerAccountsBYId(Integer parseInt) {
		 
		return customerAcDao.getCustomerAccountsBYId(parseInt);
	}

	@Override
	public List<CustomerDto> getpostponedcustomerdetails(HttpServletRequest request) {
		
		return customerAcDao.getpostponedcustomerdetails(request);
	}

}
