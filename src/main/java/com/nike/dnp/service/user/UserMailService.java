package com.nike.dnp.service.user;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


/**
 * UserMailService
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 2:40:43
 * @Description User(유저) Mail Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMailService {

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
     * @CreatedOn 2020. 7. 3. 오전 11:06:21
     * @Description 계정 생성 안내 메일 발송
     */
    public String sendMailForCreateUser(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());

        //TODO[ojh] 2020-07-03 : 변경예정
        sendDTO.setLoginUrl("loginUrl");

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.USER_CREATE.toString()
                , ServiceEnumCode.EmailTypeEnumCode.USER_CREATE.getMessage()
                , sendDTO
        );

        return user.getUserId();
    }

    /**
     * Send mail for set password.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:18:48
     * @Description 비밀번호 설정 안내 메일
     */
    public void sendMailForSetPassword(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());

        //TODO[ojh] 2020-07-02 : 변경예정
        sendDTO.setPasswordUrl("setPasswordUrl="+this.createEncodeCertCode(user.getUserId()));

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.PASSWORD_SETTING.toString()
                , ServiceEnumCode.EmailTypeEnumCode.PASSWORD_SETTING.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for change password.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:18:50
     * @Description 비밀번호 변경 안내 메일
     */
    public void sendMailForChangePassword(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());

        //TODO[ojh] 2020-07-02 : 변경예정
        sendDTO.setLoginUrl("loginUrl");

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.PASSWORD_GUIDE.toString()
                , ServiceEnumCode.EmailTypeEnumCode.PASSWORD_GUIDE.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for auth email.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:18:51
     * @Description email인증 안내 메일(인증코드)
     */
    public void sendMailForAuthEmail(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        sendDTO.setCertCode(this.createCertCode(user.getUserId()));

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.CERT_CODE_SEND.toString()
                , ServiceEnumCode.EmailTypeEnumCode.CERT_CODE_SEND.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for change dormant.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:18:53
     * @Description 휴면계정 전환 사전 안내 메일
     */
    public void sendMailForChangeDormant(final User user) {

        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        //sendDTO.setProcessingDt(this.getCurrentDate());

        //TODO[ojh] 2020-07-02 : 변경예정
        sendDTO.setLoginUrl("loginUrl");

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.DORMANT_PREV.toString()
                , ServiceEnumCode.EmailTypeEnumCode.DORMANT_PREV.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for processing dormant.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:19:34
     * @Description 휴면계정 처리 안내 메일
     */
    public void sendMailForProcessingDormant(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());
        //sendDTO.setProcessingDt(this.getCurrentDate());

        //TODO[ojh] 2020-07-02 : 변경예정
        sendDTO.setLoginUrl("loginUrl");

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.DORMANT_ACTIVE.toString()
                , ServiceEnumCode.EmailTypeEnumCode.DORMANT_ACTIVE.getMessage()
                , sendDTO
        );
    }

    /**
     * Send mail for release dormant.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:19:34
     * @Description 휴면계정 해제 안내 메일
     */
    public void sendMailForReleaseDormant(final User user) {
        final SendDTO sendDTO = new SendDTO();
        sendDTO.setNickname(user.getNickname());
        sendDTO.setEmail(user.getUserId());

        //TODO[ojh] 2020-07-02 : 변경예정
        sendDTO.setLoginUrl("loginUrl");

        mailService.sendMail(
                ServiceEnumCode.EmailTypeEnumCode.DORMANT_CHANGE.toString()
                , ServiceEnumCode.EmailTypeEnumCode.DORMANT_CHANGE.getMessage()
                , sendDTO
        );
    }

    /**
     * Create encode cert code string.
     *
     * @param userId the user id
     * @return the string
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:19:34
     * @Description 암호화된 인증코드 생성
     */
    public String createEncodeCertCode(final String userId) {
        final String certCode = RandomUtil.randomCertCode2(10);
        redisService.set("cert:"+userId, certCode, 60);
        return CryptoUtil.urlEncode(CryptoUtil.encryptAES256(userId + "|" + certCode, "Nike DnP"));
    }

    /**
     * Create cert code string.
     *
     * @param userId the user id
     * @return the string
     * @author [오지훈]
     * @CreatedOn 2020. 7. 3. 오전 11:19:34
     * @Description 인증코드 생성
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
     * @CreatedOn 2020. 7. 3. 오전 11:19:34
     * @Description 현재 년,월,일 가져오기
     */
    public String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() + "년 " + currentDate.getMonthValue() + "월 " + currentDate.getDayOfMonth() + "일";
    }
}
