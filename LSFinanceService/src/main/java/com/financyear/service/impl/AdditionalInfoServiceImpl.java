package com.financyear.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.AdditionalInfoDao;
import com.financyear.dto.AdditionalDto;
import com.financyear.model.AdditionalInfo;
import com.financyear.service.AdditionalInfoService;

@Service
@Transactional
public class AdditionalInfoServiceImpl implements AdditionalInfoService{

	@Autowired
	private AdditionalInfoDao infoDao;
	
	public void saveAdditionalInfo(AdditionalInfo addtInfo) {
		
		//infoDao.save(addtInfo);
	//	infoDao.update(addtInfo);
		infoDao.saveOrUpdate(addtInfo);
	}


	public List<AdditionalDto> getAdditionalInfo(HttpServletRequest request) {
		return infoDao.getAdditionalInfoByUser(request);
	}


	@Override
	public void deleteAdditionalInfo(AdditionalInfo addtInfo) {
		infoDao.delete(addtInfo);
		
	}


	@Override
	public AdditionalInfo getAdditionalInfoById(HttpServletRequest request,
			String id) {
		return infoDao.getAdditionalInfoByUserById(request, id);
	}

}
