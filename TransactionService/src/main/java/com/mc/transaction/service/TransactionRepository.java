package com.mc.transaction.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mc.transaction.model.Transaction;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	
	@Query("select t from transaction t where policyId=?1")
	public List<Transaction> getByPolicy(Long policyId);
}
