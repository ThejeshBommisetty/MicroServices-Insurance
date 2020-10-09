package com.mc.policy.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="premium")
public class Premium {

	@Id
	private Long id;
	
	private Long policyId;
	
	private BigDecimal dueAmount;
	
	private Date dueDate;
	
	private int remainingTenure;
	
	private BigDecimal paidAmount;

	public Premium() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Premium(Long id, Long policyId, BigDecimal dueAmount, int remainingTenure, BigDecimal paidAmount, Date dueDate) {
		super();
		this.id = id;
		this.policyId = policyId;
		this.dueAmount = dueAmount;
		this.remainingTenure = remainingTenure;
		this.paidAmount = paidAmount;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public BigDecimal getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(BigDecimal dueAmount) {
		this.dueAmount = dueAmount;
	}

	public int getRemainingTenure() {
		return remainingTenure;
	}

	public void setRemainingTenure(int remainingTenure) {
		this.remainingTenure = remainingTenure;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}



	
}
