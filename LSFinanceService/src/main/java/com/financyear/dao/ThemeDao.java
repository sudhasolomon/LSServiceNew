package com.financyear.dao;

import javax.servlet.http.HttpServletRequest;

import com.financyear.model.Themes;

public interface ThemeDao extends GenericDao<Themes, Integer>{

	/*public Themes getThemeById(Integer parseInt);*/

	public Themes getCurrentTheme(HttpServletRequest request);

	public Themes getThemeByName(String themeName, HttpServletRequest request);


}
