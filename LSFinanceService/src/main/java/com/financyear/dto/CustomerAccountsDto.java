package com.financyear.dto;

import java.util.Date;

public class CustomerAccountsDto {
	
	private String customerAccountId;
	private String customerId;
	private String paid;
	private String remainingToPay;
	private String paidOn;
	private String paymentStatus;
	private String postponedTo;
	private String onDateCollection;
	private String createdOn;
	private String createdBy;
	private String updatedOn;
	private String updatedBy;
	public String getCustomerAccountId() {
		return customerAccountId;
	}
	public void setCustomerAccountId(String customerAccountId) {
		this.customerAccountId = customerAccountId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getPaid() {
		return paid;
	}
	public String getRemainingToPay() {
		return remainingToPay;
	}
	public void setRemainingToPay(String remainingToPay) {
		this.remainingToPay = remainingToPay;
	}
	public void setPaidOn(String paidOn) {
		this.paidOn = paidOn;
	}
	public String getPaidOn() {
		return paidOn;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPostponedTo() {
		return postponedTo;
	}
	public void setPostponedTo(String postponedTo) {
		this.postponedTo = postponedTo;
	}
	public String getOnDateCollection() {
		return onDateCollection;
	}
	public void setOnDateCollection(String onDateCollection) {
		this.onDateCollection = onDateCollection;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
}
