package com.mc.policy.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.policy.model.Policy;
import com.mc.policy.model.Premium;

@RestController
public class PolicyController {

	
	@Autowired
	PolicyRepository policyRepo;
	
	@Autowired
	PremiumController premCont;
	
	@GetMapping("/{custId}/policy")
	public List<Policy> getPolicies(@PathVariable Long custId) {
		List<Policy> policies =  policyRepo.findByCustId(custId);
		for (Policy policy : policies) {
			Premium prem = premCont.getPremium(policy.getId());
			prem.setDueDate(dueDateCalc(policy));
			prem.setDueAmount(dueAmountCalc(policy, prem));
			policy.setPremium(prem);
		}
		return policies;
	}
	
	@PutMapping("/policy/{id}")
	public Policy updatePolicy(@PathVariable Long id) {
		Optional<Policy> policy = policyRepo.findById(id);
		if(policy.isPresent()) {
			Premium oldPrem= premCont.getPremium(id);
			BigDecimal prem =  policy.get().getAmount().divide(BigDecimal.valueOf(policy.get().getTenure()));
			oldPrem.setDueDate(dueDateCalc(policy.get()));
			oldPrem.setDueAmount(dueAmountCalc(policy.get(), oldPrem));			
			oldPrem.setRemainingTenure(oldPrem.getRemainingTenure()-1);
			oldPrem.setPaidAmount(oldPrem.getPaidAmount().add(prem));
			premCont.updatePremium(oldPrem);
			policy.get().setPremium(oldPrem);
			return policy.get(); 
		}
		else
			throw new RuntimeException("Policy Not Found");
		
	}
	/*
	 * Due Date will be every month with start day from policy
	 * 
	 * Get current month and set to start Date for Due Date  
	 * 01/07/2020 --startDate
	 * 25/09/2020 --presentDate
	 * 
	 * DueDate --01/09/2020
	 */
	private Date dueDateCalc(Policy policy) {
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(policy.getStartDate());
		Calendar presentCalendar = new GregorianCalendar();
		int month = presentCalendar.get(Calendar.MONTH);
		
		startCalendar.set(Calendar.MONTH,month);
		java.util.Date uid = startCalendar.getTime();
		Date dueDate = new Date(uid.getTime());
		return dueDate;
		
	}
	
	/*
	 * Customer pays first premium at the policy beginning
	 * we will check his paid premium Tenure with respect to dueDate and with respect to
	 * the remaining tenure from the DB.
	 * If both tenures are equal , show 0 as dueAmount else show Montly Premium amount
	 * 
	 */
	private BigDecimal dueAmountCalc(Policy policy, Premium prem) {
		int totalTenure = policy.getTenure();
		BigDecimal premAmount = policy.getAmount().divide(BigDecimal.valueOf(totalTenure));
		
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(policy.getStartDate());
		Calendar dueCalendar = new GregorianCalendar();
		dueCalendar.setTime(prem.getDueDate());
		
		int diffYear = dueCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int expPaidTenure = diffYear * 12 + dueCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)+1;
		int actPaidTenure = totalTenure - prem.getRemainingTenure();
		
		if(expPaidTenure==actPaidTenure){
			premAmount= BigDecimal.valueOf(0);
		}
		return premAmount;
	}
	
	
	/*
	 * private Premium dueCalculator(Policy policy, Premium prem) { Calendar
	 * startCalendar = new GregorianCalendar();
	 * startCalendar.setTime(policy.getStartDate()); Calendar endCalendar = new
	 * GregorianCalendar(); endCalendar.setTime(policy.getEndDate());
	 * //endCalendar.add(Calendar.MONTH, -5); int diffYear =
	 * endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR); int
	 * totalTenure = diffYear * 12 + endCalendar.get(Calendar.MONTH) -
	 * startCalendar.get(Calendar.MONTH);
	 * 
	 * BigDecimal premAmount =
	 * policy.getAmount().divide(BigDecimal.valueOf(totalTenure)); int paidTenure =
	 * totalTenure - prem.getRemainingTenure();
	 * 
	 * startCalendar.add(Calendar.MONTH, paidTenure); Date dueDate = (Date)
	 * startCalendar.getTime(); prem.setDueDate(dueDate); if(prem.getPaidAmount() ==
	 * premAmount.multiply(BigDecimal.valueOf(paidTenure))){
	 * prem.setDueAmount(BigDecimal.valueOf(0)); }else {
	 * prem.setDueAmount(premAmount); }
	 * 
	 * return prem; }
	 */
}
