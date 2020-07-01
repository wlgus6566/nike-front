package com.nike.dnp.service.user;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ErrorEnumCode.DataError;
import com.nike.dnp.common.variable.ErrorEnumCode.UserError;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.common.variable.SuccessEnumCode.UserSuccessEnum;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.user.*;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.SingleResult;
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
        final User user = userRepository.save(new User().save(userSaveDTO));
        final Auth auth = authRepository.findById(userSaveDTO.getAuthSeq()).orElseThrow(
                () -> new CodeMessageHandleException(DataError.NOT_FOUND.toString(), DataError.NOT_FOUND.getMessage()));
        return userAuthRepository.save(new UserAuth().save(user, auth));
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

        //TODO[ojh] 배열삭제 작업 중
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

    public SingleResult<Integer> checkId(final UserIdDTO userIdDTO) {
        final SingleResult<Integer> result = new SingleResult<>();
        final String userId = userIdDTO.getUserId();
        String code = "";
        String msg = "";

        final int count = this.countByUserId(userId);
        code = count > 0 ? "" : UserSuccessEnum.NOT_DUPLICATE.toString();
        msg = count > 0 ? "" : UserSuccessEnum.NOT_DUPLICATE.getMessage();

        result.setData(count);
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(true);
        result.setExistMsg(true);
        return result;
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
    public Boolean checkCertCode(final UserCertDTO userCertDTO) {
        log.info("UserService.checkCertCode");
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
                    ServiceEnumCode.EmailType.USER_CREATE.toString()
                    ,ServiceEnumCode.EmailType.USER_CREATE.getMessage()
                    ,sendDTO
            );

        } catch (Exception exception) {
            log.error("Exception", exception);
        }
    }
}
