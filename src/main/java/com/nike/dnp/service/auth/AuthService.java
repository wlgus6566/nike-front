package com.nike.dnp.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ErrorEnumCode.DataError;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.dto.menu.MenuRoleResourceReturnDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.auth.AuthMenuRole;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.auth.AuthMenuRoleRepository;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.menu.MenuRepository;
import com.nike.dnp.repository.menu.MenuRoleResourceRepository;
import com.nike.dnp.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
     * The Auth menu role repository
     *
     * @author [오지훈]
     */
    private final AuthMenuRoleRepository authMenuRoleRepository;

    /**
     * The Menu role resource repository
     *
     * @author [오지훈]
     */
    private final MenuRoleResourceRepository menuRoleResourceRepository;

    /**
     * The Menu repository
     *
     * @author [오지훈]
     */
    private final MenuRepository menuRepository;

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
        return authRepository.findAllByUseYnAndUpperAuthSeqIsNull("Y");
    }

    /**
     * Find auth menu role list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오전 11:08:18
     * @Description 권한 메뉴 역할 조회
     */
    public List<AuthMenuRole> findAuthMenuRole(final Long authSeq) {
        return authMenuRoleRepository.findByAuthSeq(authSeq);
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
     * Find by role type optional.
     *
     * @param roleType the role type
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 1:38:11
     * @Description 권한 상세 조회(role type으로)
     */
    public Optional<Auth> findByRoleType(final String roleType) {
        log.info("AuthService.findByRoleType");
        return Optional.ofNullable(authRepository.findByRoleType(roleType).orElseThrow(() ->
                new CodeMessageHandleException(DataError.NOT_FOUND.toString(), DataError.NOT_FOUND.getMessage())));
    }

    /**
     * Find cache by role type list.
     *
     * @param roleType the role type
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 1:28:37
     * @Description 권한별 접근 가능 리소스 목록 조회
     */
    public List<MenuRoleResourceReturnDTO> getAuthsResourcesByRoleType(final String roleType) {
        log.info("AuthService.getAuthsResourcesByRoleType");

        List<MenuRoleResourceReturnDTO> redisMenuReources = (List<MenuRoleResourceReturnDTO>) redisService.get("auths:"+roleType);
        if (!ObjectUtils.isEmpty(redisMenuReources) && redisMenuReources.size() > 0) {
            return  redisMenuReources;
        }

        return menuRoleResourceRepository.getResources(
                this.findByRoleType(roleType).get().getAuthSeq());
    }

    /**
     * Sets auths resources by role type.
     *
     * @param roleType the role type
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 1:33:11
     * @Description 권한별 접근 가능 리소스 목록 저장(redis)
     */
    public void setAuthsResourcesByRoleType(final String roleType) {
        log.info("AuthService.setAuthsResourcesByRoleType");
        this.findByRoleType(roleType).ifPresent(value -> redisService.set("roles:auths:" + roleType, menuRoleResourceRepository.getResources(value.getAuthSeq()), 60));
    }

    /**
     * Gets auths menus by role type.
     *
     * @param roleType the role type
     * @return the auths menus by role type
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 1:37:19
     * @Description 권한별 접근 가능 메뉴 목록 조회
     */
    public List<MenuReturnDTO> getAuthsMenusByRoleType(final String roleType) {
        log.info("AuthService.getAuthsMenusByRoleType");

        List<MenuReturnDTO> redisMenus = (List<MenuReturnDTO>) redisService.get("roles:menus:"+roleType);
        if (!ObjectUtils.isEmpty(redisMenus) && redisMenus.size() > 0) {
            return  redisMenus;
        }

        Optional<Auth> auth = this.findByRoleType(roleType);
        List<MenuReturnDTO> menus = new ArrayList<>();
        if (auth.isPresent()) {
            menus = menuRepository.getMenus(auth.get().getAuthSeq());
            redisService.set("roles:menus:"+roleType, menus, 60);
        }

        return menus;
    }

    /**
     * Sets auths menus by role type.
     *
     * @param roleType the role type
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 1:44:34
     * @Description 권한별 접근 가능 메뉴 목록 저장(redis)
     */
    public void setAuthsMenusByRoleType(final String roleType) {
        log.info("AuthService.setAuthsMenusByRoleType");
        this.findByRoleType(roleType).ifPresent(value -> redisService.set("roles:menus:" + roleType, menuRepository.getMenus(value.getAuthSeq()), 60));
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

        if (authSaveDTO.getMenuRoleSeqArray().length > 0) {
            Arrays.stream(authSaveDTO.getMenuRoleSeqArray()).map(
                    menuRoleSeq -> AuthMenuRole.builder()
                        .authSeq(auth.getAuthSeq())
                        .menuRoleSeq(menuRoleSeq)
                        .build()).forEach(authMenuRoleRepository::save);
            this.setAuthsResourcesByRoleType(auth.getRoleType());
            this.setAuthsMenusByRoleType(auth.getRoleType());
        }

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

        if (auth.isPresent() && authUpdateDTO.getMenuRoleSeqArray().length > 0) {
            this.remove(authSeq);
            Arrays.stream(authUpdateDTO.getMenuRoleSeqArray()).map(
                    menuRoleSeq -> AuthMenuRole.builder()
                            .authSeq(auth.get().getAuthSeq())
                            .menuRoleSeq(menuRoleSeq)
                            .build()).forEach(authMenuRoleRepository::save);
            this.setAuthsResourcesByRoleType(auth.get().getRoleType());
            this.setAuthsMenusByRoleType(auth.get().getRoleType());
        }

        //TODO[ojh] 2020-07-13 : 등록/삭제 시퀀스배열이 따로 올 경우
        //TODO[ojh] 2020-07-13 : 이번엔 안하는걸로~
        /*if (auth.isPresent()) {
            if (authUpdateDTO.getDeleteMenuRoleSeqArray().length > 0) {
                authMenuRoleRepository.deleteAllByMenuRoleSeqArray(authSeq, authUpdateDTO.getDeleteMenuRoleSeqArray());
                authMenuRoleRepository.flush();
            }
            if (authUpdateDTO.getInsertMenuRoleSeqArray().length > 0) {
                Arrays.stream(authUpdateDTO.getInsertMenuRoleSeqArray()).map(
                        menuRoleSeq -> AuthMenuRole.builder()
                                .authSeq(auth.get().getAuthSeq())
                                .menuRoleSeq(menuRoleSeq)
                                .build()).forEach(authMenuRoleRepository::save);
                this.setAuthsResourcesByRoleType(auth.get().getRoleType());
                this.setAuthsMenusByRoleType(auth.get().getRoleType());
            }
        }*/

        return auth;
    }

    /**
     * Remove.
     *
     * @param authSeq the auth seq
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 3:34:40
     * @Description 권한 메뉴 역할 삭제
     */
    public void remove(final Long authSeq) {
        authMenuRoleRepository.deleteAllByAuthSeq(authSeq);
        authMenuRoleRepository.flush();
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

        if (auth.isPresent()) {
            this.remove(authSeq);
            redisService.delete("roles:auths:"+auth.get().getRoleType());
            redisService.delete("roles:menus:"+auth.get().getRoleType());
        }

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
