package com.mc.transaction.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="transaction")
public class Transaction {

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@GenericGenerator(name="hibernate-uuid", strategy = "hibernate-uuid")
	private Long id;
	
	private Long policyId;
	private BigDecimal amountPaid;
	
	private Date dateOfTransaction;
	private String modeOfPayment;
	
	private String status;
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Transaction(Long id, Long policyId, BigDecimal amountPaid, Date dateOfTransaction, String modeOfPayment,
			String status) {
		super();
		this.id = id;
		this.policyId = policyId;
		this.amountPaid = amountPaid;
		this.dateOfTransaction = dateOfTransaction;
		this.modeOfPayment = modeOfPayment;
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}
	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	
}
