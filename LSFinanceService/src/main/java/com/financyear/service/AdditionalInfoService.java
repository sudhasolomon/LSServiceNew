package com.financyear.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.financyear.dto.AdditionalDto;
import com.financyear.model.AdditionalInfo;

@Service
public interface AdditionalInfoService {

	public void saveAdditionalInfo(AdditionalInfo addtInfo);
	

	public List<AdditionalDto> getAdditionalInfo(HttpServletRequest request);


	public void deleteAdditionalInfo(AdditionalInfo addtInfo);


	public AdditionalInfo getAdditionalInfoById(HttpServletRequest request,
			String id);


}
