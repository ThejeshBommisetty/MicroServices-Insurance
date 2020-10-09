package com.mc.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PolicyPremiumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyPremiumServiceApplication.class, args);
	}

}
