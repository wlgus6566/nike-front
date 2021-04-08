package com.nike.dnp.common.mail;

import com.nike.dnp.dto.email.SendCountDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.entity.log.EmailSendingLog;
import com.nike.dnp.entity.log.UserActionLog;
import com.nike.dnp.repository.log.EmailSendingLogRepository;
import com.nike.dnp.repository.log.UserActionLogRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class Mail service.
 *
 * @author [오지훈]
 * @implNote
 * @since 2020. 7. 1. 오후 2:31:56
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    /**
     * The Send email office 365
     *
     * @author [오지훈]
     */
    private final SendEmailOffice365 sendEmailOffice365;

    /**
     * The Email sending log repository
     */
    private final EmailSendingLogRepository emailSendingLogRepository;

    /**
     * The User action log repository
     */
    private final UserActionLogRepository userActionLogRepository;

    /**
     * Send mail.
     *
     * @param emailType the email type
     * @param subject   the subject
     * @param sendDTO   the send dto
     * @author [오지훈]
     * @implNote 설명]
     * @since 2020. 7. 1. 오후 2:31:56
     */
    public void sendMail(final String emailType, final String subject, final SendDTO sendDTO) {
        log.info("MailService.sendMail");
        sendEmailOffice365.sendEmail(
                sendDTO.getEmail()
                , subject
                , this.convert(this.getFile(emailType), sendDTO)
        );
    }

    /**
     * Send mails.
     *
     * @param emailType the email type
     * @param subject   the subject
     * @param sendDTO   the send dto
     * @author [오지훈]
     * @implNote 설명]
     * @since 2020. 7. 1. 오후 2:31:56
     */
    public void sendMails(final String emailType, final String subject, final SendDTO sendDTO) {
        log.info("MailService.sendMails");
        sendEmailOffice365.sendEmails(
                sendDTO.getEmails()
                , subject
                , this.convert(this.getFile(emailType), sendDTO)
        );
    }

    /**
     * Check send mail list.
     *
     * @return list
     * @author [이소정]
     * @implNote 메일 발송 확인
     * @since 2021. 4. 8. 오후 3:25:36
     */
    public List<SendCountDTO> checkSendMail() {
        log.info("MailService.checkSendMail");

        List<UserActionLog> userActionLogList = userActionLogRepository.findAllByUrl("/api/contents/sendMail");
        List<SendCountDTO> sendCountDTOList = new ArrayList<>();

        userActionLogList.stream().forEach(item -> {
                String parameter = item.getParameter();

                int seq_target_num = parameter.indexOf("contentsSeq=");
                String contentsSeq = parameter.substring(seq_target_num,(parameter.substring(seq_target_num).indexOf(",")+seq_target_num)).split("=")[1];

                String urlSub = "contentsUrl=";
                int url_target_num = parameter.indexOf(urlSub);


                String contentsUrl = parameter.substring(url_target_num,(parameter.substring(url_target_num).indexOf("),")+url_target_num)).split("=")[1].split(",")[0];


                int count = emailSendingLogRepository.countAllByContentsContainingAndTitle("nikespace.co.kr" + contentsUrl, "[NIKE SPACE] 컨텐츠 업데이트 알림");

                SendCountDTO sendCountDTO = new SendCountDTO();
                sendCountDTO.setContentsSeq(contentsSeq);
                sendCountDTO.setSendCount(count);
                sendCountDTO.setSendActionDate(item.getRegistrationDt().toString());
                sendCountDTOList.add(sendCountDTO);

            }
        );

        return sendCountDTOList;

    }
    /*public void sendMails(final String emailType, final String subject, final SendDTO sendDTO) {
        log.info("MailService.sendMails");
        for (String email : sendDTO.getEmails()) {
            if (EmailPatternUtil.isValidEmail(email)) {
                sendDTO.setEmail(email);
                sendEmailOffice365.sendEmail(
                        email
                        , subject
                        , this.convert(this.getFile(emailType), sendDTO)
                );
            }
        }

    }*/

    /**
     * Gets file.
     *
     * @param emailType the email type
     * @return the file
     * @author [오지훈]
     * @implNote template 파일 read > String 으로 저장
     * @since 2020. 7. 1. 오후 2:31:56
     */
    public String getFile(final String emailType) {
        log.info("MailService.getFile");
        String result = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("templates/email/NIKESPACE_"+emailType+".html").getInputStream()));
            final String response = bufferedReader.lines().collect(Collectors.joining());
            result = response;
            bufferedReader.close();
        } catch (IOException exception) {
            log.error("exception", exception);
        }

        return result;
    }

    /**
     * Convert string.
     *
     * @param file    the file
     * @param sendDTO the send dto
     * @return the string
     * @author [오지훈]
     * @implNote template String 문자열의 대체변수에 데이터 대입
     * @since 2020. 7. 1. 오후 2:31:56
     */
    public String convert(final String file, final SendDTO sendDTO) {
        log.info("MailService.convert");
        String result = file;
        for(final Field field : sendDTO.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (!ObjectUtils.isEmpty(field.get(sendDTO))
                        && BasicVariable.STRING.getValue().equals(field.getType().getSimpleName())
                        && file.contains("["+ field.getName() +"]")
                ) {
                    result = result.replace("["+ field.getName() +"]", (String) field.get(sendDTO));
                } else if (ObjectUtils.isEmpty(field.get(sendDTO))
                        && BasicVariable.STRING.getValue().equals(field.getType().getSimpleName())
                        && file.contains("["+ field.getName() +"]")
                ) {
                    result = result.replace("["+ field.getName() +"]", "");
                }
            } catch (IllegalAccessException exception) {
                log.error("exception", exception);
            }
        }
        return result;
    }

    /**
     * The enum Basic variable.
     *
     * @author [오지훈]
     * @since 2021. 4. 8. 오후 3:25:36
     */
    @Getter
    @RequiredArgsConstructor
    public enum BasicVariable {
        /**
         * String basic variable
         *
         * @author [이소정]
         */
        STRING("String");

        /**
         * The Value
         *
         * @author [오지훈]
         */
        private final String value;
    }
}
