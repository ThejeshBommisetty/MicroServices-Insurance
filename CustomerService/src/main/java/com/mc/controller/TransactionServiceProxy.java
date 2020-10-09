package com.mc.controller;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mc.model.Transaction;

@FeignClient(name ="transactionservice")	//, url="localhost:8400")
@RibbonClient(name="transactionservice")
public interface TransactionServiceProxy {
	
	@GetMapping("/{policyId}/transactions")
	public List<Transaction> getAllTransactions(@PathVariable Long policyId);
	
	
	@PostMapping("/payment")
	public int insertTransaction(@RequestBody Transaction trans);

}
