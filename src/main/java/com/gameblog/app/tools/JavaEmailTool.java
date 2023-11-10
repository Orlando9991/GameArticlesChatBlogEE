/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gameblog.app.tools;

import com.gameblog.app.model.Email;
import com.gameblog.app.service.user.UserHandle;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author orlan
 */
public final class JavaEmailTool {
    
    private static final Logger logger = Logger.getLogger(JavaEmailTool.class.getName());
    private static JavaEmailTool javaEmailTool; 
    private final static String ACCOUNT_EMAIL = "gameblogsuport@gmail.com";
    private final static String ACCOUNT_PASSWORD = "snrv cmkn sysu dgtq";
    private static Authenticator auth;
    private static Properties properties;
    
    public static JavaEmailTool getInstance(){
        if(javaEmailTool == null){
            javaEmailTool = new JavaEmailTool();
        }
        return javaEmailTool;
    }
     
    private JavaEmailTool() {   
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host","smtp.gmail.com" );
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(ACCOUNT_EMAIL,ACCOUNT_PASSWORD);
            }
        }; 
    }

    public void sendEmail(Email email){
        try {
            logger.log(Level.SEVERE, "Prepare for sending message");          
            Session session = Session.getInstance(properties,auth);
            Message message = prepareMessage(session, email);
            Transport.send(message);  
            logger.log(Level.SEVERE, "message sent");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "message not sent", e);
        }
    }
    
    private Message prepareMessage(Session session, Email email) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ACCOUNT_EMAIL));
            
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecepient()));
            message.setSubject(email.getSubject());
            message.setText(email.getTextBody());
            return message;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error on prepare message", e);
        }
        return null;
    }

    public static String getACCOUNT_EMAIL() {
        return ACCOUNT_EMAIL;
    }
    
}
