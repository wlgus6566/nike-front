package com.nike.dnp.service.user;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ErrorEnumCode.DataError;
import com.nike.dnp.common.variable.ErrorEnumCode.UserError;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.common.variable.SuccessEnumCode.UserSuccess;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.user.*;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.user.PasswordHistory;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.user.PasswordHistoryRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.EmailPatternUtil;
import com.nike.dnp.util.PasswordPatternUtil;
import com.nike.dnp.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;


/**
 * UserService
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 2:40:43
 * @Description User(유저) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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
     * UserRepository
     *
     * @author [오지훈]
     */
    private final UserRepository userRepository;

    /**
     * UserRepository
     *
     * @author [오지훈]
     */
    private final AuthRepository authRepository;

    /**
     * UserAuthRepository
     *
     * @author [오지훈]
     */
    private final UserAuthRepository userAuthRepository;

    /**
     * PasswordHistoryRepository
     *
     * @author [오지훈]
     */
    private final PasswordHistoryRepository passwordHistoryRepository;

    /**
     * PasswordEncoder
     *
     * @author [오지훈]
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Find pages page.
     *
     * @param userSearchDTO the user search dto
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description 페이징 조회(paging)
     */
    public Page<User> findPages(final UserSearchDTO userSearchDTO) {
        log.info("UserService.findPages");
        return userRepository.findPages(
                userSearchDTO,
                PageRequest.of(userSearchDTO.getPage()
                        , userSearchDTO.getSize()
                        , Sort.by("userSeq").descending()));
    }

    /**
     * Find by id optional.
     *
     * @param userSeq the user seq
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 25. 오후 6:32:35
     * @Description 상세 조회
     */
    public Optional<User> findById(final Long userSeq) {
        log.info("UserService.findById");
        return Optional.ofNullable(userRepository.findById(userSeq).orElseThrow(
                () -> new CodeMessageHandleException(UserError.NOT_FOUND.toString(), UserError.NOT_FOUND.getMessage())));
    }

    /**
     * Find by user id user.
     *
     * @param userId 유저 ID
     * @return the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description 상세 조회
     */
    public User findByUserId(final String userId) {
        log.info("UserService.findByUserId");
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new CodeMessageHandleException(UserError.NOT_FOUND.toString(), UserError.NOT_FOUND.getMessage()));
    }

    /**
     * Find by user optional.
     *
     * @param user the user
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 30. 오후 4:46:46
     * @Description 유저 권한 조회
     */
    public Optional<UserAuth> findByUser(final User user) {
        return Optional.ofNullable(userAuthRepository.findByUser(user).orElseThrow(
                () -> new CodeMessageHandleException(DataError.NOT_FOUND.toString(), DataError.NOT_FOUND.getMessage())));
    }

    /**
     * Count by user id int.
     *
     * @param userId the user id
     * @return the int
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:15:45
     * @Description 유저 아이디 카운트
     */
    public int countByUserId(final String userId) {
        return userRepository.countByUserId(userId);
    }

    /**
     * Save user.
     *
     * @param userSaveDTO the user save dto
     * @return the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description 등록
     */
    @Transactional
    public UserAuth save(final UserSaveDTO userSaveDTO) {
        log.info("UserService.save");
        this.checkId(userSaveDTO.getUserId());

        final User user = userRepository.save(new User().save(userSaveDTO));
        final Auth auth = authRepository.findById(userSaveDTO.getAuthSeq()).orElseThrow(
                () -> new CodeMessageHandleException(DataError.NOT_FOUND.toString(), DataError.NOT_FOUND.getMessage()));

        final UserAuth userAuth = userAuthRepository.save(new UserAuth().save(user, auth));
        if (userAuth.getUserAuthSeq() > 0) {
            this.sendCreateUserEmail(user);
        }
        return userAuth;
    }

    /**
     * Update optional.
     *
     * @param userSeq       the user seq
     * @param userUpdateDTO the user update dto
     * @return user user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description 닉네임 /권한 수정
     */
    @Transactional
    public Optional<UserAuth> update(
            final Long userSeq
            , final UserUpdateDTO userUpdateDTO
    ) {
        log.info("UserService.update");
        final Optional<User> user = this.findById(userSeq);
        user.ifPresent(value -> value.update(userUpdateDTO));

        final Optional<UserAuth> userAuth = this.findByUser(user.get());
        userAuth.ifPresent(value -> value.update(userUpdateDTO));

        return userAuth;
    }

    /**
     * Update status optional.
     *
     * @param userSeq             the user seq
     * @param userUpdateStatusDTO the user update status dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description 상태값 변경
     */
    @Transactional
    public Optional<User> updateStatus(
            final Long userSeq
            , final UserUpdateStatusDTO userUpdateStatusDTO
    ) {
        log.info("UserService.updateStatus");
        final Optional<User> user = this.findById(userSeq);
        user.ifPresent(value -> value.updateStatus(userUpdateStatusDTO.getUserStatusCode()));
        return user;
    }

    /**
     * Delete one optional.
     *
     * @param userSeq the user seq
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:47:29
     * @Description 유저 단건 삭제
     */
    @Transactional
    public Optional<User> deleteOne( final Long userSeq) {
        log.info("UserService.deleteOne");
        final Optional<User> user = this.findById(userSeq);
        user.ifPresent(value -> value.delete(userSeq));
        return user;
    }

    /**
     * Delete array list.
     *
     * @param userDeleteDTO the user delete dto
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 6:15:52
     * @Description 유저 배열 삭제
     */
    @Transactional
    public List<User> deleteArray(final UserDeleteDTO userDeleteDTO) {
        log.info("UserService.deleteArray");
        List<User> users = userRepository.findAllByUserSeqIn(userDeleteDTO.getUserSeqArray());

        for (User user : users) {
            user.delete(user.getUserSeq());
        }
        return users;
    }

    /**
     * Update login dt.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description 최종 로그인 일자 업데이트
     */
    @Transactional
    public void updateLoginDt(User user) {
        log.info("UserService.updateLoginDt");
        user.updateLoginDt();
    }

    /**
     * Load user by username user details.
     *
     * @param userId 유저 ID
     * @return the authUserDTO
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description 로그인 검증
     */
    @Override
    public UserDetails loadUserByUsername(String userId) {
        log.info("UserService.loadUserByUsername");
        return new AuthUserDTO(this.findByUserId(userId));
    }

    /**
     * Check id single result.
     *
     * @param userId the user id
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 1. 오후 2:52:56
     * @Description ID 중복 체크
     */
    public SingleResult<Integer> checkId(final String userId) {
        final SingleResult<Integer> result = new SingleResult<>();
        String code = UserError.NOT_VALID_EMAIL.toString();
        String msg = UserError.NOT_VALID_EMAIL.getMessage();
        result.setData(0);

        if (EmailPatternUtil.isValidEmail(userId)) {
            final int count = this.countByUserId(userId);
            code = count > 0 ? UserError.USE_ID.toString() : UserSuccess.NOT_DUPLICATE.toString();
            msg = count > 0 ? UserError.USE_ID.getMessage() : UserSuccess.NOT_DUPLICATE.getMessage();
            result.setData(count);
        }
        return new SingleResult<>(code, msg, true, true);
    }

    /**
     * Check boolean.
     *
     * @param userCertDTO the user cert dto
     * @return the boolean
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:18:42
     * @Description 인증코드 검증 및 비밀번호 변경
     */
    @Transactional
    public Boolean checkCertCode(final UserCertDTO userCertDTO) {
        log.info("UserService.checkCertCode");
        String decodeCertCode = "";
        try {
            decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(userCertDTO.getCertCode()), "Nike DnP");
        } catch (Exception exception) {
            log.error("exception", exception);
        }
        final String userId = decodeCertCode.split("\\|")[0];
        final String certCode = StringUtils.defaultString((String) redisService.get("cert:" + userId));
        final String newPassword = userCertDTO.getNewPassword();
        final String confirmPassword = ObjectUtils.isEmpty(userCertDTO.getConfirmPassword()) ? "" : userCertDTO.getConfirmPassword();
        final String certPassword = ObjectUtils.isEmpty(newPassword) ? "" : passwordEncoder.encode(newPassword);

        //TODO[ojh] 2020-07-01 : 인증코드가 존재하는지 확인
        if(certCode.isEmpty()) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.EXPIRED_PERIOD.toString()
                    , ErrorEnumCode.LoginError.EXPIRED_PERIOD.getMessage());
        }

        //TODO[ojh] 2020-07-01 : 유저가 존재하는지 확인
        User user = this.findByUserId(userId);


        //TODO[ojh] 2020-07-01 : 비밀번호 미입력 시
        if (ObjectUtils.isEmpty(newPassword)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NULL_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.NULL_PASSWORD.getMessage());
        }

        //TODO[ojh] 2020-07-01 : 입력한 새로운 비밀번호와 확인 비밀번호 비교
        if (!newPassword.equals(confirmPassword)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NOT_MATCH_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.NOT_MATCH_PASSWORD.getMessage());
        }

        //TODO[ojh] 2020-07-01 : 아이디와 비밀번호 비교
        if (PasswordPatternUtil.sameId(newPassword, userId)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.DUPLICATE_ID_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.DUPLICATE_ID_PASSWORD.getMessage());
        }

        //TODO[ojh] 2020-07-01 : 비밀번호 정규식 체크
        if (PasswordPatternUtil.invalidPassword(newPassword)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.INVALID_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.INVALID_PASSWORD.getMessage());
        }

        //TODO[ojh] 2020-07-01 : 사용되었던 비밀번호 비교 (최근 6개)
        List<PasswordHistory> histories = passwordHistoryRepository.findTop6ByUserSeqOrderByRegistrationDtDesc(user.getUserSeq());
        for (PasswordHistory history : histories) {
            if (passwordEncoder.matches(newPassword, history.getPassword())) {
                throw new CodeMessageHandleException(
                        ErrorEnumCode.LoginError.USED_PASSWORD.toString()
                        , ErrorEnumCode.LoginError.USED_PASSWORD.getMessage());
            }
        }

        //TODO[ojh] 2020-07-01 : 공통사전 비교


        //TODO[ojh] 2020-07-01 : 비밀번호 업데이트
        user.updatePassword(certPassword);
        passwordHistoryRepository.save(PasswordHistory.builder().userSeq(user.getUserSeq()).password(certPassword).build());
        return true;
    }

    /**
     * Send email.
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 3:27:51
     * @Description [계정 생성 안내] 알림메일 발송
     */
    public void sendCreateUserEmail(final User user) {
        log.info("UserService.sendCreateUserEmail");

        try {
            //TODO[ojh] 인증코드 생성
            final String certCode = RandomUtil.randomCertCode2(10);

            //TODO[ojh] ID+인증코드 암호화
            final String encodeCertCode = CryptoUtil.urlEncode(CryptoUtil.encryptAES256(user.getUserId() + "|" + certCode, "Nike DnP"));
            log.info("encodeCertCode > " + encodeCertCode);

            //TODO[ojh] REDIS 값 셋팅
            redisService.set("cert:"+user.getUserId(), certCode, 60);

            //TODO[ojh] 대체값 변경
            SendDTO sendDTO = new SendDTO();
            sendDTO.setNickname(user.getNickname());
            sendDTO.setEmail(user.getUserId());
            sendDTO.setPasswordUrl("http://nikednp.co.kr?certCode="+encodeCertCode);

            //TODO[ojh] [계정 생성 안내] 이메일 발송
            mailService.sendMail(
                    ServiceEnumCode.EmailTypeEnumCode.USER_CREATE.toString()
                    , ServiceEnumCode.EmailTypeEnumCode.USER_CREATE.getMessage()
                    ,sendDTO
            );

        } catch (Exception exception) {
            log.error("exception", exception);
        }
    }
}
