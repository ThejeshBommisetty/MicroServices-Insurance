package com.mc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Customer {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private String gender;
	private int age;
	private String city;
	
	private Long phoneNumber;
	private Long ssn;
	
	private String emailId;
	@Transient
	private List<Long> policyNumber;
	@Transient
	private List<Long> claimNumber;
	
	
	
	public Customer(long id, String name, String gender, int age, String city, List<Long> policyNumber,
			List<Long> claimNumber , Long phoneNumber, Long ssn, String emailId) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.city = city;
		this.policyNumber = policyNumber;
		this.claimNumber = claimNumber;
		this.phoneNumber = phoneNumber;
		this.ssn = ssn;
		this.emailId = emailId;
	}
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<Long> getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(List<Long> policyNumber) {
		this.policyNumber = policyNumber;
	}
	public List<Long> getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(List<Long> claimNumber) {
		this.claimNumber = claimNumber;
	}
	
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Long getSsn() {
		return ssn;
	}
	public void setSsn(Long ssn) {
		this.ssn = ssn;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
}
