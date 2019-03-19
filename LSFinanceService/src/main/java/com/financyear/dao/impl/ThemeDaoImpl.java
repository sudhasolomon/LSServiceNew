package com.financyear.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.financyear.dao.ThemeDao;
import com.financyear.model.Themes;
import com.financyear.utils.Utils;

@Repository
public class ThemeDaoImpl extends GenericDaoImpl<Themes, Integer> implements ThemeDao{

	public ThemeDaoImpl() {
		super(Themes.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Themes getCurrentTheme(HttpServletRequest request) {
		List<Themes> theme = null;
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
			sql.append("from Themes t");
			sql.append(" where t.themeStatus = :themeStatus and t.updatedBy = :updatedby and t.deleteFlag = 0");
			params.put("themeStatus", true);
			params.put("updatedby", Utils.getLoginUserName(request));
			theme = (List<Themes>) findHqlListByParams(sql.toString(), params);
		 
		return !Utils.isEmpty(theme) ? theme.get(0):null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Themes getThemeByName(String themeName, HttpServletRequest request) {
		List<Themes> theme = null;
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		if(!Utils.isBlank(themeName)){
			sql.append("from Themes t");
			sql.append(" where t.themeName = :themeName and t.updatedBy = :updatedby and t.deleteFlag = 0");
			params.put("themeName", themeName);
			params.put("updatedby", Utils.getLoginUserName(request));
			theme = (List<Themes>) findHqlListByParams(sql.toString(), params);
		}
		 
		return !Utils.isEmpty(theme) ? theme.get(0):null;
	}

	
	
	/*@SuppressWarnings("unchecked")
	public Themes getThemeById(Integer id) {
		
		
		List<Themes> theme = null;
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		if(Utils.isBlank(id.toString())){
			sql.append("* from Themes t");
			sql.append("where t.id = :themeId and t.deleteFlag = 0");
			params.put("themeId", id);
			theme = (List<Themes>) findHqlListByParams(sql.toString(), params);
		}
		 
		return !Utils.isEmpty(theme) ? theme.get(0):null;
	}*/

}
