package com.mc.contoller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mc.CustomerServiceApplication;
import com.mc.model.Claim;
import com.mc.model.Policy;
import com.mc.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIT {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	HttpHeaders headers = new HttpHeaders();

	@Before
    public void setupJSONAcceptType() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }
	private String createUrl(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	private java.sql.Date dateFormatter(String date){
		java.util.Date d= new java.util.Date();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date(d.getTime());
	}
	/*
	 * Get customer Info
	 */
	
	/*
	@Test
	public void getCustTest() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> resp = template.exchange(createUrl("/customer/101"), HttpMethod.GET, entity, String.class);
		String expected = "{\"id\":101,\"name\":\"thej\",\"gender\":\"male\",\"age\":23,\"city\":\"mpl\",\"phoneNumber\":9440334306,\"ssn\":876543,\"emailId\":\"thejesh.B@gmail.com\",\"policyNumber\":[3001,3005,3007],\"claimNumber\":[2001,2002,2008,2021]}";
		System.out.println("Response Body for the customer -" + resp.getBody());
		JSONAssert.assertEquals(expected, resp.getBody(), false);

	}
	*/
	
	/*
	 * Positive Test for Get claims by claim Id  
	 */
	@Test
	public void getClaimbyIdtest() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> resp = template.exchange(createUrl("/customer/101/claims/2001"), HttpMethod.GET, entity, String.class);
		// JSONArray arr = new JSONArray();
		String json1 = "{id:2001,custId:101,amount:987654.00,reason:slipp,claimDate:2020-05-12,policyId:3001,status:Success}";
		// arr.put();
		// arr.put("{id:2002,custId:101,amount:100000.00,reason:fastened,claimDate:2020-08-23,policyId:3005,status:Success}");
		// JSONAssert.assertEquals(json1, resp.getBody(), false);
		String expected = "{ id: 2001,    custId: 101,    amount: 987654,    reason: slipp,    policyId: 3001,    claimDate: 2020-05-12,    status: Success}";
		System.out.println("Response Body -" + resp.getBody());
		JSONAssert.assertEquals(expected, resp.getBody(), false);

	}
	
	/*
	 *Positive Test for Get claims by customer Id  
	 */
	
	@Test
	public void getClaimsforCustIdTest() {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List<Claim>> resp = template.exchange(createUrl("/customer/101/claims"), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Claim>>(){});
		//String expected = "{id:2001,custId:101,amount:987654.00,reason:slipp,claimDate:2020-05-12,policyId:3001,status:Success}";
		System.out.println("Response Body of getClaims - "+resp.getBody());
		Claim sampleClaim = new Claim(Long.valueOf(2001),Long.valueOf(101),BigDecimal.valueOf(987654.00).setScale(2, BigDecimal.ROUND_UP),"slipp",dateFormatter("2020-05-12"),3001, "Success", null,null,null);
		Claim respClaim =  resp.getBody().get(0);
		//if(sampleClaim.equals(respClaim)) System.out.println(true); else
		//System.out.println(false);
		System.out.println("SampleClaim in String - "+sampleClaim.toString());
		System.out.println("respClaim in String -"+ respClaim.toString());
		assertTrue(resp.getBody().toString().contains(sampleClaim.toString()));
	}
	
	
	/*
	 * Positive test for filing a claim
	 */

	@Test
	public void fileClaimTest() {
		Claim claim = new Claim();
		claim.setPolicyId(4000);
		claim.setAmount(BigDecimal.valueOf(20000));
		claim.setReason("Leg Fracture");

		HttpEntity<Claim> entity = new HttpEntity<Claim>(claim, headers);
		ResponseEntity<String> resp = template.exchange(createUrl("/customer/101/fileClaim"), HttpMethod.POST, entity,  String.class); 
		System.out.println("File Claim response is -"+ resp.getBody());
		assertTrue(resp.getBody().contains("ClaimNumber is "));
		
	}
	/*
	 * Negative case for get claims by claim ID
	 */
	
	@Test
	public void getClaimsforCustIdNegativeTest() throws JSONException {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> resp = template.exchange(createUrl("/customer/101/claims/2100"), HttpMethod.GET, entity, String.class);
		System.out.println("Response Body of getClaims - "+resp.getBody());
		String expected = "{\"errorCode\": \"CLAIM001\" , \"errorMessage\" : \"Claim doesnt exist for the claim id - 2100\"}";
		//actual =  {"errorCode":"CLAIM001","errorMessage":"Claim doesnt exist for the claim id - 2100","errorTimestamp":"2020-10-08T11:07:47.782+00:00"}
		JSONAssert.assertEquals(expected, resp.getBody(), false);
	}
	
	
	
	
	/*
	 * Postive test for get policy
	 */
	
	@Test
	public void getPolicyforCustTest() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		//ResponseEntity<List<Claim>> resp = template.exchange(createUrl("/customer/101/claims"), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Claim>>(){});
		ResponseEntity<List<Policy>> resp = template.exchange(createUrl("/customer/101/policy"), HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Policy>>(){});
		String expected= "Policy [id=3005, type=GVUL, startDate=2020-07-04, endDate=2020-05-04, custId=101, amount=500000.00, tenure=10, premium=Premium [id=5003, policyId=3005, dueAmount=50000.00, dueDate=2020-10-04, remainingTenure=7, paidAmount=150000.00], transactions=[968645]]";
		System.out.println("Response Body of get Policies - "+resp.getBody());
		System.out.println("Response Body of get Policies in String format - "+resp.getBody().toString());
		assertTrue(resp.getBody().toString().contains(expected));
	}
	
	@Test
	public void makePaymentTest() {
		Transaction trans = new Transaction();
		trans.setAmountPaid(BigDecimal.valueOf(25000));
		trans.setModeOfPayment("Google Pay");

		HttpEntity<Transaction> entity = new HttpEntity<Transaction>(trans, headers);
		ResponseEntity<String> resp = template.exchange(createUrl("/customer/101/payPremium/3004"), HttpMethod.POST, entity,  String.class); 
		System.out.println("File Claim response is -"+ resp.getBody());
		assertTrue(resp.getBody().contains("TransactionNumber "));
		
	}
	
	
}
