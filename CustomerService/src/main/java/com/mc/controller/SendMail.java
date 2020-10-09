package com.mc.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendMail {

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void sendMessage(String to, String subject, String body) throws MessagingException {
		System.out.println("Started");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper ;
		helper = new MimeMessageHelper(message, true);
		//helper.setFrom("MicroCredentials");
		helper.setTo(to);
		helper.setText(body);
		helper.setSubject(subject);
		javaMailSender.send(message);
		System.out.println("Done");
	}
	
}
