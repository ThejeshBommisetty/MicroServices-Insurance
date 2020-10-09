package com.mc.policy.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mc.policy.model.Premium;

public interface PremiumRepository  extends JpaRepository<Premium, Long>{
	
	@Query("select p from premium p where p.policyId=?1")
	public Premium findByPolicyId(Long policyId);

	
}
