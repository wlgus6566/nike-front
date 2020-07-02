package com.nike.dnp.service.auth;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.common.variable.ErrorEnumCode.DataError;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * AuthService
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 2:40:43
 * @Description Auth(권한) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    /**
     * The Redis service
     *
     * @author [오지훈]
     */
    private final RedisService redisService;

    /**
     * AuthRepository
     *
     * @author [오지훈]
     */
    private final AuthRepository authRepository;

    /**
     * Find all list.
     *
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description 그룹(권한) 목록 조회
     */
    public List<Auth> findAll() {
        log.info("AuthService.findAll");

        //TODO[ojh] redis 조회 기능 작성 예정

        return authRepository.findAllByUseYn("Y");
    }

    /**
     * Find by id optional.
     *
     * @param authSeq the auth seq
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:45:32
     * @Description 그룹(권한) 상세 조회
     */
    public Optional<Auth> findById(final Long authSeq) {
        log.info("AuthService.findById");

        //TODO[ojh] redis 조회 기능 작성 예정

        return Optional.ofNullable(authRepository.findById(authSeq).orElseThrow(
                () -> new UserNotFoundException(DataError.NOT_FOUND.toString())));
    }

    /**
     * Save auth.
     *
     * @param authSaveDTO the auth save dto
     * @param authUserDTO the auth user dto
     * @return the auth
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:36:07
     * @Description 그룹(권한) 등록
     */
    public Auth save(
            final AuthSaveDTO authSaveDTO
            ,final AuthUserDTO authUserDTO
    ) {
        log.info("AuthService.save");

        //TODO[ojh] redis 저장 기능 작성 예정

        Auth auth = authRepository.save(
                Auth.builder()
                        .authSaveDTO(authSaveDTO)
                        .authUserDTO(authUserDTO)
                        .build()
        );
        return auth;
    }

    /**
     * Update optional.
     *
     * @param authSeq       the auth seq
     * @param authUpdateDTO the auth update dto
     * @param authUserDTO   the auth user dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:27:08
     * @Description 그룹(권한) 수정
     */
    public Optional<Auth> update(
            final Long authSeq
            ,final AuthUpdateDTO authUpdateDTO
            ,final AuthUserDTO authUserDTO
    ) {
        log.info("AuthService.update");

        //TODO[ojh] redis 수정 기능 작성 예정

        Optional<Auth> auth = Optional.ofNullable(authRepository.findById(authSeq).orElseThrow(
                () -> new UserNotFoundException(DataError.NOT_FOUND.toString())));
        auth.ifPresent(value -> value.update(authUpdateDTO, authUserDTO));
        return auth;
    }

    /**
     * Delete optional.
     *
     * @param authSeq     the auth seq
     * @param authUserDTO the auth user dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:37:29
     * @Description 그룹(권한) 삭제
     */
    public Optional<Auth> delete(
            final Long authSeq
            ,final AuthUserDTO authUserDTO
    ) {
        log.info("AuthService.delete");

        //TODO[ojh] redis 삭제 기능 작성 예정

        Optional<Auth> auth = Optional.ofNullable(authRepository.findById(authSeq).orElseThrow(
                () -> new UserNotFoundException(DataError.NOT_FOUND.toString())));
        auth.ifPresent(value -> value.delete(authUserDTO.getUserSeq()));
        return auth;
    }

}
