package com.nike.dnp.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ErrorEnumCode.DataError;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.cache.annotation.Cacheable;
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
     * @CreatedOn 2020. 7. 7. 오후 3:37:06
     * @Description 그룹(권한) 목록 조회
     */
    public List<Auth> findAll() {
        log.info("AuthService.findAll");
        return authRepository.findAllByUseYn("Y");
    }

    /**
     * Find all json array.
     *
     * @return the json array
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 2:40:43
     * @Description 그룹(권한) 목록 조회(캐시)
     */
    @Cacheable(value = "cache:auths", cacheManager = "cacheManager")
    public JSONArray findAllByCache() {
        log.info("AuthService.findAllByCache");
        ObjectMapper ob = new ObjectMapper();
        try {
            return ob.readValue(ob.writeValueAsString(this.findAll()), JSONArray.class);
        } catch (JsonProcessingException e) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.ExceptionError.ERROR.toString()
                    , ErrorEnumCode.ExceptionError.ERROR.getMessage()
            );
        }
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
        return Optional.ofNullable(authRepository.findById(authSeq).orElseThrow(
                () -> new CodeMessageHandleException(
                        DataError.NOT_FOUND.toString()
                        , DataError.NOT_FOUND.getMessage()
                )));
    }

    /**
     * Save auth.
     *
     * @param authSaveDTO the auth save dto
     * @return the auth
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:36:07
     * @Description 그룹(권한) 등록
     */
    @Transactional
    public Auth save(final AuthSaveDTO authSaveDTO) {
        log.info("AuthService.save");
        Auth auth = authRepository.save(Auth.builder().authSaveDTO(authSaveDTO).build());
        this.initAuthCache();
        return auth;
    }

    /**
     * Update optional.
     *
     * @param authSeq       the auth seq
     * @param authUpdateDTO the auth update dto
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:27:08
     * @Description 그룹(권한) 수정
     */
    @Transactional
    public Optional<Auth> update(
            final Long authSeq
            ,final AuthUpdateDTO authUpdateDTO
    ) {
        log.info("AuthService.update");
        Optional<Auth> auth = this.findById(authSeq);
        auth.ifPresent(value -> value.update(authUpdateDTO));
        this.initAuthCache();
        return auth;
    }

    /**
     * Delete optional.
     *
     * @param authSeq the auth seq
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:37:29
     * @Description 그룹(권한) 삭제
     */
    @Transactional
    public Optional<Auth> delete(final Long authSeq) {
        log.info("AuthService.delete");
        Optional<Auth> auth = this.findById(authSeq);
        auth.ifPresent(Auth::delete);
        this.initAuthCache();
        return auth;
    }

    /**
     * Init auth cache.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 7. 7. 오후 4:31:05
     * @Description 캐시 초기화
     */
    public void initAuthCache() {
        log.info("AuthService.initAuthCache");
        redisService.delete("cache:auths::SimpleKey []");
        ObjectMapper ob = new ObjectMapper();
        try {
            redisService.set("cache:auths::SimpleKey []", ob.readValue(ob.writeValueAsString(this.findAll()), JSONArray.class), 60 * 24 * 30);
        } catch (JsonProcessingException e) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.ExceptionError.ERROR.toString()
                    , ErrorEnumCode.ExceptionError.ERROR.getMessage()
            );
        }
    }

}
