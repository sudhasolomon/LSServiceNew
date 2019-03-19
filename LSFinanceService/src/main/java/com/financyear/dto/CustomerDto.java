package com.financyear.dto;

public class CustomerDto {

	private String name;
	private String phone;
	private String address;
	private String totalAmount;
	private String interest;
	private String financeType;
	private String financeStatus;
	private String createdBy;
	private String updatedBy;
	private String createdOn;
	private String updatedOn;
	private String amountToCustomer;
	private String noOfDays;
	private String userId;
	private String amountPerType;
	private String startDate;
	private String customerId;
	private String paid;
	private String due;
	private String paidDate;
	private String onDateCollection;
	private Boolean isCollapsed;
	private CustomerAccountsDto customerAccountsDto;
	private String financyearTotalSpent;
	private String financyearTotalCollected;
	private String remainingFinanceToCollect;
	private String paymentStatus;
	private String postponedTo;
//	private String reFinance;
	
	/*public void setReFinance(String reFinance) {
		this.reFinance = reFinance;
	}
	
	public String getReFinance() {
		return reFinance;
	}*/
	
	public void setPostponedTo(String postponedTo) {
		this.postponedTo = postponedTo;
	}
	
	public String getPostponedTo() {
		return postponedTo;
	}
	
	
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	
	public String getFinancyearTotalSpent() {
		return financyearTotalSpent;
	}

	public void setFinancyearTotalSpent(String financyearTotalSpent) {
		this.financyearTotalSpent = financyearTotalSpent;
	}

	public String getFinancyearTotalCollected() {
		return financyearTotalCollected;
	}

	public void setFinancyearTotalCollected(String financyearTotalCollected) {
		this.financyearTotalCollected = financyearTotalCollected;
	}

	public String getRemainingFinanceToCollect() {
		return remainingFinanceToCollect;
	}

	public void setRemainingFinanceToCollect(String remainingFinanceToCollect) {
		this.remainingFinanceToCollect = remainingFinanceToCollect;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	
	public String getPaidDate() {
		return paidDate;
	}

	public String getOnDateCollection() {
		return onDateCollection;
	}

	public void setOnDateCollection(String onDateCollection) {
		this.onDateCollection = onDateCollection;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setAmountPerType(String amountPerType) {
		this.amountPerType = amountPerType;
	}

	public String getAmountPerType() {
		return amountPerType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getFinanceType() {
		return financeType;
	}

	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
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

	public String getAmountToCustomer() {
		return amountToCustomer;
	}

	public void setAmountToCustomer(String amountToCustomer) {
		this.amountToCustomer = amountToCustomer;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getPaid() {
		return paid;
	}
	public void setDue(String due) {
		this.due = due;
	}
	public String getDue() {
		return due;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public CustomerAccountsDto getCustomerAccountsDto() {
		return customerAccountsDto;
	}

	public void setCustomerAccountsDto(CustomerAccountsDto customerAccountsDto) {
		this.customerAccountsDto = customerAccountsDto;
	}

	public Boolean getIsCollapsed() {
		return isCollapsed != null ? isCollapsed : false;
	}

	public void setIsCollapsed(Boolean isCollapsed) {
		this.isCollapsed = isCollapsed;
	}

}
