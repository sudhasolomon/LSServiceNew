package com.financyear.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financyear.dto.AdditionalDto;
import com.financyear.dto.CustomerAccountsDto;
import com.financyear.dto.CustomerDto;
import com.financyear.model.AdditionalInfo;
import com.financyear.service.AdditionalInfoService;
import com.financyear.service.CustomerAccountService;
import com.financyear.utils.StatusMessage;
import com.financyear.utils.TransformDtoToEntity;
import com.financyear.utils.TransformEntityToDto;
import com.financyear.utils.Utils;

@Controller
@RequestMapping("addtionalInfo")
public class AdditionalInfoController {
	
	@Autowired
	private AdditionalInfoService infoService;
	
	@Autowired
	private CustomerAccountService accountService;

	@RequestMapping(value = "saveAdditionalInfo", method = RequestMethod.POST)
	public ResponseEntity<?> saveAdditionalInfo(HttpServletRequest request,
			@RequestBody AdditionalDto infoDto){
		StatusMessage status = new StatusMessage();
		 if(Utils.getLoginUserName(request) != null){
			 
			 if(!Utils.isBlank(infoDto.getDiscription())){
				 AdditionalInfo addtInfo = new AdditionalInfo();
				 TransformDtoToEntity.saveAddtionalInfo(infoDto, addtInfo, request);
				 infoService.saveAdditionalInfo(addtInfo);
				 status.setStatusCode(String.valueOf(200));
					status.setStatusMessage("Info saved");
				return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
			 }else{
				 status.setStatusCode(String.valueOf(500));
					status.setStatusMessage("Info didnot saved");
				return new ResponseEntity<StatusMessage>(status, HttpStatus.FORBIDDEN);
			 }
			 
		 }else{
			 
			 return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		 }
		
	}
	
	
	@RequestMapping(value = "getAdditionalInfo", method = RequestMethod.GET)
	public ResponseEntity<?> getAdditionalInfo(HttpServletRequest request){
		 if(Utils.getLoginUserName(request) != null){
			 
			List<AdditionalDto> infoList=infoService.getAdditionalInfo(request);
			 
			
			return new ResponseEntity<List<?>>(infoList, HttpStatus.OK);
		 }else{
			 
			 return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		 }
		
	}
	
	@RequestMapping(value = "getAdditionalInfoById", method = RequestMethod.POST)
	public ResponseEntity<?> getAdditionalInfoById(HttpServletRequest request,
			@RequestBody AdditionalDto dto){
		 if(Utils.getLoginUserName(request) != null){
			 AdditionalDto info = new AdditionalDto();
			AdditionalInfo infoList=infoService.getAdditionalInfoById(request, dto.getId());
			 TransformEntityToDto.getAdditionalInfoById(info,infoList);
			
			return new ResponseEntity<AdditionalDto>(info, HttpStatus.OK);
		 }else{
			 
			 return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		 }
		
	}
	
	
	@RequestMapping(value = "deleteAdditionalInfo", method = RequestMethod.POST)
	public ResponseEntity<?> deleteAdditionalInfo(HttpServletRequest request,
			@RequestBody AdditionalDto infoDto){
		StatusMessage status = new StatusMessage();
		 if(Utils.getLoginUserName(request) != null){
			 
			 if(!Utils.isBlank(infoDto.getId())){
				 AdditionalInfo addtInfo = new AdditionalInfo();
				 TransformDtoToEntity.saveAddtionalInfo(infoDto, addtInfo, request);
				 infoService.deleteAdditionalInfo(addtInfo);
				 status.setStatusCode(String.valueOf(200));
					status.setStatusMessage("Info Deleted");
				return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
			 }else{
				 status.setStatusCode(String.valueOf(500));
					status.setStatusMessage("Info did not deleted");
				return new ResponseEntity<StatusMessage>(status, HttpStatus.FORBIDDEN);
			 }
			 
		 }else{
			 
			 return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		 }
		
	}
	
	@RequestMapping(value = "getpostponedcustomers", method = RequestMethod.GET)
	public ResponseEntity<?> getpostponedcustomers(HttpServletRequest request){
		StatusMessage status = new StatusMessage();
		List<CustomerDto> list = null;
		 if(Utils.getLoginUserName(request) != null){
			 list = accountService.getpostponedcustomerdetails(request);
				 status.setStatusCode(String.valueOf(200));
					status.setStatusMessage("Info Deleted");
				return new ResponseEntity<List<CustomerDto>>(list, HttpStatus.OK);
			 
		 }else{
			 return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		 }
		
	}
}
