package com.kh.spring.common.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.kh.spring.common.code.Config;

@Component
public class MailSender {

	// 얘는 빈으로 등록되어있음.
	JavaMailSenderImpl mailSender;

	public MailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	
	//받는사람 , 제목 , 내용
	public void send(String to, String subject, String htmlTxt) {
		
		MimeMessage msg = mailSender.createMimeMessage();
		
		try {
			
			msg.setFrom(Config.COMPANY_EMAIL.DESC);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(htmlTxt, "UTF-8", "html");
			msg.setRecipients(Message.RecipientType.TO, to);
			mailSender.send(msg);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
