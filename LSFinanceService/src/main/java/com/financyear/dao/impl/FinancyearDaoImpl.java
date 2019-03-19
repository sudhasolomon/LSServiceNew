package com.financyear.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.financyear.dao.FinancyearDao;
import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;
import com.financyear.utils.Constants;
import com.financyear.utils.Utils;

@Repository
public class FinancyearDaoImpl extends GenericDaoImpl<Customer, Integer> implements FinancyearDao{

	public FinancyearDaoImpl() {
		super(Customer.class);
	}

	public List<CustomerDto> getCustomerDetailsByType(String viewType) {
		List<CustomerDto> customerDtos = null;
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		if(!Utils.isBlank(viewType)){
			 hql.append("select c.customerId,c.name, c.phoneNumber, c.totalAmount, c.paid, c.due, c.createdBy, c.startDate, c.updatedOn, c.createdOn from Customer c");
			hql.append(" where c.financeType = :type");
			params.put("type" , viewType);
			hql.append(" and c.financeStatus = :status");
			params.put("status",Constants.STARTED);
			hql.append(" and c.deleteFlag = '"+0+"' ");
			hql.append(" order by COALESCE(c.updatedOn,c.createdOn) DESC");
		}
		List<?> customerList = findHqlListByParams(hql.toString(),params);
		if(customerList != null){
			 customerDtos = new ArrayList<CustomerDto>();
		Iterator<?> itr = customerList.iterator();
		while(itr.hasNext()){
			CustomerDto customerDto = new CustomerDto();
			Object customer[] = (Object[]) itr.next();
			Integer CustomerId = (Integer) customer[0];
			customerDto.setCustomerId(CustomerId != null ? CustomerId.toString(): null);
			customerDto.setName(Utils.getStringValueOfObj(customer[1]));
			customerDto.setPhone(Utils.getStringValueOfObj(customer[2]));
			Double totalAmount = (Double) customer[3];
			customerDto.setTotalAmount(totalAmount != null ? String.valueOf(totalAmount) : null);
			 totalAmount = (Double) customer[4];
			customerDto.setPaid(totalAmount != null ? String.valueOf(totalAmount) : null);
			 totalAmount = (Double) customer[5];
			customerDto.setDue(totalAmount != null ? String.valueOf(totalAmount) : null);
			customerDto.setCreatedBy(Utils.getStringValueOfObj(customer[6]));
			Date StartDate = (Date) customer[7];
			customerDto.setStartDate(Utils.convertDateToStringWithSlash(StartDate));
			customerDtos.add(customerDto);
		}
		}
		return customerDtos;
	}

	 
}
