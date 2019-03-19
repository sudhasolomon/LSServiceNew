package com.financyear.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;
import com.financyear.model.CustomerAccounts;
import com.financyear.service.CustomerAccountService;
import com.financyear.service.CustomerService;
import com.financyear.utils.StatusMessage;
import com.financyear.utils.TransformDtoToEntity;
import com.financyear.utils.TransformEntityToDto;
import com.financyear.utils.Utils;


/**
 * 
 * @author Sudha
 *
 */

@Controller
@RequestMapping("/customer")
public class AddCustomerController {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerAccountService customerAccountService;

	@RequestMapping(value = "createOrUpdateCustomer", method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerObj,
			HttpServletRequest request) {
		StatusMessage statusmessage = new StatusMessage();
		Customer customer = null;
		 if(Utils.getLoginUserName(request) != null){
			 if(!Utils.isBlank(customerObj.getCustomerId())){
				 
					customer = customerService.getCustomerById(Integer.parseInt(customerObj.getCustomerId()));
					TransformDtoToEntity.getCustomer(customer, customerObj, request);
					 customerService.updateCustomer(customer);
					/* if(!Utils.isBlank(customerObj.getReFinance())){
						 CustomerAccounts customerAccounts = new CustomerAccounts();
							customerAccounts.setCustomerId(customer.getCustomerId());
							customerAccounts.setPaidOn(customer.getStartDate());
							customerAccounts.setOnDateCollection(Double.parseDouble(customerObj.getReFinance()));
							customerAccounts.setPaid(customer.getPaid());
							customerAccounts.setDue((customer.getTotalAmount() - customer.getPaid()));
							customerAccounts.setPaymentStatus("Extra");
							customerAccounts.setCreatedOn(new Date());
							customerAccounts.setCreatedBy(Utils.getLoginUserName(request));
							customerAccountService.saveCustomerAccounts(customerAccounts);
					 }*/
				}else{
					customer = new Customer();
					TransformDtoToEntity.getCustomer(customer, customerObj, request);
					customer = customerService.saveCustomer(customer);
					
					CustomerAccounts customerAccounts = new CustomerAccounts();
					customerAccounts.setCustomerId(customer.getCustomerId());
					customerAccounts.setPaidOn(customer.getStartDate());
					customerAccounts.setOnDateCollection(0.00);
					customerAccounts.setPaid(0.00);
					customerAccounts.setDue(Double.parseDouble(customerObj.getTotalAmount()));
					customerAccounts.setPaymentStatus("Paid");
					customerAccounts.setCreatedOn(new Date());
					customerAccounts.setCreatedBy(Utils.getLoginUserName(request));
					customerAccountService.saveCustomerAccounts(customerAccounts);
				}
				 
				return new ResponseEntity<CustomerDto>(HttpStatus.OK);
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer not Found");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		 
	}

	
	
	@RequestMapping(value = "getCustomerDetails", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomer(HttpServletRequest request) {
		List<CustomerDto> customerList = null;
		StatusMessage statusmessage = new StatusMessage();
		if(Utils.getLoginUserName(request) != null){
			String month = request.getParameter("month");
			String year = request.getParameter("year");
			String type = request.getParameter("type");
			
			if(!Utils.isBlank(month)&& !Utils.isBlank(year) || !Utils.isBlank(type)){
				Date startDate = Utils.getDateFormMonthAndYear(month, year);
				Date endDate = Utils.getlastDateOfMonth(month, year);
				 customerList = customerService.getCustomerByMonth(startDate, endDate, type);
			}else{
			 customerList = customerService.getCustomer();
			}
			
			return new ResponseEntity<List<CustomerDto>>(customerList, HttpStatus.OK);
		
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer account added successfully");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
	}
	
	
	
	@RequestMapping(value= "getCustomerAccountById", method=RequestMethod.GET)
	public ResponseEntity<?> getCustomerAccountDetails(HttpServletRequest request,
			@RequestParam("customerId")String customerId){
		StatusMessage statusmessage = new StatusMessage();
		List<CustomerDto> customerDto = null;	
		if(Utils.getLoginUserName(request) != null){
				
			if(!Utils.isBlank(customerId)){
				customerDto = new ArrayList<CustomerDto>();
			List<CustomerAccounts> customerList = customerAccountService.getCustomerAccountsBYId(Integer.parseInt(customerId));
			TransformEntityToDto.getCustomerAccountDetails(customerDto, customerList);
			}
			return new ResponseEntity<List<CustomerDto>>(customerDto,HttpStatus.OK);
			
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer account added successfully");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		
		
	}
	
	
	
	@RequestMapping(value= "getCustomerForEdit", method=RequestMethod.GET)
	public ResponseEntity<?> getCustomerForEdit(HttpServletRequest request,
			@RequestParam("customerId")String customerId){
		CustomerDto customerDto = null;
		StatusMessage statusmessage = new StatusMessage();
		if(Utils.getLoginUserName(request) != null){
			 
			if(!Utils.isBlank(customerId)){
				customerDto = new CustomerDto();
			Customer customer = customerService.getCustomerById(Integer.parseInt(customerId));
			TransformEntityToDto.getCustomerForEdit(customerDto, customer);
			}
			return new ResponseEntity<CustomerDto>(customerDto,HttpStatus.OK);
			
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer account added successfully");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		
	}
	
	@RequestMapping(value = "getAccounts", method = RequestMethod.GET)
	public ResponseEntity<?> getAccounts(HttpServletRequest request) {
		CustomerDto customerList = null;
		StatusMessage statusmessage = new StatusMessage();
		if(Utils.getLoginUserName(request) != null){
			String month = request.getParameter("month");
			String year = request.getParameter("year");
			String type = request.getParameter("type");
			
			if(!Utils.isBlank(month)&& !Utils.isBlank(year) || !Utils.isBlank(type)){
				Date startDate = Utils.getDateFormMonthAndYear(month, year);
				Date endDate = Utils.getlastDateOfMonth(month, year);
				 customerList = customerService.getCustomerAccountsByMonth(startDate, endDate, type);
			}else{
			 customerList = customerService.getCustomerAccounts();
			}
			
			return new ResponseEntity<CustomerDto>(customerList, HttpStatus.OK);
		
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer account added successfully");
				return new ResponseEntity<StatusMessage>(statusmessage,HttpStatus.FORBIDDEN);
		 }
	}
}
