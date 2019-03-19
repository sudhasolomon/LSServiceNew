package com.financyear.controller;

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

import com.financyear.dto.CustomerAccountsDto;
import com.financyear.dto.CustomerDto;
import com.financyear.model.Customer;
import com.financyear.model.CustomerAccounts;
import com.financyear.service.CustomerAccountService;
import com.financyear.service.CustomerService;
import com.financyear.service.FinancyearService;
import com.financyear.utils.StatusMessage;
import com.financyear.utils.TransformDtoToEntity;
import com.financyear.utils.Utils;

@Controller
@RequestMapping("/financyear")
public class FinancyearController {

	@Autowired
	public CustomerService customerService;

	@Autowired
	public FinancyearService financeService;
	@Autowired
	public CustomerAccountService customerAcService;

	@RequestMapping(value = "getFinancyearView", method = RequestMethod.POST)
	public ResponseEntity<?> getFinancyearView(HttpServletRequest request,
			@RequestParam("viewType") String viewType) {
		StatusMessage statusmessage = new StatusMessage();
		List<CustomerDto> customerDtos = null;
		if(Utils.getLoginUserName(request) != null){
			 
			
			System.out.println(viewType);
			customerDtos = financeService.getCustomerDetailsByType(viewType);
			if (customerDtos != null) {
				return new ResponseEntity<List<CustomerDto>>(customerDtos,
						HttpStatus.OK);
			} else {
				statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer not Found");
				return new ResponseEntity<StatusMessage>(statusmessage,
						HttpStatus.OK);
			}
			
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer not Found");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		
	}

	@RequestMapping(value = "saveCustomerAccount", method = RequestMethod.POST)
	public ResponseEntity<?> saveCustomerAccounts(HttpServletRequest request,
			@RequestParam("customerId") String customerId,
			@RequestBody CustomerAccountsDto accountDto) {
		StatusMessage statusmessage = new StatusMessage();
		if(Utils.getLoginUserName(request) != null){
			 
			
			if (!Utils.isBlank(customerId)) {
				CustomerAccounts customerAcObj = new CustomerAccounts();
				Customer customer = customerService.getCustomerById(Integer.parseInt(customerId));
				if(accountDto.getPaymentStatus().equalsIgnoreCase("Paid")){
					
					accountDto.setCustomerId(customer.getCustomerId().toString());
					customerAccountCalculation(accountDto, customer, request);
					TransformDtoToEntity.getCustomerAccountObj(customerAcObj,customer,
							accountDto, request);
					customerService.updateCustomer(customer);
				}else{
					if(accountDto.getPaymentStatus().equalsIgnoreCase("Postponed")){
						TransformDtoToEntity.getCustomerAccountObj(customerAcObj,customer,
								accountDto, request);
						customer.setUpdatedBy(Utils.getLoginUserName(request));
						customer.setUpdatedOn(new Date());
						customerService.updateCustomer(customer);
					}
				}
				customerAcService.saveCustomerAccounts(customerAcObj);
			 	
					/*List<CustomerAccounts> customerAccounts = customerAcService
							.getCustomerAccountsBYId(Integer.parseInt(customerId));*/
				statusmessage.setStatusCode(String.valueOf(200));
				statusmessage.setStatusMessage("Customer account added successfully");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.OK);
			}else{
			statusmessage.setStatusCode(String.valueOf(500));
			statusmessage.setStatusMessage("Customer Not Found");
			return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.OK);
			}
		 }else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Customer Not Found");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		
	}

	private void customerAccountCalculation(		 
			CustomerAccountsDto accountDto, Customer customer, HttpServletRequest request) {
		if(accountDto.getOnDateCollection() != null){
		 Double newAmt = customer.getPaid() + Double.parseDouble(accountDto.getOnDateCollection());
		 Double newDue = customer.getDue() - Double.parseDouble(accountDto.getOnDateCollection());
		 customer.setPaid(newAmt);
		 customer.setDue(newDue);
		 customer.setUpdatedOn(new Date());
		 customer.setUpdatedBy(Utils.getLoginUserName(request));	
		}
	}
	
	/*private void customerAccountCalcullation(
			List<CustomerAccounts> customerAccounts,
			CustomerAccountsDto accountDto, Customer customer) {

		Iterator itr = customerAccounts.iterator();
		Double amountCount = 0.00;
		while (itr.hasNext()) {
			CustomerAccounts customerAc = (CustomerAccounts) itr.next();
			if( customerAc.getOnDateCollection() != null){
			Double collected = customerAc.getOnDateCollection();
			amountCount += collected;
			}
		}
		Double totalAmount = customer.getTotalAmount();
		Double payed = amountCount + Double.parseDouble(accountDto.getOnDateCollection());
		Double remainingToPay = totalAmount - payed;
		accountDto.setPayed(String.valueOf(payed));
		accountDto.setRemainingToPay(String.valueOf(remainingToPay));
		customer.setPayed(payed);
		customer.setRemainingToPay(remainingToPay);
	}*/
	
	
}
