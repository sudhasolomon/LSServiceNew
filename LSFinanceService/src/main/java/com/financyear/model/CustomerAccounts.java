package com.financyear.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_accounts")
public class CustomerAccounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerAccountId;
	private Integer customerId;
	private Double paid;
	private Double due;
	private Date paidOn;
	private String paymentStatus;
	private Date postponedTo;
	private Double onDateCollection;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "customerId", insertable = false, updatable = false)
	private Customer customer;

	
	
	
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Double getOnDateCollection() {
		return onDateCollection;
	}

	public void setOnDateCollection(Double onDateCollection) {
		this.onDateCollection = onDateCollection;
	}

	public Double getPaid() {
		return paid;
	}
	
	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public void setDue(Double due) {
		this.due = due;
	}
	
	public Double getDue() {
		return due;
	}

	public Date getPaidOn() {
		return paidOn;
	}
	
	public void setPaidOn(Date paidOn) {
		this.paidOn = paidOn;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getPostponedTo() {
		return postponedTo;
	}

	public void setPostponedTo(Date postponedTo) {
		this.postponedTo = postponedTo;
	}

	public Integer getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(Integer customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
