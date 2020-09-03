package com.nike.dnp.service.user;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


/**
 * UserMailService
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 2:40:43
 * @implNote User(유저) Mail Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMailService {

    /**
     * PC Login Url
     *
     * @author [오지훈]
     */
    @Value("${nike.url.login}")
    private String LOGIN_URL;

    /**
     * PC Password 설정 Url
     *
     * @author [오지훈]
     */
    @Value("${nike.url.password}")
    private String PASSWORD_URL;

    /**
     * PC Domain
     *
     * @author [오지훈]
     */
    @Value("${nike.url.pc.domain}")
    private String PC_DOMAIN;

    /**
     * MOBILE Domain
     *
     * @author [오지훈]
     */
    @Value("${nike.url.mobile.domain}")
    private String MOBILE_DOMAIN;

    /**
     * MOBILE Password 설정 Url
     *
     * @author [오지훈]
     */

    /**
     * RedisService
     *
     * @author [오지훈]
     */
    private final RedisService redisService;

    /**
     * MailService
     *
     * @author [오지훈]
     */
    private final MailService mailService;

    /**
     * Send mail for create user string.
     *
     * @param user the user
     * @return the string
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:06:21
     * @implNote 계정 생성 안내 메일 발송
     */
    @Transactional
    public String sendMailForCreateUser(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        sendDTO.setLoginUrl(PC_DOMAIN + LOGIN_URL);

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.USER_CREATE.toString()
                , ServiceCode.EmailTypeEnumCode.USER_CREATE.getMessage()
                , sendDTO
        );

        return user.getUserId();
    }

    /**
     * Send mail for set password.
     *
     * @param user the user
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:18:48
     * @implNote 비밀번호 설정 안내 메일
     */
    @Transactional
    public String sendMailForSetPassword(final User user, final String platform) {
        final String keyCode = this.createEncodeCertCode(user.getUserId());
        final String url = (platform.equals("MOBILE") ? MOBILE_DOMAIN : PC_DOMAIN) + PASSWORD_URL;
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        sendDTO.setPasswordUrl(url+"?certCode="+keyCode);

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.PASSWORD_SETTING.toString()
                , ServiceCode.EmailTypeEnumCode.PASSWORD_SETTING.getMessage()
                , sendDTO
        );

        return keyCode;
    }

    /**
     * Send mail for change password.
     *
     * @param user the user
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:18:50
     * @implNote 비밀번호 변경 안내 메일
     */
    @Transactional
    public void sendMailForChangePassword(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        sendDTO.setLoginUrl(PC_DOMAIN + LOGIN_URL);

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.PASSWORD_GUIDE.toString()
                , ServiceCode.EmailTypeEnumCode.PASSWORD_GUIDE.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for auth email.
     *
     * @param user the user
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:18:51
     * @implNote email인증 안내 메일(인증코드)
     */
    @Transactional
    public void sendMailForAuthEmail(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        sendDTO.setCertCode(this.createCertCode(user.getUserId()));

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.CERT_CODE_SEND.toString()
                , ServiceCode.EmailTypeEnumCode.CERT_CODE_SEND.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for change dormant.
     *
     * @param user the user
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:18:53
     * @implNote 휴면계정 전환 사전 안내 메일
     */
    @Transactional
    public void sendMailForChangeDormant(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        //sendDTO.setProcessingDt(this.getCurrentDate());
        sendDTO.setLoginUrl(PC_DOMAIN + LOGIN_URL);

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.DORMANT_PREV.toString()
                , ServiceCode.EmailTypeEnumCode.DORMANT_PREV.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for processing dormant.
     *
     * @param user the user
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:19:34
     * @implNote 휴면계정 처리 안내 메일
     */
    @Transactional
    public void sendMailForProcessingDormant(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        //sendDTO.setProcessingDt(this.getCurrentDate());
        sendDTO.setLoginUrl(PC_DOMAIN + LOGIN_URL);

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.DORMANT_ACTIVE.toString()
                , ServiceCode.EmailTypeEnumCode.DORMANT_ACTIVE.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for release dormant.
     *
     * @param user the user
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:19:34
     * @implNote 휴면계정 해제 안내 메일
     */
    @Transactional
    public void sendMailForReleaseDormant(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        sendDTO.setLoginUrl(PC_DOMAIN + LOGIN_URL);

        mailService.sendMail(
                ServiceCode.EmailTypeEnumCode.DORMANT_CHANGE.toString()
                , ServiceCode.EmailTypeEnumCode.DORMANT_CHANGE.getMessage()
                , sendDTO
        );
    }

    /**
     * Create encode cert code string.
     *
     * @param userId the user id
     * @return the string
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:19:34
     * @implNote 암호화된 인증코드 생성
     */
    public String createEncodeCertCode(final String userId) {
        final String certCode = RandomUtil.randomCertCode2(10);
        redisService.set("cert:"+userId, certCode, 60);
        return CryptoUtil.urlEncode(CryptoUtil.encryptAES256(userId + "NIKESPACE" + certCode, "Nike DnP"));
    }

    /**
     * Create cert code string.
     *
     * @param userId the user id
     * @return the string
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:19:34
     * @implNote 인증코드 생성
     */
    public String createCertCode(final String userId) {
        final String certCode = RandomUtil.randomCertCode2(10);
        redisService.set("cert:"+userId, certCode, 60);
        return certCode;
    }

    /**
     * Gets current date.
     *
     * @return the current date
     * @author [오지훈]
     * @since 2020. 7. 3. 오전 11:19:34
     * @implNote 현재 년,월,일 가져오기
     */
    public String getCurrentDate() {
        final LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() + "년 " + currentDate.getMonthValue() + "월 " + currentDate.getDayOfMonth() + "일";
    }
}
