package com.mc.transaction.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mc.transaction.model.Transaction;

@RestController
public class TransactionController {

	@Autowired
	TransactionRepository transRepo;
	
	@GetMapping("/{policyId}/transactions")
	public List<Transaction> getAllTransactions(@PathVariable Long policyId){
		return transRepo.getByPolicy(policyId);

	}
	
	@PostMapping("/payment")
	public int insertTransaction(@RequestBody Transaction trans) {
		Calendar cal = new GregorianCalendar();
		java.util.Date uid = cal.getTime();
		Date sqldate = new Date(uid.getTime());
		trans.setDateOfTransaction(sqldate);
		int randomNum = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
		trans.setId(Long.valueOf(randomNum));
		//System.out.println(trans.getDateOfTransaction().toString());
		trans.setStatus("Pending");
		transRepo.save(trans);
		return randomNum;
	}
	
	@Scheduled(cron = "0 0/2 * * * *")
	private void processPayment() {
		List<Transaction> transactions = transRepo.findAll();
		List<Transaction> pendingTransactions =transactions.stream().
				filter(trans -> trans.getStatus().equals("Pending"))
				.collect(Collectors.toList());
		Iterator<Transaction> it = pendingTransactions.iterator();
		while(it.hasNext()) {
			Transaction t = it.next();
			t.setStatus("Success");
		}
		transRepo.saveAll(pendingTransactions);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		if(pendingTransactions.isEmpty()) {
			System.out.println("No Transactions to Process");
		}
		else
			System.out.println("Transaction Status updated to Success at "
				+ dateFormat.format(new java.util.Date()));
	}
}
