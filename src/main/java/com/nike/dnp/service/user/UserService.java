package com.nike.dnp.service.user;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.common.variable.ErrorEnumCode.LoginError;
import com.nike.dnp.common.variable.ErrorEnumCode.UserError;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.user.UserSaveDTO;
import com.nike.dnp.dto.user.UserSearchDTO;
import com.nike.dnp.dto.user.UserUpdateDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.user.User;
import com.nike.dnp.entity.user.UserAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.repository.user.UserRepository;
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
 * @Description User(유저) Service 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * UserRepository
     * @author [오지훈]
     */
    private final UserRepository userRepository;

    /**
     * UserRepository
     * @author [오지훈]
     */
    private final AuthRepository authRepository;

    /**
     * UserAuthRepository
     * @author [오지훈]
     */
    private final UserAuthRepository userAuthRepository;

    /**
     * 전체 조회
     *
     * @return the list
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 페이징 조회(paging)
     *
     * @param userSearchDTO the user search dto
     * @return the list
     * @author [오지훈]
     */
    public Page<User> findPages(final UserSearchDTO userSearchDTO) {
        return userRepository.findAlls(
                userSearchDTO,
                PageRequest.of(userSearchDTO.getPage()
                        , userSearchDTO.getSize()
                        , Sort.by("managerId").descending()));
    }

    /**
     * 상세 조회
     *
     * @param userSeq the user seq
     * @return the optional
     * @author [오지훈]
     */
    public User findById(final Long userSeq) {
        return userRepository.findById(userSeq)
                .orElseThrow(() -> new CodeMessageHandleException(UserError.USER01.toString(), UserError.USER01.getMessage()));
    }

    /**
     * 등록
     *
     * @param userSaveDTO the user save dto
     * @param authUserDTO the auth user dto
     * @return the user
     * @author [오지훈]
     */
    @Transactional
    public User save(
            final UserSaveDTO userSaveDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Auth> auth = authRepository.findById(userSaveDTO.getAuthSeq());
        User user = userRepository.save(User.builder()
                .nickname(userSaveDTO.getNickname())
                .userId(userSaveDTO.getUserId())
                .registerSeq(authUserDTO.getUserSeq())
                .build());

        if (user.getUserSeq() > 0) {
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
     */
    @Transactional
    public User update(
            final Long userSeq
            , final UserUpdateDTO userUpdateDTO
            , final AuthUserDTO authUserDTO
    ) {
        final Optional<Auth> auth = authRepository.findById(userUpdateDTO.getAuthSeq());
        Optional<User> user = userRepository.findById(userSeq);
        if (user.isPresent() && auth.isPresent()) {
            user.get().update(userUpdateDTO.getNickname(), authUserDTO.getUserSeq());
            userAuthRepository.deleteByUserSeq(userSeq);
            userAuthRepository.save(
                    UserAuth.builder()
                    .userSeq(userSeq)
                    .authSeq(auth.get().getAuthSeq())
                    .build());
        }

        return user.get();
    }

    /**
     * 최종 로그인일자 업데이트
     *
     * @param user the user
     * @author [오지훈]
     */
    @Transactional
    public void updateLoginDt(User user) {
        user.updateLoginDt();
    }

    /**
     * 로그인 검증
     *
     * @param userId
     * @return the authUserDTO
     * @author [오지훈]
     */
    @Override
    public UserDetails loadUserByUsername(String userId) {
       User user = userRepository.findByUserId(userId)
               .orElseThrow(() -> new UserNotFoundException(LoginError.LOGE01.toString()));
        return new AuthUserDTO(user);
    }
}
