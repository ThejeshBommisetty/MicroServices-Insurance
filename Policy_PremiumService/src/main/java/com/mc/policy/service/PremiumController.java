package com.mc.policy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mc.policy.model.Premium;

@RestController
public class PremiumController {

	@Autowired
	PremiumRepository premiumRepo;
	
	@GetMapping("/{policyId}/premium")
	public Premium getPremium(@PathVariable Long policyId) {
		Premium prem = premiumRepo.findByPolicyId(policyId);
		return prem;
	}
	
	//@PutMapping("/premium")
	public void updatePremium(@RequestBody Premium premium) {
		Optional<Premium>  oldP =  premiumRepo.findById(premium.getId());
		if(oldP.isPresent()) {
			premiumRepo.save(premium);
		}
	}
}
