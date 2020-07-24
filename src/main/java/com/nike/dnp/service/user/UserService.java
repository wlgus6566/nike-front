package com.nike.dnp.service.user;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.SuccessEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.*;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.user.PasswordHistory;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.log.UserLoginLogRepository;
import com.nike.dnp.repository.slang.SlangRepository;
import com.nike.dnp.repository.user.PasswordHistoryRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.EmailPatternUtil;
import com.nike.dnp.util.PasswordPatternUtil;
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

import java.util.ArrayList;
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
     * SlangRepository
     *
     * @author [오지훈]
     */
    private final SlangRepository slangRepository;

    /**
     * The User mail service
     *
     * @author [오지훈]
     */
    private final UserMailService userMailService;

    /**
     * The User login log repository
     *
     * @author [오지훈]
     */
    private final UserLoginLogRepository userLoginLogRepository;

    /**
     * Find pages page.
     *
     * @param userSearchDTO the user search dto
     * @return the page
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description 페이징 조회(paging)
     */
    public Page<UserReturnDTO> findPages(final UserSearchDTO userSearchDTO) {
        log.info("UserService.findPages");
        return userRepository.findPages(
                userSearchDTO,
                PageRequest.of(userSearchDTO.getPage()
                        , userSearchDTO.getSize()
                        , Sort.by(userSearchDTO.getSort()).descending()));
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
                () -> new CodeMessageHandleException(ErrorEnumCode.UserError.NOT_FOUND.toString(), ErrorEnumCode.UserError.NOT_FOUND.getMessage())));
    }

    /**
     * Gets user.
     *
     * @param userSeq the user seq
     * @return the user
     * @author [오지훈]
     * @CreatedOn 2020. 7. 6. 오후 2:38:47
     * @Description 상세 부분 조회
     */
    public UserReturnDTO getUser(final Long userSeq) {
        log.info("UserService.getUser");
        final Optional<User> user = Optional.ofNullable(userRepository.findById(userSeq).orElseThrow(
                () -> new CodeMessageHandleException(ErrorEnumCode.UserError.NOT_FOUND.toString(), ErrorEnumCode.UserError.NOT_FOUND.getMessage())));

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        if (user.isPresent()) {
            final User getUser = user.get();
            userReturnDTO.setUserSeq(getUser.getUserSeq());
            userReturnDTO.setNickname(getUser.getNickname());
            userReturnDTO.setUserId(getUser.getUserId());
            userReturnDTO.setUserStatusCode(getUser.getUserStatusCode());
            userReturnDTO.setAuthName(getUser.getUserAuth().getAuth().getAuthName());
        }

        return userReturnDTO;
    }

    /**
     * Gets my page.
     *
     * @param userSeq the user seq
     * @return the my page
     * @author [오지훈]
     * @CreatedOn 2020. 7. 14. 오후 12:02:26
     * @Description
     */
    public UserReturnDTO getMyPage(final Long userSeq) {
        log.info("UserService.getMyPage");
        final Optional<User> user = Optional.ofNullable(userRepository.findById(userSeq).orElseThrow(
                () -> new CodeMessageHandleException(ErrorEnumCode.UserError.NOT_FOUND.toString(), ErrorEnumCode.UserError.NOT_FOUND.getMessage())));

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        if (user.isPresent()) {
            final User getUser = user.get();
            userReturnDTO.setUserSeq(getUser.getUserSeq());
            userReturnDTO.setNickname(getUser.getNickname());
            userReturnDTO.setUserId(getUser.getUserId());
            userReturnDTO.setUserStatusCode(getUser.getUserStatusCode());
            userReturnDTO.setAuthName(getUser.getUserAuth().getAuth().getAuthName());
            userReturnDTO.setLoginLogs(userLoginLogRepository.findTop5ByUserSeqOrderByRegistrationDtDesc(userSeq));
        }

        return userReturnDTO;
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
                () -> new CodeMessageHandleException(ErrorEnumCode.UserError.NOT_FOUND.toString(), ErrorEnumCode.UserError.NOT_FOUND.getMessage()));
    }

    /**
     * Find by user id return optional optional.
     *
     * @param userId the user id
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오전 11:27:53
     * @Description 상세 조회
     */
    public Optional<User> findByUserIdReturnOptional(final String userId) {
        log.info("UserService.findByUserId");
        return userRepository.findByUserId(userId);
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
                () -> new CodeMessageHandleException(ErrorEnumCode.UserError.NOT_FOUND.toString(), ErrorEnumCode.DataError.NOT_FOUND.getMessage())));
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
        log.info("UserService.countByUserId");
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
    public UserReturnDTO save(final UserSaveDTO userSaveDTO) {
        log.info("UserService.save");
        this.checkId(userSaveDTO.getUserId());
        final User user = userRepository.save(new User().save(userSaveDTO));
        final Auth auth = authRepository.findById(userSaveDTO.getAuthSeq()).orElseThrow(
                () -> new CodeMessageHandleException(ErrorEnumCode.UserError.NOT_FOUND.toString(), ErrorEnumCode.DataError.NOT_FOUND.getMessage()));

        if(user.getUserSeq() > 0) {
            userAuthRepository.save(new UserAuth().save(user, auth));
        }

        // [계정생성안내] 메일 발송
        userMailService.sendMailForCreateUser(user);

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        userReturnDTO.setUserSeq(user.getUserSeq());
        return userReturnDTO;
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
    public UserReturnDTO update(
            final Long userSeq
            , final UserUpdateDTO userUpdateDTO
    ) {
        log.info("UserService.update");
        final Optional<User> user = this.findById(userSeq);
        user.ifPresent(value -> value.update(userUpdateDTO));

        final Optional<UserAuth> userAuth = this.findByUser(user.get());
        userAuth.ifPresent(value -> value.update(userUpdateDTO));

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        userReturnDTO.setUserSeq(user.get().getUserSeq());
        userReturnDTO.setUserId(user.get().getUserId());
        userReturnDTO.setAuthName(userAuth.get().getAuth().getAuthName());
        return userReturnDTO;
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
    public UserReturnDTO updateStatus(
            final Long userSeq
            , final UserUpdateStatusDTO userUpdateStatusDTO
    ) {
        log.info("UserService.updateStatus");
        final Optional<User> user = this.findById(userSeq);
        user.ifPresent(value -> value.updateStatus(userUpdateStatusDTO.getUserStatusCode()));

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        userReturnDTO.setUserSeq(user.get().getUserSeq());
        return userReturnDTO;
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
    public UserReturnDTO deleteOne(final Long userSeq) {
        log.info("UserService.deleteOne");
        final Optional<User> user = this.findById(userSeq);
        user.ifPresent(value -> value.delete(userSeq));

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        userReturnDTO.setUserSeq(user.get().getUserSeq());
        return userReturnDTO;
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
    public List<Long> deleteArray(final UserDeleteDTO userDeleteDTO) {
        log.info("UserService.deleteArray");
        final List<User> users = userRepository.findAllByUserSeqIn(userDeleteDTO.getUserSeqArray());
        final List<Long> list = new ArrayList<>();
        for (final User user : users) {
            user.delete(user.getUserSeq());
            list.add(user.getUserSeq());
        }

        return list;
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
    public UserDetails loadUserByUsername(final String userId) {
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
        String code = ErrorEnumCode.UserError.NOT_VALID_EMAIL.toString();
        String msg = ErrorEnumCode.UserError.NOT_VALID_EMAIL.getMessage();
        result.setData(0);

        if (EmailPatternUtil.isValidEmail(userId)) {
            final int count = this.countByUserId(userId);
            code = count > 0 ? ErrorEnumCode.UserError.USE_ID.toString() : SuccessEnumCode.UserSuccess.NOT_DUPLICATE.toString();
            msg = count > 0 ? ErrorEnumCode.UserError.USE_ID.getMessage() : SuccessEnumCode.UserSuccess.NOT_DUPLICATE.getMessage();
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
    public UserReturnDTO confirmPassword(final UserCertDTO userCertDTO) {
        log.info("UserService.confirmPassword1");
        final String decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(userCertDTO.getCertCode()), "Nike DnP");
        final String userId = decodeCertCode.split("\\|")[0];
        final String certKey = decodeCertCode.split("\\|")[1];

        System.out.println("======================================================");
        System.out.println("userCertDTO.getCertCode() = " + userCertDTO.getCertCode());
        System.out.println("decodeCertCode = " + decodeCertCode);
        System.out.println("userId = " + userId);
        System.out.println("certKey = " + certKey);


        final String certCode = StringUtils.defaultString((String) redisService.get("cert:" + userId));

        System.out.println("certCode = " + certCode);
        System.out.println("======================================================");

        final String password = userCertDTO.getPassword();
        final String newPassword = userCertDTO.getNewPassword();
        final String confirmPassword = ObjectUtils.isEmpty(userCertDTO.getConfirmPassword()) ? "" : userCertDTO.getConfirmPassword();
        final String certPassword = ObjectUtils.isEmpty(newPassword) ? "" : passwordEncoder.encode(newPassword);
        final User user = this.findByUserId(userId);
        this.checkCertCode(certCode, certKey);
        this.checkPassword(
                user.getUserSeq()
                , user.getUserId()
                , user.getPassword()
                , password
                , newPassword
                , confirmPassword);

        //비밀번호 업데이트
        user.updatePassword(certPassword);
        passwordHistoryRepository.save(
                PasswordHistory.builder()
                        .userSeq(user.getUserSeq())
                        .password(certPassword)
                        .build());

        //인증코드 삭제
        redisService.delete("cert:" + userId);

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        userReturnDTO.setUserSeq(user.getUserSeq());
        return userReturnDTO;
    }

    /**
     * Confirm password boolean.
     *
     * @param userId      the user id
     * @param userCertDTO the user cert dto
     * @return the boolean
     * @author [오지훈]
     * @CreatedOn 2020. 7. 6. 오후 3:32:25
     * @Description
     */
    @Transactional
    public UserReturnDTO confirmPassword(final String userId, final UserCertDTO userCertDTO) {
        log.info("UserService.confirmPassword2");
        final String password = userCertDTO.getPassword();
        final String newPassword = userCertDTO.getNewPassword();
        final String confirmPassword = ObjectUtils.isEmpty(userCertDTO.getConfirmPassword()) ? "" : userCertDTO.getConfirmPassword();
        final String certPassword = ObjectUtils.isEmpty(newPassword) ? "" : passwordEncoder.encode(newPassword);
        final User user = this.findByUserId(userId);
        this.checkPassword(
                user.getUserSeq()
                , userId
                , user.getPassword()
                , password
                , newPassword
                , confirmPassword);

        //비밀번호 업데이트
        user.updatePassword(certPassword);
        passwordHistoryRepository.save(PasswordHistory.builder().userSeq(user.getUserSeq()).password(certPassword).build());

        final UserReturnDTO userReturnDTO = new UserReturnDTO();
        userReturnDTO.setUserSeq(user.getUserSeq());
        return userReturnDTO;
    }

    /**
     * Check password.
     *
     * @param userSeq         the user seq
     * @param userId          the user id
     * @param userPassword    the user password
     * @param password        the password
     * @param newPassword     the new password
     * @param confirmPassword the confirm password
     * @author [오지훈]
     * @CreatedOn 2020. 7. 6. 오후 3:32:17
     * @Description
     */
    public void checkPassword(
            final Long userSeq
            , final String userId
            , final String userPassword
            , final String password
            , final String newPassword
            , final String confirmPassword
    ) {
        log.info("UserService.checkPassword");
        //기존비밀번호확인
        if (!ObjectUtils.isEmpty(password)) {
            if (!passwordEncoder.matches(password, userPassword)) {
                throw new CodeMessageHandleException(
                        ErrorEnumCode.LoginError.WRONG_PASSWORD.toString()
                        , ErrorEnumCode.LoginError.WRONG_PASSWORD.getMessage());
            }
        }

        //비밀번호 미입력 시
        if (ObjectUtils.isEmpty(newPassword)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NULL_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.NULL_PASSWORD.getMessage());
        }

        //입력한 새로운 비밀번호와 확인 비밀번호 비교
        if (!newPassword.equals(confirmPassword)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NOT_MATCH_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.NOT_MATCH_PASSWORD.getMessage());
        }

        //아이디와 비밀번호 비교
        if (PasswordPatternUtil.sameId(newPassword, userId)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.DUPLICATE_ID_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.DUPLICATE_ID_PASSWORD.getMessage());
        }

        //비밀번호 정규식 체크
        if (PasswordPatternUtil.invalidPassword(newPassword)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.INVALID_PASSWORD.toString()
                    , ErrorEnumCode.LoginError.INVALID_PASSWORD.getMessage());
        }

        //사용되었던 비밀번호 비교 (최근 6개)
        final List<PasswordHistory> histories = passwordHistoryRepository.findTop6ByUserSeqOrderByRegistrationDtDesc(userSeq);
        for (final PasswordHistory history : histories) {
            if (passwordEncoder.matches(newPassword, history.getPassword())) {
                throw new CodeMessageHandleException(
                        ErrorEnumCode.LoginError.USED_PASSWORD.toString()
                        , ErrorEnumCode.LoginError.USED_PASSWORD.getMessage());
            }
        }

        //공통사전 비교
        if (slangRepository.countBySlangContains(newPassword) > 0) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.IS_SLANG.toString()
                    , ErrorEnumCode.LoginError.IS_SLANG.getMessage());
        }
    }

    /**
     * Check cert code.
     *
     * @param certCode the cert code
     * @param certKey  the cert key
     * @author [오지훈]
     * @CreatedOn 2020. 7. 6. 오후 3:32:21
     * @Description
     */
    public void checkCertCode (final String certCode, final String certKey) {
        log.info("UserService.checkCertCode");
        //인증코드가 존재하는지 확인
        if(certCode.isEmpty()) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.EXPIRED_PERIOD.toString()
                    , ErrorEnumCode.LoginError.EXPIRED_PERIOD.getMessage());
        }

        //인증코드가 맞는지
        if(!certKey.equals(certCode)) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.LoginError.NOT_MATCH_CERT_CODE.toString()
                    , ErrorEnumCode.LoginError.NOT_MATCH_CERT_CODE.getMessage());
        }
    }
}
