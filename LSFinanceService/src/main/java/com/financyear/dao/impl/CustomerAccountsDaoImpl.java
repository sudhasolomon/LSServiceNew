package com.financyear.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.financyear.dao.CustomerAccountsDao;
import com.financyear.dto.CustomerDto;
import com.financyear.model.CustomerAccounts;
import com.financyear.utils.Utils;

@Repository
public class CustomerAccountsDaoImpl extends GenericDaoImpl<CustomerAccounts, Integer> implements CustomerAccountsDao{

	public CustomerAccountsDaoImpl() {
		super(CustomerAccounts.class);
	}

	@SuppressWarnings("unchecked")
	public List<CustomerAccounts> getCustomerAccountsBYId(Integer customerId) {
		List<CustomerAccounts> customerAccount = null;
		if(customerId != null){
		Map<String, Object> params = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder("from CustomerAccounts c");
		hql.append(" where c.customerId = :customerId");
		params.put("customerId", customerId);
		customerAccount = (List<CustomerAccounts>) findHqlListByParams(hql.toString(), params);
		}
		return customerAccount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> getpostponedcustomerdetails(
			HttpServletRequest request) {
		List<CustomerDto> dtos = new ArrayList<CustomerDto>();
		List<?> customerAccount = null;
		if(!Utils.isBlank(Utils.getLoginUserName(request))){
			Map<String, Object> params = new HashMap<String,Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("select c.name, c.startDate,c.totalAmount, t1.due,t1.paid,t1.postponedTo  from CustomerAccounts t1, Customer c ");
			
			sql.append(" WHERE t1.createdOn = (SELECT MAX(t2.createdOn) FROM CustomerAccounts t2 ");
			sql.append(" WHERE t2.customerId = t1.customerId and t1.paymentStatus = :status)"); 
			sql.append(" and t1.customerId = c.customerId");
			sql.append(" order by (c.updatedOn) DESC");
				params.put("status", "Postponed");
				customerAccount =  findHqlListByParams(sql.toString(), params);
				//TransformEntityToDto.getpostponedCustomerDtos(dtos,customerAccount);
				
				if(!Utils.isEmpty(customerAccount)){
					Iterator<?> itr = customerAccount.iterator();
					while(itr.hasNext()){
						CustomerDto dto = new CustomerDto();
						
						Object[] accounts = (Object[]) itr.next();
						dto.setName(Utils.getStringValueOfObj(accounts[0]));
						
						Date start = (Date) accounts[1];
						dto.setStartDate(Utils.convertDateToString_IndiaWithSlashes(start));
						
						Double total = (Double) accounts[2];
						dto.setTotalAmount(Utils.nullIfBlank(String.valueOf(Utils.round(total))));
						
						Double due = (Double) accounts[3];
						dto.setDue(Utils.nullIfBlank(String.valueOf(Utils.round(due))));
						
						Double paid = (Double) accounts[4];
						dto.setPaid(Utils.nullIfBlank(String.valueOf(Utils.round(paid))));
						
						Date postponed = (Date) accounts[5];
						dto.setPostponedTo(Utils.convertDateToString_IndiaWithSlashes(postponed));
						
						dtos.add(dto);
					}
				}
		}
		
		return dtos;
	}

}
