package com.mc.policy.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mc.policy.model.Policy;

@Repository
@Transactional
public interface PolicyRepository  extends JpaRepository<Policy, Long>{

	@Query("select p from policy p where p.custId=?1")
	List<Policy> findByCustId(Long custId);

	
	
}
