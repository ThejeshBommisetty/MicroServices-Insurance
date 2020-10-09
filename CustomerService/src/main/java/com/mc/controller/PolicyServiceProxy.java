package com.mc.controller;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.mc.model.Policy;

@FeignClient(name ="policy-premium-service")	//, url="localhost:8300")
@RibbonClient(name="policy-premium-service")
public interface PolicyServiceProxy {
	
	@GetMapping("/{custId}/policy")
	public List<Policy> getPolicies(@PathVariable Long custId);
	
	@PutMapping("/policy/{id}")
	public Policy updatePolicy(@PathVariable Long id);
}
