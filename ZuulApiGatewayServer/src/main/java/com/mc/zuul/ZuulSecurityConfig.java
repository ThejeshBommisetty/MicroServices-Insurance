package com.mc.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ZuulSecurityConfig extends WebSecurityConfigurerAdapter{

	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  	auth
		  	// enable in memory based authentication with a user named
		  	// "user" and "admin"
		  	.inMemoryAuthentication()
		  	.passwordEncoder(org.springframework.security.crypto.password.
		  			NoOpPasswordEncoder.getInstance()).withUser("user1").password("secret1")
			.roles("USER").and().withUser("admin1").password("secret1").roles("USER", "ADMIN");
		  }
	
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http.httpBasic().and().authorizeRequests()
			.antMatchers("/customer-api/**").hasRole("USER")
			.antMatchers("/**").hasRole("ADMIN")
			.and().csrf().disable()
			//.formLogin().and()
			.headers().frameOptions().disable();
		
	}
	
}
