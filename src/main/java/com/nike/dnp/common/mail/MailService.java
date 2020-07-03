package com.nike.dnp.common.mail;

import com.nike.dnp.dto.email.SendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * The Class Mail service.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 1. 오후 2:31:56
 * @Description
 */
@Service
@Slf4j
public class MailService {

    /**
     * The Send email office 365
     *
     * @author [오지훈]
     */
    @Autowired
    private SendEmailOffice365 sendEmailOffice365;

    /**
     * Send mail.
     *
     * @param emailType the email type
     * @param subject   the subject
     * @param sendDTO   the send dto
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:31:56
     * @Description
     */
    public void sendMail(String emailType, String subject, SendDTO sendDTO) {
        sendEmailOffice365.sendEmail(
                sendDTO.getEmail()
                , subject
                , this.convert(this.getFile(emailType), sendDTO)
        );
    }

    /**
     * Gets file.
     *
     * @param emailType the email type
     * @return the file
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:31:56
     * @Description template 파일 read > String 으로 저장
     */
    public String getFile(final String emailType) {
        String result = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new ClassPathResource("templates/email/NIKE_DNP_"+emailType+".html").getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            result = response.toString();

        } catch (IOException e) {
            e.printStackTrace();
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
     * @CreatedOn 2020. 7. 1. 오후 2:31:56
     * @Description template String 문자열의 대체변수에 데이터 대입
     */
    public String convert(String file, SendDTO sendDTO) {
        log.info("MailService.convert");
        String result = file;
        for(Field field : sendDTO.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (!ObjectUtils.isEmpty(field.get(sendDTO))
                        && "String".equals(field.getType().getSimpleName())
                        && file.contains("["+ field.getName() +"]")
                ) {
                    result = result.replace("["+ field.getName() +"]", (String) field.get(sendDTO));
                }
            } catch (IllegalAccessException e) {
                log.error("exception", e);
            }
        }
        return result;
    }

}