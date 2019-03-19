package com.financyear.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.financyear.dto.AdditionalDto;
import com.financyear.dto.CustomerAccountsDto;
import com.financyear.dto.CustomerDto;
import com.financyear.dto.ThemeDto;
import com.financyear.dto.UserDto;
import com.financyear.model.AdditionalInfo;
import com.financyear.model.Customer;
import com.financyear.model.CustomerAccounts;
import com.financyear.model.Themes;
import com.financyear.model.User;
import com.mysql.jdbc.Util;

public class TransformEntityToDto {

	public static List<CustomerDto> getCustomerDetails(List<Customer> customerList){
		List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
		
		
		if(!Utils.isEmpty(customerList)){
		
		Iterator<?> itr1 = customerList.iterator();
		while(itr1.hasNext()){
			Customer customer = (Customer) itr1.next();
			CustomerDto customerDto = new CustomerDto();
			customerDto.setCustomerId(customer.getCustomerId().toString());
			customerDto.setName(Utils.nullIfBlank(customer.getName()));
			customerDto.setPhone(Utils.nullIfBlank(customer.getPhoneNumber()));
			customerDto.setAddress(Utils.nullIfBlank(customer.getAddress()));
			customerDto.setStartDate(Utils.convertDateToString_IndiaWithSlashes(customer.getStartDate()));
			customerDto.setFinanceStatus(Utils.nullIfBlank(customer.getFinanceStatus()));
			customerDto.setTotalAmount(customer.getTotalAmount() != null?  String.valueOf(customer.getTotalAmount()) : null);
			customerDto.setInterest(customer.getRateOfInterest() != null ? String.valueOf(customer.getRateOfInterest()) : null);
			customerDto.setAmountToCustomer(customer.getAmountToCustomer() != null ? String.valueOf(customer.getAmountToCustomer()) : null);
			customerDto.setFinanceType(Utils.nullIfBlank(customer.getFinanceType()));
			customerDto.setNoOfDays(customer.getDays() != null ? customer.getDays().toString() : null);
			customerDto.setAmountPerType(customer.getAmountPerType() != null ? String.valueOf(customer.getAmountPerType()) : null);
			customerDto.setPaid(customer.getPaid() != null ? String.valueOf(customer.getPaid()): String.valueOf(0.00));
			customerDto.setDue(customer.getDue()!=null?String.valueOf(customer.getDue()): String.valueOf(0.00));
			
			customerDto.setCreatedBy(customer.getCreatedBy());
			customerDto.setCreatedOn(Utils.convertDateToString_IndiaWithSlashes(customer.getCreatedOn()));
			
			customerDtos.add(customerDto);
		}
		}
		
		return customerDtos;
	}

	
	public static void getCustomerAccountDetails(List<CustomerDto> customerDto,
			List<CustomerAccounts> customerList) {
		
		DecimalFormat dc = new DecimalFormat("#.##");
		 Iterator  itr = customerList.iterator();
		 while(itr.hasNext()){
			 CustomerDto dto  = new CustomerDto(); 
			 CustomerAccounts accounts = (CustomerAccounts) itr.next();
			 dto.setName( Utils.nullIfBlank(accounts.getCustomer().getName()));
			 dto.setCustomerId(Utils.nullIfBlank(accounts.getCustomerId().toString()));
			 dto.setStartDate(Utils.convertDateToString_IndiaWithSlashes(accounts.getCustomer().getStartDate()));
			 dto.setTotalAmount(Utils.nullIfBlank(String.valueOf(accounts.getCustomer().getTotalAmount())));
			 dto.setPaidDate(Utils.convertDateToString_IndiaWithSlashes(accounts.getPaidOn()));
			 dto.setOnDateCollection(Utils.nullIfBlank(String.valueOf(accounts.getOnDateCollection())));
			 dto.setPaid(Utils.nullIfBlank(String.valueOf(accounts.getPaid())));
			 dto.setPaymentStatus(Utils.nullIfBlank(accounts.getPaymentStatus()));
			 dto.setDue(Utils.nullIfBlank(String.valueOf(accounts.getDue())));
			 dto.setPostponedTo(accounts.getPostponedTo()!=null ? Utils.convertDateToString_IndiaWithSlashes(accounts.getPostponedTo()) : null);
			 customerDto.add(dto);
		 }
		 
		
	}


	public static void getCustomerForEdit(CustomerDto customerDto,
			Customer customer) {
		customerDto.setCustomerId(Utils.nullIfBlank(customer.getCustomerId().toString()));
		customerDto.setAddress(Utils.nullIfBlank(customer.getAddress()));
		customerDto.setAmountPerType(Utils.nullIfBlank(String.valueOf(customer.getAmountPerType())));
		customerDto.setAmountToCustomer(Utils.nullIfBlank(String.valueOf(customer.getAmountToCustomer())));
		customerDto.setFinanceStatus(Utils.nullIfBlank(customer.getFinanceStatus()));
		customerDto.setFinanceType(Utils.nullIfBlank(customer.getFinanceType()));
		customerDto.setInterest(Utils.nullIfBlank(String.valueOf(customer.getRateOfInterest())));
		customerDto.setName(Utils.nullIfBlank(customer.getName()));
		customerDto.setNoOfDays(Utils.nullIfBlank(String.valueOf(customer.getDays())));
		customerDto.setPhone(Utils.nullIfBlank(customer.getPhoneNumber()));
		customerDto.setStartDate(Utils.convertDateToString_IndiaWithSlashes(customer.getStartDate()));
		customerDto.setPaid(Utils.nullIfBlank(String.valueOf(customer.getPaid())));
		customerDto.setTotalAmount(Utils.nullIfBlank(String.valueOf(customer.getTotalAmount())));
	}


	public static UserDto getLogedInUserDto(UserDto dto, User user) {
		if(user != null){
		dto.setUserName(Utils.nullIfBlank(user.getUserName()));
		dto.setFirstName(Utils.nullIfBlank(user.getFirstName()));
		dto.setLastName(Utils.nullIfBlank(user.getLastName()));
		dto.setPassword(Utils.nullIfBlank(user.getPassword()));
		dto.setConfirmPassword(Utils.nullIfBlank(user.getConfirmPassword()));
		dto.setAddress(Utils.nullIfBlank(user.getAddress()));
		dto.setEmail(Utils.nullIfBlank(user.getEmail()));
		dto.setPhoneNumber(Utils.nullIfBlank(user.getPhoneNumber()));
		dto.setUserId(Utils.nullIfBlank(user.getId().toString()));
		dto.setTheme(user.getTheme() != null ?user.getTheme() : "mainTheme");
		}
		return dto;
	}


	public static CustomerDto getCustomerAccountsByMonth(
			List<Customer> customerList) {
		CustomerDto customerDto = new CustomerDto();
		Double financyearTotalSpent = 0.00;
		Double financyearTotalCollected = 0.00;
		if(customerList != null){
		Iterator<?> itr = customerList.iterator();
		while(itr.hasNext()){
			Customer customer = (Customer) itr.next();
			if(customer.getTotalAmount()!= null)
			financyearTotalSpent += customer.getTotalAmount();
			
			if(customer.getPaid()!= null)
			financyearTotalCollected += customer.getPaid();
		}
		
		Double remainingFinanceToCollect = financyearTotalSpent - financyearTotalCollected; 
		
		
		customerDto.setFinancyearTotalSpent(String.valueOf(financyearTotalSpent));
		customerDto.setFinancyearTotalCollected(String.valueOf(financyearTotalCollected));
		customerDto.setRemainingFinanceToCollect(String.valueOf(remainingFinanceToCollect));
		
	}
		return customerDto;
	}


	public static void getAdditionalInfoById(AdditionalDto info,
			AdditionalInfo infoList) {
		
		if(infoList != null){
			info.setId(infoList.getId().toString());
			info.setCreatedBy(infoList.getCreatedBy());
			info.setDiscription(infoList.getDiscription());
			info.setCreatedOn(Utils.convertDateToString_India(infoList.getCreatedOn()));
			info.setUpdatedBy(infoList.getUpdatedBy());
			info.setUpdatedOn(Utils.convertDateToString_India(infoList.getUpdatedOn()));
			
		}

		
	}


	public static void getTheme(Themes theme, ThemeDto dto) {
		if(!Utils.isNull(theme)){
			dto.setCreatedBy(Utils.nullIfBlank(theme.getCreatedBy()));
			dto.setCreatedOn(Utils.convertDateToString_IndiaWithSlashes(theme.getCreatedOn()));
			dto.setDeleteFlag(Utils.nullIfBlank(theme.getDeleteFlag()));
			dto.setId(Utils.nullIfBlank(String.valueOf(theme.getId())));
			dto.setPath(Utils.nullIfBlank(theme.getPath()));
			dto.setLoginFormPath(Utils.nullIfBlank(theme.getLoginFormPath()));
			dto.setStylesPath(Utils.nullIfBlank(theme.getStylesPath()));
			dto.setBootstrapPath(Utils.nullIfBlank(theme.getBootstrapPath()));
			dto.setThemeStatus(theme.isThemeStatus());
			dto.setThemeName(Utils.nullIfBlank(theme.getThemeName()));
			dto.setUpdatedBy(Utils.nullIfBlank(theme.getUpdatedBy()));
			dto.setUpdatedOn(Utils.convertDateToString_IndiaWithSlashes(theme.getUpdatedOn()));
		}
		
	}


	public static void getpostponedCustomerDtos(List<CustomerAccountsDto> dtos,
			List<CustomerAccounts> customerAccount) {
		if(!Utils.isEmpty(customerAccount)){
			Iterator<CustomerAccounts> itr = customerAccount.iterator();
			while(itr.hasNext()){
				CustomerAccountsDto dto = new CustomerAccountsDto();
				CustomerAccounts accounts = itr.next();
				dto.setCreatedBy(Utils.nullIfBlank(accounts.getCreatedBy()));
				dto.setCreatedOn(Utils.convertDateToString_IndiaWithSlashes(accounts.getCreatedOn()));
				dto.setCustomerAccountId(accounts.getCustomerAccountId().toString());
				dto.setCustomerId(accounts.getCustomerId().toString());
				dto.setPaid(Utils.nullIfBlank(String.valueOf(Utils.round(accounts.getPaid()))));
				dto.setPaidOn(Utils.convertDateToString_IndiaWithSlashes(accounts.getPaidOn()));
				dto.setPaymentStatus(Utils.nullIfBlank(accounts.getPaymentStatus()));
				dto.setPostponedTo(Utils.convertDateToString_IndiaWithSlashes(accounts.getPostponedTo()));
				dto.setRemainingToPay(Utils.nullIfBlank(String.valueOf(Utils.round(accounts.getDue()))));
				dto.setUpdatedBy(Utils.nullIfBlank(accounts.getUpdatedBy()));
				dto.setUpdatedOn(Utils.convertDateToString_IndiaWithSlashes(accounts.getUpdatedOn()));
				dtos.add(dto);
			}
		}
	}
}
