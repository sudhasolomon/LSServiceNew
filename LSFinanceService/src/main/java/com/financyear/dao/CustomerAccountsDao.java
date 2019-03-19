package com.financyear.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.financyear.dto.CustomerAccountsDto;
import com.financyear.dto.CustomerDto;
import com.financyear.model.CustomerAccounts;

public interface CustomerAccountsDao extends GenericDao<CustomerAccounts, Integer> {

	public	List<CustomerAccounts> getCustomerAccountsBYId(Integer parseInt);

	public List<CustomerDto> getpostponedcustomerdetails(
			HttpServletRequest request);

}
