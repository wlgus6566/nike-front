package com.nike.dnp.common.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class Send email office 365.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오전 11:44:13
 * @Description Office365용 Email 전송 Service 작성
 */
@Service
public class SendEmailOffice365 {

    /**
     * The constant LOGGER
     *
     * @author [오지훈]
     */
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * The constant SERVIDOR_SMTP
     *
     * @author [오지훈]
     */
    private static final String SERVIDOR_SMTP = "smtp.office365.com";
    /**
     * The constant PORTA_SERVIDOR_SMTP
     *
     * @author [오지훈]
     */
    private static final int PORTA_SERVIDOR_SMTP = 587;
    /**
     * The constant CONTA_PADRAO
     *
     * @author [오지훈]
     */
    private static final String CONTA_PADRAO = "cokeplay.emotion@emotion.co.kr";
    /**
     * The constant SENHA_CONTA_PADRAO
     *
     * @author [오지훈]
     */
    private static final String SENHA_CONTA_PADRAO = "5Rzz*w#,Kc%9W7s";

    //private final String from = "cokeplay.emotion@emotion.co.kr";
    //private final String to = "jihoon.oh@emotion.co.kr";
    //private String subject = "[NIKE D&P] Test";
    //private String messageContent = "[NIKE D&P] 발신 테스트 메일입니다.";

    /**
     * Send email.
     *
     * @param from    the from
     * @param to      the to
     * @param subject the subject
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오전 11:44:14
     * @Description 메일 발송
     */
    public void sendEmail(final String from, final String to, final String subject) {
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            //message.setText(getFile());

            MimeBodyPart mimeMultipart = new MimeBodyPart();
            mimeMultipart.setContent(getFile(), "text/html; charset=UTF-8");

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mimeMultipart);

            message.setContent(mp);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (final MessagingException ex) {
            LOGGER.log(Level.WARNING, "Erro ao enviar mensagem: " + ex.getMessage(), ex);
        }
    }

    /**
     * Gets file.
     *
     * @return the file
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:05:10
     * @Description 파일읽기 및 내용작성
     */
    public String getFile() {
        String result = "";
        try {
            //BufferedReader in = new BufferedReader(new InputStreamReader(new ClassPathResource("templates/email/E_MAIL_04.html").getInputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(new ClassPathResource("templates/email/NIKE_P_MANAGE_02_001e.html").getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("===================================");
            System.out.println(response.toString());
            result = response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gets email properties.
     *
     * @return the email properties
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오전 11:44:14
     * @Description properties 설정
     */
    public Properties getEmailProperties() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVIDOR_SMTP);
        config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
        return config;
    }

}