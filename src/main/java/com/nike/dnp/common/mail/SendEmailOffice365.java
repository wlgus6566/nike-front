package com.nike.dnp.common.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The Class Send email office 365.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오전 11:44:13
 * @Description Office365용 Email 전송 Service 작성
 */
@Service
@Slf4j
@AllArgsConstructor
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
    @Value("${nike.email.auth.id:}")
    private static transient String CONTA_PADRAO;

    /**
     * The constant SENHA_CONTA_PADRAO
     *
     * @author [오지훈]
     */
    @Value("${nike.email.auth.pw:}")
    private static transient String SENHA_CONTA_PADRAO;

    /**
     * The From email
     *
     * @author [오지훈]
     */
    @Value("${nike.email.send.from:}")
    private static transient String FROM_EMAIL;

    /**
     * Send email.
     *
     * @param toEmail the to email
     * @param subject the subject
     * @param file    the file
     * @author [오지훈]
     * @CreatedOn 2020. 7. 14. 오전 11:53:51
     * @Description
     */
    public void sendEmail(final String toEmail, final String subject, final String file) {
        this.sendEmail(FROM_EMAIL, toEmail, subject, file);
    }

    /**
     * Send email.
     *
     * @param from    the from
     * @param toEmail the to
     * @param subject the subject
     * @param file    the file
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오전 11:44:14
     * @Description 메일 발송
     */
    public void sendEmail(final String from, final String toEmail, final String subject, final String file) {
        log.info("SendEmailOffice365.sendEmail");
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);

            final MimeBodyPart mimeMultipart = new MimeBodyPart();
            mimeMultipart.setContent(file, "text/html; charset=UTF-8");
            final Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeMultipart);

            message.setContent(multipart);
            message.setSentDate(new Date());
            Transport.send(message);
            System.out.println("발송완료");
            System.out.println("======================================================");
        } catch (final MessagingException exception) {
            log.error("exception", exception);
        }
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