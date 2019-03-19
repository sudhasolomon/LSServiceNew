package com.financyear.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.FinancyearDao;
import com.financyear.dao.impl.FinancyearDaoImpl;
import com.financyear.dto.CustomerDto;
import com.financyear.service.FinancyearService;

@Service
@Transactional
public class FinancyearServiceImpl implements FinancyearService{

	@Autowired
	public FinancyearDao financyearDao;

	public List<CustomerDto> getCustomerDetailsByType(String viewType) {
		return financyearDao.getCustomerDetailsByType(viewType);
	}
}
