package com.nike.dnp.common.mail;

import com.nike.dnp.dto.email.SendDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * The Class Mail service.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 1. 오후 2:31:56
 * @Description
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
     * Send mail.
     *
     * @param emailType the email type
     * @param subject   the subject
     * @param sendDTO   the send dto
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:31:56
     * @Description
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
     * Gets file.
     *
     * @param emailType the email type
     * @return the file
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:31:56
     * @Description template 파일 read > String 으로 저장
     */
    public String getFile(final String emailType) {
        log.info("MailService.getFile");
        String result = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("templates/email/NIKESPACE_"+emailType+".html").getInputStream()));
            /*String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }*/
            //TODO[ojh] 2020-07-21 : 기능 작동하는지 확인 안될 경우 위의 주석 해제
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
     * @CreatedOn 2020. 7. 1. 오후 2:31:56
     * @Description template String 문자열의 대체변수에 데이터 대입
     */
    public String convert(final String file, final SendDTO sendDTO) {
        log.info("MailService.convert");
        String result = file;
        for(final Field field : sendDTO.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (!ObjectUtils.isEmpty(field.get(sendDTO))
                        && BasicVariable.String.toString().equals(field.getType().getSimpleName())
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

    /**
     * The enum Basic variable.
     *
     * @author [오지훈]
     */
    public enum BasicVariable {
        String
    }
}
