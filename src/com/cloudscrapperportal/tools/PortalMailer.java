package com.cloudscrapperportal.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class PortalMailer {

	public static final int VERIFY = 1;
	
//TODO download javamail	
	public static void sendMail (Properties smtpProps, String mailTo, String mailToName, int emailType, String uuid, String contextPath) throws PortalMailerException {
		
		//email not valid
		if (!EmailValidator.getInstance().isValid(mailTo)) {
			throw new PortalMailerException("Invalid Email Address");
		}

		String subject = getSubject(emailType, smtpProps);
		String body = getBody(emailType, mailTo, mailToName, uuid, contextPath, smtpProps);
		
		// Create a Properties object to contain connection configuration information.
	    Properties sendMailProps = getSendMailProperties(smtpProps.get("smtpPort").toString());

	    // Create a Session object to represent a mail session with the specified properties. 
	   	Session session = Session.getDefaultInstance(sendMailProps);

	   	// Create a message with the specified information. 
	    MimeMessage msg = new MimeMessage(session);
	    try {
	    	msg.setFrom(new InternetAddress(smtpProps.getProperty("smtpFrom").toString()));
	    	msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
	    	msg.setSubject(subject);
	    	msg.setContent(body,"text/html");
	    } catch (MessagingException me) {
	    	throw new PortalMailerException ("Error creating the email message: " + me.getMessage());
	    }
	            
	    // Create a transport.
	    Transport transport = null;
	    try {
	    	transport = session.getTransport();
	    } catch (NoSuchProviderException ne) {
	    	throw new PortalMailerException ("No provider to send email: " + ne.getMessage());
	    }

	    // Send the message.
	    try {
	    	//change this to log4j
	    	System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
	    	// Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(smtpProps.getProperty("smtpHost").toString(), smtpProps.getProperty("smtpUsername").toString(), smtpProps.getProperty("smtpPassword").toString());
	        	
	        // Send the email.
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	        System.out.println("Email sent!");
	    } catch (Exception ex) {
	         System.out.println("The email was not sent.");
	         throw new PortalMailerException("Could not send verification email: " + ex.getMessage());
	    }		
	}
	
	private static String getSubject(int emailType, Properties smtpProps) throws PortalMailerException {
		switch(emailType) {
			case VERIFY:
				return smtpProps.getProperty("smtpVerifyEmailSubject");
			default:
				throw new PortalMailerException("Unknown email type");
		}
	}
	
	private static String getBody(int emailType, String mailTo, String mailToName, String uuid, String contextPath, Properties props) throws PortalMailerException {
		String mailTemplatePath = "";
		String mailTemplate = "";
		String verifyLink = "";
		
		switch(emailType) {
			case VERIFY:
				mailTemplatePath = contextPath + "/verifyEmail.template";
				verifyLink = props.getProperty("smtpVerifyEmailURL").concat("?mailTo=").concat(mailTo).concat("&verify=").concat(uuid); 
				break;
			default:
				throw new PortalMailerException("Unknown email type");
		}
		
		try {
			FileInputStream inputStream = new FileInputStream(mailTemplatePath);
	        mailTemplate = IOUtils.toString(inputStream);
	        inputStream.close();
	    } catch (IOException e) {
	    	throw new PortalMailerException("Error reading template file: " + e.getMessage());
	    }

		mailTemplate = mailTemplate.replace("%MAILTONAME%", mailToName);
		mailTemplate = mailTemplate.replaceAll("%VERIFYLINK%", verifyLink);
		
		return mailTemplate;
	}
	
	private static Properties getSendMailProperties (String smtpPort) {
		Properties sendMailProps = System.getProperties();
		sendMailProps.put("mail.transport.protocol", "smtps");
		sendMailProps.put("mail.smtp.port", smtpPort); 
    	sendMailProps.put("mail.smtps.auth", "true");
		sendMailProps.put("mail.smtp.ssl.enable", "true");
		return sendMailProps;
	}
}
