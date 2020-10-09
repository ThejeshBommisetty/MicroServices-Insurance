package com.mc.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

public class Policy {

	private Long id;

	private String type;

	private Date startDate;

	private Date endDate;

	private Long custId;

	private BigDecimal amount;

	private int tenure;

	private Premium premium;
	
	private List<Long> transactions;

	public Policy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Policy(Long id, String type, int tenure, Long custId, Date startDate, Date endDate, BigDecimal amount,
			Premium premium) {
		super();
		this.id = id;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.custId = custId;
		this.amount = amount;
		this.tenure = tenure;
		this.premium = premium;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Premium getPremium() {
		return premium;
	}

	public void setPremium(Premium premium) {
		this.premium = premium;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	public List<Long> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Long> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Policy [id=" + id + ", type=" + type + ", startDate=" + startDate + ", endDate=" + endDate + ", custId="
				+ custId + ", amount=" + amount + ", tenure=" + tenure + ", premium=" + premium + ", transactions="
				+ transactions + "]";
	}

}
