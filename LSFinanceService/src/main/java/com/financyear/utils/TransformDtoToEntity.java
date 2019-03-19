package com.financyear.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cglib.transform.impl.AddInitTransformer;

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

public class TransformDtoToEntity {
	
	public static Customer getCustomer (Customer customer , CustomerDto customerobj, HttpServletRequest request){
		
		customer.setName(customerobj.getName());
		customer.setPhoneNumber(customerobj.getPhone());
		customer.setAddress(customerobj.getAddress());
		customer.setFinanceType(customerobj.getFinanceType());
		customer.setStartDate(Utils.convertStringToDate_India(customerobj.getStartDate()));
		customer.setTotalAmount(Double.parseDouble(customerobj.getTotalAmount()));
		customer.setRateOfInterest(Float.valueOf(customerobj.getInterest()));
		customer.setAmountToCustomer(Double.parseDouble(customerobj.getAmountToCustomer()));
		customer.setFinanceStatus(Utils.nullIfBlank(customerobj.getFinanceStatus()));
		customer.setDays(Integer.parseInt(customerobj.getNoOfDays()));
		customer.setAmountPerType(Double.parseDouble(customerobj.getAmountPerType()));
		if(customerobj.getFinanceStatus().equalsIgnoreCase(Constants.STARTED)){
			customer.setDeleteFlag(String.valueOf(0));
		}else{
			customer.setDeleteFlag(String.valueOf(1));
		}
		
		if(!Utils.isBlank(customerobj.getCustomerId())){
			customer.setUpdatedOn(new Date());
			customer.setUpdatedBy(Utils.getLoginUserName(request));
			/* if(!Utils.isBlank(customerobj.getReFinance())){
				 customer.setTotalAmount(customer.getTotalAmount() != null ? (customer.getTotalAmount()+Double.parseDouble(customerobj.getReFinance())):Double.parseDouble(customerobj.getReFinance()));
				 customer.setRateOfInterest(Float.valueOf(customerobj.getInterest()));
				 customer.setAmountToCustomer(Double.parseDouble(customerobj.getAmountToCustomer()));
				 customer.setAmountPerType(Double.parseDouble(customerobj.getAmountPerType()));
				 customer.setDays(Integer.parseInt(customerobj.getNoOfDays()));
				 customer.setFinanceType(customerobj.getFinanceType());
			 }*/
			 
		}else{
			customer.setPaid(0.00);
			customer.setDue(Double.parseDouble(customerobj.getTotalAmount()));
			customer.setCreatedOn(new Date());
			customer.setCreatedBy(Utils.getLoginUserName(request));
		}
		
		return customer;
	}

	@SuppressWarnings("unused")
	public static void getCustomerAccountObj(CustomerAccounts customerAcObj,
			Customer customer, CustomerAccountsDto accountDto, HttpServletRequest request) {
		
		customerAcObj.setCustomerId(  customer.getCustomerId());
		customerAcObj.setCreatedBy(Utils.getLoginUserName(request));
		customerAcObj.setCreatedOn(new Date());
		customerAcObj.setPaid(customer.getPaid());
		customerAcObj.setDue(customer.getDue());
		customerAcObj.setPaymentStatus(Utils.nullIfBlank(accountDto.getPaymentStatus()));
		
		if(accountDto.getCustomerId() != null){
			customerAcObj.setOnDateCollection(accountDto.getOnDateCollection() != null ? Double.parseDouble(accountDto.getOnDateCollection()) : 0.00);
			customerAcObj.setPaidOn(accountDto.getPaidOn() != null ? Utils.convertStringToDate_India(accountDto.getPaidOn()) : null);
		}else{
			customerAcObj.setOnDateCollection(0.00);
			customerAcObj.setPostponedTo(accountDto.getPostponedTo() != null ? Utils.convertStringToDate_India(accountDto.getPostponedTo()) : null);
		}
		
		
	}

	public static void saveOrUpdateUser(User user, UserDto dto) {
		 user.setFirstName(Utils.nullIfBlank(dto.getFirstName()));
		 user.setLastName(Utils.nullIfBlank(dto.getLastName()));
		 user.setUserName(Utils.nullIfBlank(dto.getUserName()));
		 user.setAddress(Utils.nullIfBlank(dto.getAddress()));
		 user.setEmail(Utils.nullIfBlank(dto.getEmail()));
		 user.setPhoneNumber(Utils.nullIfBlank(dto.getPhoneNumber()));
		 user.setPassword(Utils.nullIfBlank(dto.getPassword()));
		 user.setConfirmPassword(Utils.nullIfBlank(dto.getConfirmPassword()));
		 user.setTheme(dto.getTheme()!=null?dto.getTheme():"mainTheme");
	}

	public static void saveAddtionalInfo(AdditionalDto infoDto,
			AdditionalInfo addtInfo, HttpServletRequest request) {
		if(infoDto!=null){
			addtInfo.setCreatedBy(Utils.getLoginUserName(request));
			addtInfo.setCreatedOn(new Date());
			addtInfo.setDiscription(Utils.nullIfBlank(infoDto.getDiscription()));
			addtInfo.setDeleteFlag(String.valueOf(0));
			if(infoDto.getId() != null){
				addtInfo.setId(Integer.parseInt(infoDto.getId()));
				addtInfo.setUpdatedBy(infoDto.getUpdatedBy());
				addtInfo.setUpdatedOn(new Date());
			}
		}
		
	}

	public static void updateCurrentTheme(Themes theme, ThemeDto dto, HttpServletRequest request) {
		if(!Utils.isNull(dto)){
			if(!Utils.isBlank(dto.getThemeName()) && !Utils.isBlank(dto.getId())){
				theme.setThemeStatus(false);
				theme.setUpdatedBy(Utils.getLoginUserName(request));
				theme.setUpdatedOn(new Date());
			}
			
		}
		
	}

	public static void updateNewTheme(Themes theme, ThemeDto dto,
			HttpServletRequest request) {
		if(!Utils.isNull(dto)){
			if(!Utils.isBlank(dto.getThemeName()) && !Utils.isBlank(dto.getId())){
				theme.setThemeStatus(true);
				theme.setUpdatedBy(Utils.getLoginUserName(request));
				theme.setUpdatedOn(new Date());
			}
			
		}
		
	}

	public static void saveTheme(Themes theme, ThemeDto dto,
			HttpServletRequest request) {
		if(!Utils.isNull(dto)){
				theme.setThemeStatus(dto.getThemeStatus());
				theme.setCreatedBy(Utils.getLoginUserName(request));
				theme.setCreatedOn(new Date());
				theme.setPath(dto.getPath());
				theme.setLoginFormPath(dto.getLoginFormPath());
				theme.setStylesPath(dto.getStylesPath());
				theme.setBootstrapPath(dto.getBootstrapPath());
				theme.setThemeName(dto.getThemeName());
				theme.setDeleteFlag(String.valueOf(0));
		}
		
	}

}
