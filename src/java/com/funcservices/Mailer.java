/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funcservices;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Mbah Royce
 */
public class Mailer {

    private final static String smtpServer = "smtp.gmail.com";
    private final static String port = "465";
    private final static String sslSocket = "javax.net.ssl.SSLSocketFactory";
    private final static String auth = "true";
    private final static String password = "tolosricardos123";

    public static void send(String from, String to, String sub, String msg) {
        Properties props = new Properties();
        
//         production
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", auth);
        
//        //development
//        props.put("mail.smtp.host", "localhost");
//        props.put("mail.smtp.port", "1025");
//        props.put("mail.smtp.ssl.enable", "false");
//        props.put("mail.smtp.auth", "false");
        //get Session   
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("tolosricardos@gmail.com", "tolosricardos123");
                    }
                });

        //compose message    
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("alemchu123@yahoo.com"));
            message.setSubject(sub);
            message.setText(msg);
            //send message  
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
