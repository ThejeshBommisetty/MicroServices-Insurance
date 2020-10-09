package com.mc.claim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mc.claim.model.ClaimBean;

public interface ClaimRepository extends JpaRepository<ClaimBean, Long> {
	
	@Query("select c from claims c where c.id=?1 and c.custId=?2")
	Optional<ClaimBean> findByCustIdandClaimId(Long id, Long custId);
	
	@Query("select c from claims c where c.custId=?1")
	List<ClaimBean> findByCustId(Long custId);
	
	@Query("select c from claims c where c.status='Pending'")
	Optional<ClaimBean> findByStatus();

}
