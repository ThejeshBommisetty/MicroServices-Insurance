
package com.mc.claim.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mc.claim.model.ClaimBean;

@RestController
public class ClaimService {
	
	@Autowired
	ClaimRepository claimRepo;
 	
	@PostMapping("/fileClaim")
	public ClaimBean createClaim(@RequestBody ClaimBean claim) {
		Calendar cal = new GregorianCalendar();
		java.util.Date uid = cal.getTime();
		Date sqldate = new Date(uid.getTime());
		claim.setClaimDate(sqldate);
		claim.setStatus("Pending");
		
		claimRepo.save(claim);
		Optional<ClaimBean> returnedClaim = claimRepo.findByStatus();
		System.out.println("Claim ID "+returnedClaim.get().getId());
		return returnedClaim.get();
	}
	
	@GetMapping("/claims")
	public List<ClaimBean> getAllClaims(){
		List<ClaimBean> claims = claimRepo.findAll();
		return claims;
	}
	
	@GetMapping("/claims/{id}")
	public ClaimBean getClaimById(@PathVariable Long id){
		Optional<ClaimBean> claims = claimRepo.findById(id);
		if(!claims.isPresent())
		{
			//throw new ClaimNotFoundException("id "+id);
			ClaimBean exClaim =  new ClaimBean();
			exClaim.setErrorCode("CLAIM001");
			exClaim.setErrorMessage("Claim doesnt exist for the claim id - "+id);
			exClaim.setErrorTimestamp(new java.util.Date());
			return exClaim;
		}
		return claims.get();
	}
	@GetMapping("/{custId}/claims/{id}")
	public ClaimBean getClaimByCustIdandClaimId(@PathVariable Long custId, @PathVariable Long id){
		Optional<ClaimBean> claims = claimRepo.findByCustIdandClaimId(id, custId);
		
		if(claims.isEmpty())
		{
			//throw new ClaimNotFoundException("id "+id);
			ClaimBean exClaim =  new ClaimBean();
			exClaim.setErrorCode("CLAIM001");
			exClaim.setErrorMessage("Claim doesnt exist for the claim id - "+id);
			exClaim.setErrorTimestamp(new java.util.Date());
			return exClaim;
		}
		return claims.get();
	}
	@GetMapping("/{custId}/claims")
	public List<ClaimBean> getClaimByCustId(@PathVariable Long custId){
		List<ClaimBean> claims = claimRepo.findByCustId(custId);
		if(claims.isEmpty()) {
			ClaimBean exClaim =  new ClaimBean();
			exClaim.setErrorCode("CLAIM001");
			exClaim.setErrorMessage("Claims doesnt exist for the customer - "+custId);
			exClaim.setErrorTimestamp(new java.util.Date());
			return Arrays.asList(exClaim);
		}
		return claims;
	}
	
	@Scheduled(cron = "0 0/2 * * * *")
	private void processPayment() {
		List<ClaimBean> claims = claimRepo.findAll();
		List<ClaimBean> pendingClaims =claims.stream().
				filter(trans -> trans.getStatus().equals("Pending"))
				.collect(Collectors.toList());
		Iterator<ClaimBean> it = pendingClaims.iterator();
		while(it.hasNext()) {
			ClaimBean t = it.next();
			t.setStatus("Success");
		}
		if(!pendingClaims.isEmpty()) {
			claimRepo.saveAll(pendingClaims);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm:ss");
			System.out.println("Claim Status updated to Success at "
					+ dateFormat.format(new java.util.Date()));
		}else {
			System.out.println("No pending claims to process");
		}
		
	}
	
}
