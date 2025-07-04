package kada.util;

import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;


public class EmailSender {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("#{info['email.dev']}")
    private Boolean emailDev;
	
	@Value("#{info['email.dev_to']}")
    private String emailDevTo;
	
	@Value("#{info['email.Sender']}")
    private String emailSender;
	
	@Value("#{info['email.SenderPw']}")
    private String emailSenderPw;
	
	public Boolean getEmailDev() {
		return emailDev;
	}

	public String getEmailDevTo() {
		return emailDevTo;
	}

	public String getEmailSender() {
		return emailSender;
	}

	public String getEmailSenderPw() {
		return emailSenderPw;
	}

	public Session emailAuthInit() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "mproxy01.mailplug.co.kr");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "mproxy01.mailplug.co.kr");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.debug", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
        	String un = emailSender;
            String pw = emailSenderPw;

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(un, pw);
            }
        });
        
		return session;
	}
	
	
}
