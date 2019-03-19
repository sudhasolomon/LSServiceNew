package com.financyear.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	private String name;
	private String phoneNumber;
	private String address;
	private String financeStatus;
	private Date startDate;
	private Double totalAmount;
	private Float rateOfInterest;
	private Integer days;
	private String financeType;
	private Double amountToCustomer;
	private Double amountPerType;
	private String createdBy;
	private String updatedBy;
	private Date createdOn;
	private Date updatedOn;
	private Double paid;
	private Double due;
	private String deleteFlag;
	@OneToMany(mappedBy = "customer")
	private List<CustomerAccounts> customerAccount;

	
	
	
	
	public void setPaid(Double paid) {
		this.paid = paid;
	}
	public Double getPaid() {
		return paid;
	}
	public void setDue(Double due) {
		this.due = due;
	}
	public Double getDue() {
		return due;
	}

	public List<CustomerAccounts> getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(List<CustomerAccounts> customerAccount) {
		this.customerAccount = customerAccount;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	/*public Double getPayed() {
		return payed;
	}

	public void setPayed(Double payed) {
		this.payed = payed;
	}

	public Double getRemainingToPay() {
		return remainingToPay;
	}

	public void setRemainingToPay(Double remainingToPay) {
		this.remainingToPay = remainingToPay;
	}*/

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

	public void setAmountPerType(Double amountPerType) {
		this.amountPerType = amountPerType;
	}

	public Double getAmountPerType() {
		return amountPerType;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Float getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(Float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getFinanceType() {
		return financeType;
	}

	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}

	public Double getAmountToCustomer() {
		return amountToCustomer;
	}

	public void setAmountToCustomer(Double amountToCustomer) {
		this.amountToCustomer = amountToCustomer;
	}

}
