package com.financyear.service;

import javax.servlet.http.HttpServletRequest;

import com.financyear.model.Themes;

public interface ThemeService {


	public Themes getCurrentTheme(HttpServletRequest request);

	public void updateCurrentTheme(Themes theme);

	public Themes getThemeByName(String themeName, HttpServletRequest request);

	public void saveTheme(Themes theme);

	
}
