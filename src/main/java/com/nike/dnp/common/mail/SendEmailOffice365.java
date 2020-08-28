package com.nike.dnp.common.mail;

import com.nike.dnp.dto.log.EmailSendingLogSaveDTO;
import com.nike.dnp.service.log.EmailSendingLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * The Class Send email office 365.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오전 11:44:13
 * @implNote Office365용 Email 전송 Service 작성
 */
@Service
@Slf4j
@AllArgsConstructor
public class SendEmailOffice365 {

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
    public static String contaPadrao;

    /**
     * The constant SENHA_CONTA_PADRAO
     *
     * @author [오지훈]
     */
    public static String senhaContaPadrao;

    /**
     * The From email
     *
     * @author [오지훈]
     */
    public static String fromEmail;

    /**
     * The Email sending log service
     *
     * @author [오지훈]
     */
    private final EmailSendingLogService emailSendingLogService;

    /**
     * Sets conta padrao.
     *
     * @param contaPadrao the conta padrao
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 2:52:48
     * @implNote
     */
    @Value("${nike.email.auth.id:}")
    public void setContaPadrao(final String contaPadrao) {
        SendEmailOffice365.contaPadrao = contaPadrao;
    }

    /**
     * Sets senha conta padrao.
     *
     * @param senhaContaPadrao the senha conta padrao
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 2:52:49
     * @implNote
     */
    @Value("${nike.email.auth.pw:}")
    public void setSenhaContaPadrao(final String senhaContaPadrao) {
        SendEmailOffice365.senhaContaPadrao = senhaContaPadrao;
    }

    /**
     * Sets from email.
     *
     * @param fromEmail the from email
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 2:52:50
     * @implNote
     */
    @Value("${nike.email.send.from:}")
    public void setFromEmail(final String fromEmail) {
        SendEmailOffice365.fromEmail = fromEmail;
    }

    /**
     * Send email.
     *
     * @param toEmail the to email
     * @param subject the subject
     * @param file    the file
     * @author [오지훈]
     * @since 2020. 7. 14. 오전 11:53:51
     * @implNote
     */
    public void sendEmail(final String toEmail, final String subject, final String file) {
        this.sendEmail(fromEmail, toEmail, subject, file);
    }

    /**
     * Send email.
     *
     * @param fromEmail the from
     * @param toEmail   the to
     * @param subject   the subject
     * @param file      the file
     * @author [오지훈]
     * @since 2020. 6. 24. 오전 11:44:14
     * @implNote 메일 발송
     */
    public void sendEmail(final String fromEmail, final String toEmail, final String subject, final String file) {
        log.info("SendEmailOffice365.sendEmail");
        final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(contaPadrao, senhaContaPadrao);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setFrom(new InternetAddress(fromEmail, "NIKE SPACE", "UTF-8"));
            message.setSubject(subject);

            if (file.isEmpty()) {
                message.setSubject("[NIKE SPACE] 발신 테스트 메일입니다.");
                message.setText("TEST");
            } else {
                final MimeBodyPart mimeMultipart = new MimeBodyPart();
                mimeMultipart.setContent(file, "text/html; charset=UTF-8");
                final Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeMultipart);
                message.setContent(multipart);
            }

            message.setSentDate(new Date());
            Transport.send(message);
            emailSendingLogService.save(
                    EmailSendingLogSaveDTO.builder()
                            .email(toEmail)
                            .title(message.getSubject())
                            .contents(ObjectUtils.isEmpty(file) ? "" : file)
                            .build());

        } catch (final MessagingException | UnsupportedEncodingException exception) {
            log.error("exception", exception);
        }
    }

    /**
     * Gets email properties.
     *
     * @return the email properties
     * @author [오지훈]
     * @since 2020. 6. 24. 오전 11:44:14
     * @implNote properties 설정
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
