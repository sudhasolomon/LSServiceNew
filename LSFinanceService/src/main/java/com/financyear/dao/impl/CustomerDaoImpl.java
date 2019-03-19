package com.financyear.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.financyear.dao.CustomerDao;
import com.financyear.model.Customer;
import com.financyear.utils.Constants;
import com.financyear.utils.Utils;

@Repository
public class CustomerDaoImpl extends GenericDaoImpl<Customer, Integer> implements CustomerDao {
	
	public CustomerDaoImpl() {
		super(Customer.class);
		 
	}

	@SuppressWarnings("unchecked")
	public List<Customer> getCustomer() {
		List<Customer> customers = null;
		try {
			Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Customer.class);
			
			//criteria.addOrder(CoalesceOrder.desc("createdOn", "updatedOn"));
			customers = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	@SuppressWarnings("unchecked")
	public Customer getCustomerById(Integer customerId) {
		List<Customer> customer = null;
		
		if(!Utils.isBlank(customerId.toString())){ 
			
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("from Customer c");
		hql.append(" where c.customerId = :customerId");
		params.put("customerId", customerId);
		
		customer =  (List<Customer>) findHqlListByParams(hql.toString(), params);
		
		}
				 
		return !Utils.isEmpty(customer) ? customer.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<Customer> getCustomerByMonth(Date startDate, Date endDate, String type) {
		List<Customer> customer = null;
		Map<String, Object> params = new HashMap<String, Object>();
		 StringBuilder hql = new StringBuilder(" from Customer c ");
		 
		 if(startDate !=null && endDate !=null || type != null){
			 
			 if(startDate !=null && endDate !=null && type != null && !type.equalsIgnoreCase("")){
				 hql.append("where c.startDate >= :startDate");
				 params.put("startDate", startDate);
				 hql.append(" and c.startDate< :endDate");
				 params.put("endDate",endDate);
				 hql.append(" and c.financeStatus = :type");
				 params.put("type",type);
				 if(type.equalsIgnoreCase(Constants.STARTED)){
					 hql.append(" and c.deleteFlag = 0");
				 }else{
					 hql.append(" and c.deleteFlag = 1");
				 }
			 }else{
				 if( startDate != null && endDate != null){
					 hql.append("where c.startDate >= :startDate");
					 params.put("startDate", startDate);
					 hql.append(" and c.startDate< :endDate");
					 params.put("endDate",endDate);
					 }else{
						 if(type.equalsIgnoreCase(Constants.STARTED)){
							 hql.append("where c.deleteFlag = 0");
						 }else{
							 hql.append("where c.deleteFlag = 1");
						 }
					 }
			 }
		 }
		 hql.append(" order by COALESCE(c.updatedOn,c.createdOn) DESC");
		 customer =  (List<Customer>) findHqlListByParams(hql.toString(), params);
		return customer;
	}

	 

}
