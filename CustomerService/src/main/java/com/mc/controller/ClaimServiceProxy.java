package com.mc.controller;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mc.model.Claim;


@FeignClient(name="claimService" ) //,url="localhost:8200"
@RibbonClient(name="claimService")
public interface ClaimServiceProxy {
	
	@GetMapping("/{custId}/claims")
	public List<Claim> getClaims(@PathVariable Long custId);
	
	/*@GetMapping("/claims/{id}")
	public Claim getClaimsById(@PathVariable Long id);*/
	
	@PostMapping("/fileClaim")
	public Claim createClaim(@RequestBody Claim claim);
	
	@GetMapping("/{custId}/claims/{id}")
	public Claim getClaimByCustId(@PathVariable Long custId, @PathVariable Long id);
}
