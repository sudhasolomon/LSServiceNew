package com.financyear.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Themes")
public class Themes {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String themeName;
	private String path;
	private String loginFormPath;
	private String bootstrapPath;
	private String stylesPath;
	private boolean themeStatus;
	private String deleteFlag;
	private String createdBy;
	private String updatedBy;
	private Date createdOn;
	private Date updatedOn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getThemeName() {
		return themeName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isThemeStatus() {
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
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
