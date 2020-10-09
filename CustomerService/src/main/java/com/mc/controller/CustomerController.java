package com.mc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mc.CustomerRepository;
import com.mc.model.Claim;
import com.mc.model.Customer;
import com.mc.model.Policy;
import com.mc.model.Transaction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
public class CustomerController {
	

	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ClaimServiceProxy claimProxy; 
	
	@Autowired
	PolicyServiceProxy policyProxy;
	
	@Autowired
	TransactionServiceProxy transactionProxy;

	@Autowired
	private SendMail sendMail;
	
	@GetMapping("customer/{id}")
	public Customer getCustomers(@PathVariable Long id) {
		
		Optional<Customer> cust = custRepo.findById(id);
		if(cust.isEmpty()) {
			throw new RuntimeException("Customer Not found "+id);
		}
		Customer resp = cust.get();
		List<Claim> claimresp = claimProxy.getClaims(id);
		List<Long> claimNum = new ArrayList<>();
		claimresp.forEach(claim -> claimNum.add(claim.getId()));
		resp.setClaimNumber(claimNum);
		
		List<Policy> policyResp = policyProxy.getPolicies(id);
		List<Long> policyNum = new ArrayList<>();
		policyResp.forEach(policy -> policyNum.add(policy.getId()));
		resp.setPolicyNumber(policyNum);
		return resp;
	}
	@HystrixCommand(fallbackMethod = "claimsFailure")
	@GetMapping("customer/{id}/claims")
	public List<Claim> getClaims(@PathVariable Long id) {
		List<Claim> claimresp = claimProxy.getClaims(id);
		return claimresp;
	}
	
	public List<Claim> claimsFailure(@PathVariable Long id) {
		
		String errorMessage = "Error while fetching records for the customer id "+id ;
		Claim claim = new Claim();
		claim.setErrorMessage(errorMessage);
		claim.setErrorCode("ERROR_CUST001");
		claim.setErrorTimestamp(new java.util.Date());
		return Arrays.asList(claim);
	}
	
	@GetMapping("customer/{id}/claims/{claimId}")
	public Claim getClaimsbyId(@PathVariable Long id, @PathVariable Long claimId) {
		Claim claimresp = claimProxy.getClaimByCustId(id, claimId);
		return claimresp;
	}
	
	//@HystrixCommand(fallbackMethod = "sendMailFailure")
	@PostMapping("customer/{id}/fileClaim")
	public String fileClaim(@PathVariable Long id, @RequestBody Claim claimReq) {
		claimReq.setCustId(id);
		Claim claim = claimProxy.createClaim(claimReq);
		Long customerId = claim.getCustId();
		Long claimNumber = claim.getId();
		try {
			sendMail(customerId, claimNumber);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "ClaimNumber is "+claimNumber;
		
	}
	


	@GetMapping("customer/{id}/policy")
	public List<Policy> getPolicy(@PathVariable Long id) {
		List<Policy> policies = policyProxy.getPolicies(id);
		Iterator<Policy> it = policies.iterator();
		while(it.hasNext()) {
			Policy p = it.next();
			List<Transaction> transactions = transactionProxy.getAllTransactions(p.getId());
			List<Long> transactionIds = new ArrayList<>();
			transactions.forEach(transaction -> transactionIds.add(transaction.getId()));
			p.setTransactions(transactionIds);
		}
		return policies;
	}
	
	
	/*
	 * Update the Policy and Transaction table
	 * Retrieve all the transactions of the policy and find the latest transaction number
	 * and display it 
	 * 
	 * Used Java 8 feature --forEach, lambda, stream, method reference, predicates, type inference
	 */
	@PostMapping("customer/{id}/payPremium/{policyId}")
	public String initiateTransaction(@PathVariable Long id , @PathVariable Long policyId, 
			@RequestBody Transaction trans) {
		policyProxy.updatePolicy(policyId);
		trans.setPolicyId(policyId);
		int transactionId = transactionProxy.insertTransaction(trans);
		
		//List<Transaction> transactions = transactionProxy.getAllTransactions(policyId);
		
		//String transactionId =transactions.stream().mapToInt(num ->Integer.parseInt(num.getId())).reduce(Integer::max).toString();
		/*
		 * // transactions.forEach(transaction ->
		 * transactionIds.add(Integer.valueOf(transaction.getId()))); int transactionID=
		 * transactionIds.stream().reduce(
		 */
		return "TransactionNumber "+transactionId;
	}
	
	

	private  void sendMail(Long customerId, Long claimNumber) throws MessagingException {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Optional<Customer> cust = custRepo.findById(customerId);
		String emailId = cust.get().getEmailId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//("Month D, Yr");
		//SimpleMailMessage msg = new SimpleMailMessage();
		//msg.setFrom("MicroCredentials Application");
		String subject ="Claim Acknowledged "+ claimNumber;
		String text = "Your Claim "+claimNumber+" has been acknowledged and processed on "+ sdf.format(new java.util.Date());
		//msg.setText(text);
		sendMail.sendMessage(emailId, subject, text);
		logger.info("Email is triggered to "+emailId+ " for the claim Number "+claimNumber);;
	}
	
	
}
