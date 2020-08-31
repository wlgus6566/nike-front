package com.nike.dnp.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class TestUtil {


    public static void main(String[] args) throws IOException, MessagingException {

        TestUtil test = new TestUtil();
        test.doTest2();
    }


    private void doTest2(){

//        String user = "cokeplay.emotion@emotion.co.kr"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
//        String password = "5Rzz*w#,Kc%9W7s";   // 패스워드
        String user = "admin@nikespace.co.kr"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = "2motion12!@";   // 패스워드

        // SMTP 서버 정보를 설정한다.
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");

//        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        props.put("mail.debug", true);
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("chejug@gmail.com"));

            // Subject
            message.setSubject("제목을 입력하세요"); //메일 제목을 입력

            // Text
            message.setText("내용을 입력하세요");    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
            System.out.println("message sent successfully...");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void doTest() {
        String user = "admin@nikespace.co.kr"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = "2motion12!@";   // 패스워드

        final String from = "yourUserName@office365.com";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });


        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    "chejug@gmail.com");
            msg.setSubject("Testing SMTP using ["+ from + "]");
            msg.setSentDate(new Date());
            msg.setText("Hey, this is a test from [" + from + "]");
            Transport.send(msg);

        } catch (MessagingException e) {
            System.out.println("send failed, exception: " + e);
        }
        System.out.println("Sent Ok") ;
    }

}
