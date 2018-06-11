package com.elearningbackend.utility;
import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailService {
    public static void send(String to,String subject,String content) throws ElearningException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "E Learning");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("elearning.english.demo@gmail.com", "@{elearning}@");
                    }
                });
        try {

            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress("elearning.english.demo@gmail.com", "E Learning"));
            } catch (UnsupportedEncodingException e) {
                message.setFrom(new InternetAddress());
                e.printStackTrace();
            }
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content,"text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new ElearningException(Errors.ERROR_SENT_MAIL.getId(),Errors.ERROR_SENT_MAIL.getMessage());
        }
    }
}
