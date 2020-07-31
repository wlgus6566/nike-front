package com.nike.dnp.service.user;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.*;
import com.nike.dnp.entity.user.PasswordHistory;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.log.UserLoginLogRepository;
import com.nike.dnp.repository.slang.SlangRepository;
import com.nike.dnp.repository.user.PasswordHistoryRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.EmailPatternUtil;
import com.nike.dnp.util.MessageUtil;
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
 * @since 2020. 6. 22. 오후 2:40:43
 * @implNote User(유저) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * The constant REGEX
     *
     * @author [오지훈]
     */
    private final static String REGEX = "\\|";

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
     * AuthService
     *
     * @author [오지훈]
     */
    private final AuthService authService;

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
     * @since 2020. 6. 22. 오후 2:40:43
     * @implNote 페이징 조회(paging)
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
     * @since 2020. 6. 25. 오후 6:32:35
     * @implNote 상세 조회
     */
    public Optional<User> findById(final Long userSeq) {
        log.info("UserService.findById");
        return Optional.ofNullable(userRepository.findById(userSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
    }

    /**
     * Gets user.
     *
     * @param userSeq the user seq
     * @return the user
     * @author [오지훈]
     * @since 2020. 7. 6. 오후 2:38:47
     * @implNote 상세 부분 조회
     */
    public UserReturnDTO getUser(final Long userSeq) {
        log.info("UserService.getUser");
        final Optional<User> user = Optional.ofNullable(userRepository.findById(userSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));

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
     * @since 2020. 7. 14. 오후 12:02:26
     * @implNote
     */
    public UserReturnDTO getMyPage(final Long userSeq) {
        log.info("UserService.getMyPage");
        final Optional<User> user = Optional.ofNullable(userRepository.findById(userSeq).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));

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
     * @since 2020. 6. 22. 오후 2:40:43
     * @implNote 상세 조회
     */
    public User findByUserId(final String userId) {
        log.info("UserService.findByUserId");
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())));
    }

    /**
     * Find by user id return optional optional.
     *
     * @param userId the user id
     * @return the optional
     * @author [오지훈]
     * @since 2020. 7. 2. 오전 11:27:53
     * @implNote 상세 조회
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
     * @since 2020. 6. 30. 오후 4:46:46
     * @implNote 유저 권한 조회
     */
    public Optional<UserAuth> findByUser(final User user) {
        return Optional.ofNullable(userAuthRepository.findByUser(user).orElseThrow(
                () -> new CodeMessageHandleException(FailCode.ExceptionError.NOT_FOUND.name(), MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()))));
    }

    /**
     * Count by user id int.
     *
     * @param userId the user id
     * @return the int
     * @author [오지훈]
     * @since 2020. 7. 1. 오후 2:15:45
     * @implNote 유저 아이디 카운트
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
     * @since 2020. 6. 22. 오후 2:40:44
     * @implNote 등록
     */
    @Transactional
    public UserReturnDTO save(final UserSaveDTO userSaveDTO) {
        log.info("UserService.save");
        this.checkId(userSaveDTO.getUserId());
        final User user = userRepository.save(new User().save(userSaveDTO));
        if(user.getUserSeq() > 0) {
            //userAuthRepository.save(new UserAuth().save(user, auth));
            userAuthRepository.save(UserAuth
                    .builder()
                    .user(user)
                    .auth(authService.getById(userSaveDTO.getAuthSeq()))
                    .build());
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
     * @since 2020. 6. 22. 오후 2:40:44
     * @implNote 닉네임 /권한 수정
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
     * @since 2020. 6. 22. 오후 2:40:44
     * @implNote 상태값 변경
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
     * @since 2020. 6. 23. 오후 5:47:29
     * @implNote 유저 단건 삭제
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
     * @since 2020. 6. 23. 오후 6:15:52
     * @implNote 유저 배열 삭제
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
     * @since 2020. 6. 22. 오후 2:40:44
     * @implNote 로그인 검증
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
     * @return the integer
     * @author [오지훈]
     * @implNote ID 중복 체크
     * @since 2020. 7. 1. 오후 2:52:56
     */
    public Integer checkId(final String userId) {
        if (EmailPatternUtil.isValidEmail(userId)) {
            if (this.countByUserId(userId) > 0) {
                throw new CodeMessageHandleException(
                        FailCode.ConfigureError.USED_ID.name()
                        , MessageUtil.getMessage(FailCode.ConfigureError.USED_ID.name())
                );
            }

            return 0;
        } else {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.NOT_VALID_EMAIL.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.NOT_VALID_EMAIL.name())
            );
        }
    }

    /**
     * Check cert code boolean.
     *
     * @param userCertDTO the user cert dto
     * @return the boolean
     * @author [오지훈]
     * @since 2020. 7. 27. 오후 4:12:37
     * @implNote
     */
    public Boolean checkCertCode(final UserCertDTO userCertDTO) {
        log.info("UserService.checkCertCode");
        final String decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(userCertDTO.getCertCode()), "Nike DnP");
        if (FailCode.ExceptionError.ERROR.name().equals(decodeCertCode)) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.EXPIRED_CERT_CODE.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.EXPIRED_CERT_CODE.name())
            );
        }
        final String userId = decodeCertCode.split(REGEX)[0];
        final String certKey = decodeCertCode.split(REGEX)[1];
        final String certCode = StringUtils.defaultString((String) redisService.get("cert:" + userId));
        return this.checkCertCode(certCode, certKey);
    }

    /**
     * Check boolean.
     *
     * @param userCertDTO the user cert dto
     * @return the boolean
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 4:18:42
     * @implNote 인증코드 검증 및 비밀번호 변경
     */
    @Transactional
    public UserReturnDTO confirmPassword(final UserCertDTO userCertDTO) {
        log.info("UserService.confirmPassword1");
        final String decodeCertCode = CryptoUtil.decryptAES256(CryptoUtil.urlDecode(userCertDTO.getCertCode()), "Nike DnP");
        if (FailCode.ExceptionError.ERROR.name().equals(decodeCertCode)) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.EXPIRED_CERT_CODE.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.EXPIRED_CERT_CODE.name())
            );
        }
        final String userId = decodeCertCode.split(REGEX)[0];
        final String certKey = decodeCertCode.split(REGEX)[1];
        final String certCode = StringUtils.defaultString((String) redisService.get("cert:" + userId));
        final String password = userCertDTO.getPassword();
        final String newPassword = userCertDTO.getNewPassword();
        final String confirmPassword = ObjectUtils.isEmpty(userCertDTO.getConfirmPassword()) ? "" : userCertDTO.getConfirmPassword();
        final String certPassword = ObjectUtils.isEmpty(newPassword) ? "" : passwordEncoder.encode(newPassword);
        final User user = this.findByUserId(userId);
        this.checkCertCode(certCode, certKey);
        this.checkPassword(
                UserPasswordDTO.builder()
                        .userSeq(user.getUserSeq())
                        .userId(user.getUserId())
                        .userPassword(user.getPassword())
                        .password(password)
                        .newPassword(newPassword)
                        .confirmPassword(confirmPassword)
                        .build());

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
     * @since 2020. 7. 6. 오후 3:32:25
     * @implNote
     */
    @Transactional
    public UserReturnDTO confirmPassword(final String userId, final UserCertDTO userCertDTO) {
        log.info("UserService.confirmPassword2");
        final String password = userCertDTO.getPassword();
        final String newPassword = userCertDTO.getNewPassword();
        final String confirmPassword = ObjectUtils.isEmpty(userCertDTO.getConfirmPassword()) ? "" : userCertDTO.getConfirmPassword();
        final String certPassword = ObjectUtils.isEmpty(newPassword) ? "" : passwordEncoder.encode(newPassword);
        final User user = this.findByUserId(userId);
        this.checkPassword(UserPasswordDTO.builder()
                .userSeq(user.getUserSeq())
                .userId(userId)
                .userPassword(user.getPassword())
                .password(password)
                .newPassword(newPassword)
                .confirmPassword(confirmPassword)
                .build());

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
     * @param userPasswordDTO the user password dto
     * @author [오지훈]
     * @implNote 설명]
     * @since 2020. 7. 6. 오후 3:32:17
     */
    public void checkPassword(final UserPasswordDTO userPasswordDTO) {
        log.info("UserService.checkPassword");

        //기존비밀번호확인
        if (!ObjectUtils.isEmpty(userPasswordDTO.getPassword()) && !passwordEncoder.matches(userPasswordDTO.getPassword(), userPasswordDTO.getUserPassword())) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.CHECK_ID_PASSWORD.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.CHECK_ID_PASSWORD.name()));
        }

        //비밀번호 미입력 시
        if (ObjectUtils.isEmpty(userPasswordDTO.getNewPassword())) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.NULL_PASSWORD.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.NULL_PASSWORD.name()));
        }

        //입력한 새로운 비밀번호와 확인 비밀번호 비교
        if (!userPasswordDTO.getNewPassword().equals(userPasswordDTO.getConfirmPassword())) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.NOT_MATCH_PASSWORD.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.NOT_MATCH_PASSWORD.name()));
        }

        //아이디와 비밀번호 비교
        if (PasswordPatternUtil.sameId(userPasswordDTO.getNewPassword(), userPasswordDTO.getUserId())) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.DUPLICATE_ID_PASSWORD.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.DUPLICATE_ID_PASSWORD.name()));
        }

        //비밀번호 정규식 체크
        if (PasswordPatternUtil.invalidPassword(userPasswordDTO.getNewPassword())) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.INVALID_PASSWORD.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.INVALID_PASSWORD.name()));
        }

        //사용되었던 비밀번호 비교 (최근 6개)
        final List<PasswordHistory> histories = passwordHistoryRepository.findTop6ByUserSeqOrderByRegistrationDtDesc(userPasswordDTO.getUserSeq());
        for (final PasswordHistory history : histories) {
            if (passwordEncoder.matches(userPasswordDTO.getNewPassword(), history.getPassword())) {
                throw new CodeMessageHandleException(
                        FailCode.ConfigureError.USED_PASSWORD.name()
                        , MessageUtil.getMessage(FailCode.ConfigureError.USED_PASSWORD.name()));
            }
        }

        //공통사전 비교
        if (slangRepository.countBySlangContains(userPasswordDTO.getNewPassword()) > 0) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.IS_SLANG.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.IS_SLANG.name()));
        }
    }

    /**
     * Check cert code.
     *
     * @param certCode the cert code
     * @param certKey  the cert key
     * @author [오지훈]
     * @since 2020. 7. 6. 오후 3:32:21
     * @implNote
     */
    public Boolean checkCertCode (final String certCode, final String certKey) {
        log.info("UserService.checkCertCode");
        //인증코드가 존재하는지 확인
        if(certCode.isEmpty()) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.EXPIRED_PERIOD.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.EXPIRED_PERIOD.name()));
        }

        //인증코드가 맞는지
        if(!certKey.equals(certCode)) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.NOT_MATCH_CERT_CODE.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.NOT_MATCH_CERT_CODE.name()));
        }
        return true;
    }
}
