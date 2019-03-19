package com.financyear.dto;

import java.util.Date;

public class ThemeDto {

	private String id;
	private String themeName;
	private String newTheme;
	private String path;
	private String loginFormPath;
	private String bootstrapPath;
	private String stylesPath;
	private boolean themeStatus;
	private String deleteFlag;
	private String createdBy;
	private String updatedBy;
	private String createdOn;
	private String updatedOn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getThemeName() {
		return themeName;
	}
	public void setNewTheme(String newTheme) {
		this.newTheme = newTheme;
	}
	public String getNewTheme() {
		return newTheme;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean getThemeStatus() {
		return themeStatus;
	}
	public void setThemeStatus(boolean themeStatus) {
		this.themeStatus = themeStatus;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getLoginFormPath() {
		return loginFormPath;
	}
	public void setLoginFormPath(String loginFormPath) {
		this.loginFormPath = loginFormPath;
	}
	public String getBootstrapPath() {
		return bootstrapPath;
	}
	public void setBootstrapPath(String bootstrapPath) {
		this.bootstrapPath = bootstrapPath;
	}
	public String getStylesPath() {
		return stylesPath;
	}
	public void setStylesPath(String stylesPath) {
		this.stylesPath = stylesPath;
	}
	
}
