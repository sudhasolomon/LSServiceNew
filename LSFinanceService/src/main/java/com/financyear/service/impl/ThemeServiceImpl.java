package com.financyear.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.ThemeDao;
import com.financyear.model.Themes;
import com.financyear.service.ThemeService;

@Service
@Transactional
public class ThemeServiceImpl implements ThemeService{

	@Autowired
	private ThemeDao themeDao;

	@Override
	public Themes getCurrentTheme(HttpServletRequest request) {
		return themeDao.getCurrentTheme(request);
	}

	@Override
	public void updateCurrentTheme(Themes theme) {
		themeDao.update(theme);
	}

	@Override
	public Themes getThemeByName(String themeName, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return themeDao.getThemeByName(themeName,request);
	}

	@Override
	public void saveTheme(Themes theme) {
		themeDao.save(theme);
	}


	

	
	/*@Override
	public Themes getThemeById(int parseInt) {
		return themeDao.getThemeById(parseInt);
	}*/
}
