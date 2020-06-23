package com.nike.dnp.service.user;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.common.variable.ErrorEnumCode.LoginError;
import com.nike.dnp.common.variable.ErrorEnumCode.UserError;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.*;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.repository.user.UserRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.CryptoUtil;
import com.nike.dnp.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * The Redis service
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
     * 전체 조회
     *
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description
     */
    public List<User> findAll() {
        log.info("UserService.findAll");
        return userRepository.findAll();
    }

    /**
     * 페이징 조회(paging)
     *
     * @param userSearchDTO the user search dto
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description
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
     * 상세 조회
     *
     * @param userSeq 유저 시퀀스
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description
     */
    public User findById(final Long userSeq) {
        log.info("UserService.findById");
        return userRepository.findById(userSeq)
                .orElseThrow(() -> new CodeMessageHandleException(UserError.USER01.toString(), UserError.USER01.getMessage()));
    }

    /**
     * 상세 조회
     *
     * @param userId 유저 ID
     * @return the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description
     */
    public User findByUserId(final String userId) {
        log.info("UserService.findByUserId");
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CodeMessageHandleException(UserError.USER01.toString(), UserError.USER01.getMessage()));
    }

    /**
     * 인증코드 검증 및 비밀번호 변경
     *
     * @param userCertDTO the user cert dto
     * @return the boolean
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:18:42
     * @Description
     */
    public Boolean check(final UserCertDTO userCertDTO) {
        log.info("UserService.findByCertCode");
        String decodeCertCode = "";
        try {
            decodeCertCode = CryptoUtil.urlDecode(CryptoUtil.decryptAES256(userCertDTO.getCertCode(), "Nike DnP"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        final String userId = decodeCertCode.split("\\|")[0];
        final String certCode = (String) redisService.get("cert:" + userId);

        //TODO[ojh] 비밀번호가 다를때
        if (!userCertDTO.getNewPassword().equals(userCertDTO.getConfirmPassword())) {
            throw new CodeMessageHandleException("", "");
        }
        //TODO[ojh] 정규식이 다를때
        if (!userCertDTO.getNewPassword().equals(userCertDTO.getConfirmPassword())) {
            throw new CodeMessageHandleException("", "");
        }

        //TODO[ojh] 공통사전에 있을때
        if (!userCertDTO.getNewPassword().equals(userCertDTO.getConfirmPassword())) {
            throw new CodeMessageHandleException("", "");
        }

        //TODO[ojh] 아이디와 같을때
        if (!userCertDTO.getNewPassword().equals(userId)) {
            throw new CodeMessageHandleException("", "");
        }

        //TODO[ojh] 기존 비밀번호와 같을때
        if (!userCertDTO.getNewPassword().equals(userId)) {
            throw new CodeMessageHandleException("", "");
        }

        //TODO[ojh] 기존 비밀번호와 같을때
        if (decodeCertCode.split("\\|")[1].equals(certCode)) {

        }

        //this.updatePassword();

        return true;
    }

    /**
     * 비밀번호 설정 알림메일 발송
     *
     * @param userId the user id
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 3:27:51
     * @Description
     */
    public void sendEmail(final String userId) {
        log.info("UserService.sendEmailByPassword");

        try {
            //TODO[ojh] 인증코드 생성
            String certCode = RandomUtil.randomCertCode2(10);

            //TODO[ojh] ID+인증코드 암호화
            String encodeCertCode = CryptoUtil.urlEncode(CryptoUtil.encryptAES256(userId + "|" + certCode, "Nike DnP"));
            log.info("encodeCertCode > " + encodeCertCode);

            //TODO[ojh] REDIS 값 셋팅
            redisService.set("cert:"+userId, certCode, 60);

            //TODO[ojh] 패스워드 변경 이메일 발송

        } catch (Exception exception) {
            log.error("Exception", exception);
        }
    }

    /**
     * 등록
     *
     * @param userSaveDTO the user save dto
     * @param authUserDTO the auth user dto
     * @return the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description
     */
    @Transactional
    public User save(
            final UserSaveDTO userSaveDTO
            , final AuthUserDTO authUserDTO
    ) {
        log.info("UserService.save");
        final Optional<Auth> auth = authRepository.findById(userSaveDTO.getAuthSeq());
        final User user = userRepository.save(User.builder()
                .nickname(userSaveDTO.getNickname())
                .userId(userSaveDTO.getUserId())
                .registerSeq(authUserDTO.getUserSeq())
                .build());

        if (user.getUserSeq() > 0 && auth.isPresent()) {
            userAuthRepository.save(UserAuth.builder()
                    .userSeq(user.getUserSeq())
                    .authSeq(auth.get().getAuthSeq())
                    .build());
        }

        return user;
    }

    /**
     * 닉네임/권한 수정
     *
     * @param userSeq       the user seq
     * @param userUpdateDTO the user update dto
     * @param authUserDTO   the auth user dto
     * @return user user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description
     */
    @Transactional
    public Optional<User> update(
            final Long userSeq
            , final UserUpdateDTO userUpdateDTO
            , final AuthUserDTO authUserDTO
    ) {
        log.info("UserService.update");
        final Optional<Auth> auth = authRepository.findById(userUpdateDTO.getAuthSeq());
        final Optional<User> user = userRepository.findById(userSeq);
        if (user.isPresent() && auth.isPresent()) {
            user.get().update(userUpdateDTO.getNickname(), authUserDTO.getUserSeq());
            userAuthRepository.deleteByUserSeq(userSeq);
            userAuthRepository.save(
                    UserAuth.builder()
                            .userSeq(userSeq)
                            .authSeq(auth.get().getAuthSeq())
                            .build());
        }

        return user;
    }

    /**
     * 상태값 변경
     *
     * @param userSeq             the user seq
     * @param userUpdateStatusDTO the user update status dto
     * @param authUserDTO         the auth user dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description
     */
    @Transactional
    public Optional<User> updateStatus(
            final Long userSeq
            , final UserUpdateStatusDTO userUpdateStatusDTO
            , final AuthUserDTO authUserDTO
    ) {
        log.info("UserService.updateStatus");
        final Optional<User> user = userRepository.findById(userSeq);
        user.ifPresent(value -> value.updateStatus(userUpdateStatusDTO.getUserStatusCode(), authUserDTO.getUserSeq()));
        return user;
    }

    /**
     * 최종 로그인 일자 업데이트
     *
     * @param user the user
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description
     */
    @Transactional
    public void updateLoginDt(User user) {
        log.info("UserService.updateLoginDt");
        user.updateLoginDt();
    }

    /**
     * 로그인 검증
     *
     * @param userId 유저 ID
     * @return the authUserDTO
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:44
     * @Description
     */
    @Override
    public UserDetails loadUserByUsername(String userId) {
        log.info("UserService.loadUserByUsername");
        final User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(LoginError.LOGE01.toString()));
        return new AuthUserDTO(user);
    }
}
