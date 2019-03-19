package com.financyear.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.financyear.dao.AdditionalInfoDao;
import com.financyear.dto.AdditionalDto;
import com.financyear.model.AdditionalInfo;
import com.financyear.utils.Utils;

@Repository
public class AdditionalInfoDaoImpl extends GenericDaoImpl<AdditionalInfo, Integer> implements AdditionalInfoDao{

	public AdditionalInfoDaoImpl() {
		super(AdditionalInfo.class);
	}

	public List<AdditionalDto> getAdditionalInfoByUser(HttpServletRequest request) {
		
		List<AdditionalDto> additionalInfos = new ArrayList<AdditionalDto>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from AdditionalInfo a");
		hql.append(" where a.deleteFlag = '"+0+"'");
		
		if(Utils.getLoginUser(request) != null){
			hql.append(" and a.createdBy = :user");
			params.put("user",Utils.getLoginUserName(request));
		}
		List<?> result = findHqlListByParams(hql.toString(), params);
		if(result != null){
			Iterator<?> itr = result.iterator();
			while(itr.hasNext()){
				AdditionalInfo info = (AdditionalInfo) itr.next();
				AdditionalDto addtDto = new AdditionalDto();
				addtDto.setCreatedBy(Utils.nullIfBlank(info.getCreatedBy()));
				addtDto.setCreatedOn(info.getCreatedOn() !=null ? Utils.convertDateToString_IndiaWithSlashes(info.getCreatedOn()): null);
				addtDto.setDiscription(Utils.nullIfBlank(info.getDiscription()));
				addtDto.setId(Utils.nullIfBlank(info.getId().toString()));
				addtDto.setUpdatedBy(Utils.nullIfBlank(info.getUpdatedBy()));
				addtDto.setUpdatedOn(info.getUpdatedOn() != null ? Utils.convertDateToString_IndiaWithSlashes(info.getUpdatedOn()) : null);
				additionalInfos.add(addtDto);
			}
		}
		return additionalInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdditionalInfo getAdditionalInfoByUserById(
			HttpServletRequest request, String id) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct a from AdditionalInfo a");
		hql.append(" where a.Id = :id and a.deleteFlag='"+0+"'");
		params.put("id",Integer.parseInt(id));
		List<AdditionalInfo> result = (List<AdditionalInfo>) findHqlListByParams(hql.toString(), params);
		
		return result!=null ? result.get(0): null;
	}

}
