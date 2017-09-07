package com.spire.common;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail{
	
	public static void sendEmail(String to,StringBuilder body,String subject){
		
		  String from = "product-engg-admin-qe@spire2grow.com";

	      // Setup mail server
	      String host = "smtp.googlemail.com";
	      final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	      Properties properties = System.getProperties();
	      properties.put("mail.smtp.auth", "true"); 
	      properties.setProperty("mail.smtp.host", host);
	      properties.setProperty("mail.smtp.port", "465");
	      properties.put("mail.smtp.starttls.enable","true");
	      properties.put("mail.smtp.debug", "true");
	      properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	      properties.setProperty("mail.smtp.socketFactory.fallback", "false");
	      properties.setProperty("mail.smtp.socketFactory.port", "465");
	      
	      Authenticator authenticator = new Authenticator () {
	          public PasswordAuthentication getPasswordAuthentication(){
	              return new PasswordAuthentication("product-engg-admin-qe@spire2grow.com","spire@123"); 
	          }
	      };

	      Session session = Session.getDefaultInstance( properties , authenticator);
	      
	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         //message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.addRecipients(Message.RecipientType.TO, to);
	         
	         StringBuilder sb = Report.closeTable(Report.createBody(Report.createHeader()));
	       
	         Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	         // Set Subject: header field
	         message.setSubject("["+localCalendar.get(Calendar.DATE)+"-"+(localCalendar.get(Calendar.MONTH)+1)+
	        		 "-"+localCalendar.get(Calendar.YEAR)+"] "+subject);
	         message.setContent(body.toString(), "text/html; charset=ISO-8859-1");
	         
	         // Send message
	         Transport.send(message);
	         System.out.println("Sent bug list successfully.");
	      
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}
	
   public static void main(String [] args)throws Exception{    

	   StringBuilder builder=Report.createHeader();
	   
	   builder = Report.createBody(builder);
	   
	   StringBuilder body = Report.closeTable(builder);

	   sendEmail("raghavender.nagabandi@spire2grow.com", body,"Git New Branch Created");
	   
   }
}
