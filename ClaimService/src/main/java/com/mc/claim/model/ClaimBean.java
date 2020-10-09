package com.mc.claim.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity(name="claims")
public class ClaimBean{
	
	@Id
	//@GeneratedValue
	private long id;
	
	/*
	 * private String firstName; private String lastName;
	 */
	private long custId;
	private BigDecimal amount;
	private String reason;
	private long policyId;
	private Date claimDate;
	
	private String status;
	//private String comments;
	
	@Transient
	private String errorCode;
	
	@Transient
	private String errorMessage;
	
	@Transient
	private java.util.Date errorTimestamp;
	
	public ClaimBean(long id, long custId, BigDecimal amount, String reason,  Date claimDate, String status) {
		super();
		this.id = id;
		this.custId = custId;
		this.amount = amount;
		this.reason = reason;
		this.claimDate = claimDate;
		this.status = status;
	}
	public ClaimBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public java.util.Date getErrorTimestamp() {
		return errorTimestamp;
	}
	public void setErrorTimestamp(java.util.Date errorTimestamp) {
		this.errorTimestamp = errorTimestamp;
	}

}
