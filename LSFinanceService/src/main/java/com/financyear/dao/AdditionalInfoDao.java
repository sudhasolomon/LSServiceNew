package com.financyear.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.financyear.dto.AdditionalDto;
import com.financyear.model.AdditionalInfo;


public interface AdditionalInfoDao extends GenericDao<AdditionalInfo, Integer> {

 public	List<AdditionalDto> getAdditionalInfoByUser(HttpServletRequest request);

public AdditionalInfo getAdditionalInfoByUserById(HttpServletRequest request,
		String id);


}
