package com.spire.talent.tests;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

public class URLReader {
   public static void main(String[] args) throws Exception {
	  /* String replaceDate = "13_Feb_2017";
	   String a = "C:/Program Files(x86)/ABC/XYZ/PQR.exe /GUEST3 /F:C:/MyData/ppp.txt";
	   String arrayA [] =a.split("/");	 	   
	   System.out.println("file path is >>>> " + arrayA[ arrayA.length-1]);*/
	   
	   Properties props = new Properties();
       props.setProperty("mail.store.protocol", "imaps");
       try {
           Session session = Session.getInstance(props, null);
           Store store = session.getStore();
           store.connect("imap.gmail.com", "staging.crm.test@spire2grow.com", "spire@123");
           Folder inbox = store.getFolder("INBOX");
           inbox.open(Folder.READ_ONLY);
           Message msg = inbox.getMessage(inbox.getMessageCount());
           Address[] in = msg.getFrom();
           for (Address address : in) {
               System.out.println("FROM:" + address.toString());
           }
           Multipart mp = (Multipart) msg.getContent();
           BodyPart bp = mp.getBodyPart(0);
           System.out.println("SENT DATE:" + msg.getSentDate());
           System.out.println("SUBJECT:" + msg.getSubject());
           System.out.println("CONTENT:" + bp.getContent());
       } catch (Exception mex) {
           mex.printStackTrace();
       }
   }
}