/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.commons.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author kaitsu
 */
public class SendMail {

    
    public void send(String Email,String Body) throws AddressException, MessagingException
    {

             String host ="mail.digidieetti.net";
             String from ="info@digidieetti.net";  //Your mail id
             String pass ="heippa27";   // Your Password
             Properties props = System.getProperties();
             props.put("mail.smtp.starttls.enable", "true"); // added this line
             props.put("mail.smtp.host", host);
             props.put("mail.smtp.user", from);
             props.put("mail.smtp.password", pass);
             props.put("mail.smtp.port", "25");
             props.put("mail.smtp.auth", "true");
             String[] to = {Email}; // To Email address
             Session session = Session.getDefaultInstance(props, null);
             MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(from));
             InternetAddress[] toAddress = new InternetAddress[to.length];        
             // To get the array of addresses
              for( int i=0; i < to.length; i++ )
              { // changed from a while loop
                  toAddress[i] = new InternetAddress(to[i]);
              }
             System.out.println(Message.RecipientType.TO);
             for( int j=0; j < toAddress.length; j++)
             { // changed from a while loop
             message.addRecipient(Message.RecipientType.TO, toAddress[j]);
             }
             message.setSubject("SendMail kokeilu");

             message.setContent(Body,"text/html");
             Transport transport = session.getTransport("smtp");
             transport.connect(host, from, pass);
             transport.sendMessage(message, message.getAllRecipients());
                 transport.close();
        }
    }