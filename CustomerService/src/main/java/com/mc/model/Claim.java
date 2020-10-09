package com.mc.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Claim {

	private long id;
	private long custId;
	private BigDecimal amount;
	private String reason;
	private Date claimDate;
	private long policyId;
	private String status;
	private String errorCode;
	private String errorMessage;
	private java.util.Date errorTimestamp;
	
	public Claim() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Claim(long id, long custId, BigDecimal amount, String reason, Date claimDate, long policyId, String status,
			String errorCode, String errorMessage, java.util.Date errorTimestamp) {
		super();
		this.id = id;
		this.custId = custId;
		this.amount = amount;
		this.reason = reason;
		this.claimDate = claimDate;
		this.policyId = policyId;
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorTimestamp = errorTimestamp;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Claim [id=" + id + ", custId=" + custId + ", amount=" + amount + ", reason=" + reason + ", claimDate="
				+ claimDate + ", policyId=" + policyId + ", status=" + status + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + ", errorTimestamp=" + errorTimestamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((claimDate == null) ? 0 : claimDate.hashCode());
		result = prime * result + (int) (custId ^ (custId >>> 32));
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((errorTimestamp == null) ? 0 : errorTimestamp.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (policyId ^ (policyId >>> 32));
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Claim other = (Claim) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (claimDate == null) {
			if (other.claimDate != null)
				return false;
		} else if (!claimDate.equals(other.claimDate))
			return false;
		if (custId != other.custId)
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (errorTimestamp == null) {
			if (other.errorTimestamp != null)
				return false;
		} else if (!errorTimestamp.equals(other.errorTimestamp))
			return false;
		if (id != other.id)
			return false;
		if (policyId != other.policyId)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
}
