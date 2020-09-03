package com.nike.dnp.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.auth.AuthSaveDTO;
import com.nike.dnp.dto.auth.AuthUpdateDTO;
import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.dto.menu.MenuRoleResourceReturnDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.auth.AuthMenuRole;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.auth.AuthMenuRoleRepository;
import com.nike.dnp.repository.auth.AuthRepository;
import com.nike.dnp.repository.menu.MenuRepository;
import com.nike.dnp.repository.menu.MenuRoleRepository;
import com.nike.dnp.repository.menu.MenuRoleResourceRepository;
import com.nike.dnp.repository.user.UserAuthRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.ObjectMapperUtil;
import com.nike.dnp.util.SecurityUtil;
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
 * @implNote Auth(권한) Service 작성
 * @since 2020. 6. 22. 오후 2:40:43
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    /**
     * The constant REDIS_ROLES_MENUS
     *
     * @author [오지훈]
     */
    private final static String REDIS_ROLES_MENUS = "roles:menus:";

    /**
     * The constant REDIS_ROLES_AUTHS
     *
     * @author [오지훈]
     */
    private final static String REDIS_ROLES_AUTHS = "roles:auths:";


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
     * The Menu role repository
     *
     * @author [오지훈]
     */
    private final MenuRoleRepository menuRoleRepository;

    /**
     * UserAuthRepository
     *
     * @author [오지훈]
     */
    private final UserAuthRepository userAuthRepository;

    /**
     * Find all list.
     *
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 7. 오후 3:37:06
     * @implNote 그룹(권한) 목록 조회
     */
    public List<Auth> findAll() {
        log.info("AuthService.findAll");
        return authRepository.findAllByUseYnAndUpperAuthSeqIsNull("Y");
    }

    /**
     * Count by auth seq int.
     *
     * @param authSeq the auth seq
     * @return the int
     * @author [오지훈]
     * @implNote 해당 권한의 유저 수 확인
     * @since 2020. 9. 1. 오전 11:06:48
     */
    public int countByAuthSeq(final Long authSeq) {
        return userAuthRepository.findAllByAuthSeq(authSeq).size();
    }

    /**
     * Find auth menu role list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 13. 오전 11:08:18
     * @implNote 권한 메뉴 역할 조회
     */
    public List<AuthMenuRole> findAuthMenuRole(final Long authSeq) {
        return authMenuRoleRepository.findByAuthSeq(authSeq);
    }

    /**
     * Find by auth depth list.
     *
     * @param authSeq   the auth seq
     * @param menuCode  the menu code
     * @param skillCode the skill code
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 5:11:54
     * @implNote 권한 뎁스 별 목록 조회
     */
    public List<AuthReturnDTO> findByAuthDepth(
            final Long authSeq
            , final String menuCode
            , final String skillCode
    ) {
        return authRepository.findByAuthDepth(
                authSeq
                ,this.getById(authSeq).getAuthDepth()
                , menuCode
                , skillCode
        );
    }

    /**
     * Find auths list.
     *
     * @return the list
     * @author [오지훈]
     * @implNote 그룹목록(Tree 구조), 사용안하는 그룹 제외
     * @since 2020. 9. 1. 오전 10:58:03
     */
    public List<AuthReturnDTO> findAuths() {
        log.info("AuthService.findAuths");
        final List<AuthReturnDTO> firstAuths = new ArrayList<>();
        for (Auth auth : this.findAll()) {
            if (auth.getUseYn().equals(ServiceCode.YesOrNoEnumCode.Y.name())) {
                final AuthReturnDTO topAuthDTO = ObjectMapperUtil.map(auth, AuthReturnDTO.class);
                if (auth.getSubAuths().size() > 0) {
                    final List<AuthReturnDTO> secondAuths = new ArrayList<>();
                    for (Auth secondDepth : auth.getSubAuths()) {
                        if (secondDepth.getUseYn().equals(ServiceCode.YesOrNoEnumCode.Y.name())) {
                            final AuthReturnDTO bottomAuthDTO = ObjectMapperUtil.map(secondDepth, AuthReturnDTO.class);
                            if (secondDepth.getSubAuths().size() > 0) {
                                final List<AuthReturnDTO> thirdAuths = new ArrayList<>();
                                for (Auth thirdDepth : secondDepth.getSubAuths()) {
                                    if (thirdDepth.getUseYn().equals(ServiceCode.YesOrNoEnumCode.Y.name())) {
                                        thirdAuths.add(ObjectMapperUtil.map(thirdDepth, AuthReturnDTO.class));
                                    }
                                }
                                bottomAuthDTO.setSubAuths(thirdAuths);
                            }
                            secondAuths.add(bottomAuthDTO);
                        }
                    }
                    topAuthDTO.setSubAuths(secondAuths);
                }
                firstAuths.add(topAuthDTO);
            }
        }

        return firstAuths;
    }

    /**
     * Find all json array.
     *
     * @return the json array
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 2:40:43
     * @implNote 그룹(권한) 목록 조회(캐시)
     */
    @Cacheable(value = "cache:auths", cacheManager = "cacheManager")
    public List<AuthReturnDTO> findAllByCache() {
        log.info("AuthService.findAllByCache");
        return this.findAuths();
    }
    /*public JSONArray findAllByCache() {
        log.info("AuthService.findAllByCache");
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(this.findAll()), JSONArray.class);
        } catch (JsonProcessingException exception) {
            throw (CodeMessageHandleException) new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.toString()
                    , exception.getMessage()
            );
        }
    }*/

    /**
     * Find by role type optional.
     *
     * @param roleType the role type
     * @return the optional
     * @author [오지훈]
     * @since 2020. 7. 13. 오후 1:38:11
     * @implNote 권한 상세 조회(role type으로)
     */
    public Optional<Auth> findByRoleType(final String roleType) {
        log.info("AuthService.findByRoleType");
        return Optional.ofNullable(authRepository.findByRoleType(roleType).orElseThrow(NotFoundHandleException::new));
    }

    /**
     * Gets by role type.
     *
     * @param roleType the role type
     * @return the by role type
     * @author [오지훈]
     * @since 2020. 7. 30. 오전 11:57:02
     * @implNote 권한 상세 조회(role type으로)
     */
    public Auth getByRoleType(final String roleType) {
        log.info("AuthService.getByRoleType");
        return this.findByRoleType(roleType).orElse(new Auth());
    }

    /**
     * Gets menus.
     *
     * @return the menus
     * @author [오지훈]
     * @implNote 접근 가능 메뉴 조회
     * @since 2020. 8. 10. 오후 5:31:38
     */
    public List<MenuReturnDTO> getMenus() {
        log.info("AuthService.getMenus");
        return this.getAuthsMenusByRoleType(SecurityUtil.currentUser().getRole());
    }

    /**
     * Find cache by role type list.
     *
     * @param roleType the role type
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 13. 오후 1:28:37
     * @implNote 권한별 접근 가능 리소스 목록 조회
     */
    public List<MenuRoleResourceReturnDTO> getAuthsResourcesByRoleType(final String roleType) {
        log.info("AuthService.getAuthsResourcesByRoleType");
        List<MenuRoleResourceReturnDTO> redisReources = (List<MenuRoleResourceReturnDTO>) redisService.get(REDIS_ROLES_AUTHS+roleType);
        if (ObjectUtils.isEmpty(redisReources)) {
            redisReources = menuRoleResourceRepository.getResources(this.getByRoleType(roleType).getAuthSeq());
            redisService.set(REDIS_ROLES_AUTHS+roleType, redisReources, 60);
        }
        return redisReources;
    }

    /**
     * Sets auths resources by role type.
     *
     * @param roleType the role type
     * @author [오지훈]
     * @since 2020. 7. 13. 오후 1:33:11
     * @implNote 권한별 접근 가능 리소스 목록 저장(redis)
     */
    public void setAuthsResourcesByRoleType(final String roleType) {
        log.info("AuthService.setAuthsResourcesByRoleType");
        this.findByRoleType(roleType).ifPresent(value -> redisService.set(REDIS_ROLES_AUTHS + roleType, menuRoleResourceRepository.getResources(value.getAuthSeq()), 60));
    }

    /**
     * Gets auths menus by role type.
     *
     * @param roleType the role type
     * @return the auths menus by role type
     * @author [오지훈]
     * @implNote 권한별 접근 가능 메뉴 목록 조회
     * @since 2020. 7. 13. 오후 1:37:19
     */
    public List<MenuReturnDTO> getAuthsMenusByRoleType(final String roleType) {
        log.info("AuthService.getAuthsMenusByRoleType");
        List<MenuReturnDTO> redisMenus = (List<MenuReturnDTO>) redisService.get(REDIS_ROLES_MENUS+roleType);
        if (ObjectUtils.isEmpty(redisMenus)) {
            redisMenus = this.getAuthsMenusByRoleType(this.getByRoleType(roleType).getAuthSeq());
            redisService.set(REDIS_ROLES_MENUS+roleType, redisMenus, 60);
        }
        return redisMenus;
    }

    /**
     * Gets auths menus by role type.
     *
     * @param authSeq the auth seq
     * @return the auths menus by role type
     * @author [오지훈]
     * @implNote 권한별 접근 가능 메뉴 목록 조회
     * @since 2020. 8. 10. 오후 6:26:08
     */
    public List<MenuReturnDTO> getAuthsMenusByRoleType(final Long authSeq) {
        log.info("AuthService.getAuthsMenusByRoleType");
        final List<MenuReturnDTO> menus = new ArrayList<>();
        final List<MenuReturnDTO> upperMenus = menuRepository.getUpperMenus(authSeq);

        for (final MenuReturnDTO upperMenu : upperMenus) {
            if ("HOME".equals(upperMenu.getMenuCode())) {
                menus.add(upperMenu);
            } else if ("N".equals(upperMenu.getManagementYn())) {
                final List<MenuReturnDTO> lowerMenus = menuRepository.getSubMenus(upperMenu.getMenuSeq(), 2L);
                if (!lowerMenus.isEmpty()) {
                    for (final MenuReturnDTO lowerMenu : lowerMenus) {
                        if ("Y".equals(lowerMenu.getManagementYn())) {
                            lowerMenu.setMenus(menuRepository.getLowerMenus(authSeq, lowerMenu.getMenuSeq(), 3L));
                        } else {
                            lowerMenu.setMenus(menuRepository.getSubMenus(lowerMenu.getMenuSeq(), 3L));
                        }
                        lowerMenu.setMenuRoles(menuRoleRepository.findByMenuSeq(lowerMenu.getMenuSeq()));
                    }
                    upperMenu.setMenus(lowerMenus);
                    upperMenu.setMenuRoles(menuRoleRepository.findByMenuSeq(upperMenu.getMenuSeq()));
                    menus.add(upperMenu);
                }
            } else if ("Y".equals(upperMenu.getManagementYn())) {
                final List<MenuReturnDTO> lowerMenus = menuRepository.getLowerMenus(authSeq, upperMenu.getMenuSeq(), 2L);
                if (!lowerMenus.isEmpty()) {
                    for (final MenuReturnDTO lowerMenu : lowerMenus) {
                        if ("Y".equals(lowerMenu.getManagementYn())) {
                            lowerMenu.setMenus(menuRepository.getLowerMenus(authSeq, lowerMenu.getMenuSeq(), 3L));
                        } else {
                            lowerMenu.setMenus(menuRepository.getSubMenus(lowerMenu.getMenuSeq(), 3L));
                        }
                        lowerMenu.setMenuRoles(menuRoleRepository.findByMenuSeq(lowerMenu.getMenuSeq()));
                    }
                    upperMenu.setMenus(lowerMenus);
                    upperMenu.setMenuRoles(menuRoleRepository.findByMenuSeq(upperMenu.getMenuSeq()));
                    menus.add(upperMenu);
                }
            }
        }

        return menus;
    }

    /**
     * Sets auths menus by role type.
     *
     * @param roleType the role type
     * @author [오지훈]
     * @since 2020. 7. 13. 오후 1:44:34
     * @implNote 권한별 접근 가능 메뉴 목록 저장(redis)
     */
    public void setAuthsMenusByRoleType(final String roleType) {
        log.info("AuthService.setAuthsMenusByRoleType");
        this.findByRoleType(roleType).ifPresent(value -> redisService.set(REDIS_ROLES_MENUS + roleType, this.getAuthsMenusByRoleType(value.getAuthSeq()), 60));
    }


    /**
     * Find by id optional.
     *
     * @param authSeq the auth seq
     * @return the optional
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:45:32
     * @implNote 그룹(권한) 상세 조회
     */
    public Optional<Auth> findById(final Long authSeq) {
        log.info("AuthService.findById");
        return Optional.ofNullable(authRepository.findById(authSeq).orElseThrow(
                () -> new NotFoundHandleException()));
    }

    /**
     * Gets by id.
     *
     * @param authSeq the auth seq
     * @return the by id
     * @author [오지훈]
     * @since 2020. 7. 22. 오전 11:32:07
     * @implNote 그룹(권한) 상세 조회
     */
    public Auth getById(final Long authSeq) {
        log.info("AuthService.getById");
        return this.findById(authSeq).orElseGet(Auth::new);
    }

    /**
     * Save auth.
     *
     * @param authSaveDTO the auth save dto
     * @return the auth
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:36:07
     * @implNote 그룹(권한) 등록
     */
    @Transactional
    public Auth save(final AuthSaveDTO authSaveDTO) {
        log.info("AuthService.save");
        final Auth auth = authRepository.save(Auth.builder().authSaveDTO(authSaveDTO).build());
        this.initAuthCache();

        if (authSaveDTO.getMenuRoleSeqArray().length > 0) {
            Arrays.stream(authSaveDTO.getMenuRoleSeqArray()).map(
                    menuRoleSeq -> AuthMenuRole.builder()
                        .authSeq(auth.getAuthSeq())
                        .menuRoleSeq(menuRoleSeq)
                        .build()).forEach(authMenuRoleRepository::save);
            //this.setAuthsResourcesByRoleType(auth.getRoleType());
            //this.setAuthsMenusByRoleType(auth.getRoleType());
        }

        return auth;
    }

    /**
     * Update optional.
     *
     * @param authUpdateDTO the auth update dto
     * @return the optional
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:27:08
     * @implNote 그룹(권한) 수정
     */
    @Transactional
    public Auth update(
            final Long authSeq
            , final AuthUpdateDTO authUpdateDTO
    ) {
        log.info("AuthService.update");
        final Auth auth = this.getById(authSeq);
        final String roleType = auth.getRoleType();

        auth.update(authUpdateDTO);
        this.initAuthCache();
        this.remove(authSeq);
        if (authUpdateDTO.getMenuRoleSeqArray().length > 0) {
            Arrays.stream(authUpdateDTO.getMenuRoleSeqArray()).map(
                    menuRoleSeq -> AuthMenuRole.builder()
                            .authSeq(authSeq)
                            .menuRoleSeq(menuRoleSeq)
                            .build()).forEach(authMenuRoleRepository::save);

            redisService.delete(REDIS_ROLES_AUTHS + roleType);
            redisService.delete(REDIS_ROLES_MENUS + roleType);
//            this.setAuthsResourcesByRoleType(roleType);
//            this.setAuthsMenusByRoleType(roleType);
        }
//        else {
//            redisService.delete(REDIS_ROLES_AUTHS + roleType);
//            redisService.delete(REDIS_ROLES_MENUS + roleType);
//        }

        // 등록/삭제 시퀀스배열이 따로 올 경우 > 이번엔 안하는걸로~
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
     * @since 2020. 7. 13. 오후 3:34:40
     * @implNote 권한 메뉴 역할 삭제
     */
    @Transactional
    public void remove(final Long authSeq) {
        log.info("AuthService.remove");
        authMenuRoleRepository.deleteAllByAuthSeq(authSeq);
        authMenuRoleRepository.flush();
    }

    /**
     * Delete optional.
     *
     * @param authSeq the auth seq
     * @return the optional
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:37:29
     * @implNote 그룹(권한) 삭제
     */
    @Transactional
    public Auth delete(final Long authSeq) {
        log.info("AuthService.delete");
        final Auth auth = this.getById(authSeq);

        // 그룹에 속한 유저 수 파악
        if (this.countByAuthSeq(authSeq) > 0) {
            throw new CodeMessageHandleException(
                    FailCode.ConfigureError.FAIL_DELETE.name()
                    , MessageUtil.getMessage(FailCode.ConfigureError.FAIL_DELETE.name())
            );
        }

        auth.delete();
        this.initAuthCache();
        this.remove(authSeq);
        redisService.delete(REDIS_ROLES_AUTHS+auth.getRoleType());
        redisService.delete(REDIS_ROLES_MENUS+auth.getRoleType());

        return auth;
    }

    /**
     * Init auth cache.
     *
     * @author [오지훈]
     * @since 2020. 7. 7. 오후 4:31:05
     * @implNote 캐시 초기화
     */
    public void initAuthCache() {
        log.info("AuthService.initAuthCache");
        redisService.delete("cache:auths::SimpleKey []");
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            redisService.set("cache:auths::SimpleKey []", objectMapper.readValue(objectMapper.writeValueAsString(this.findAll()), JSONArray.class), 60 * 24 * 30);
        } catch (JsonProcessingException exception) {
            throw (CodeMessageHandleException) new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.toString()
                    , exception.getMessage()
            );
        }
    }

    /**
     * Find by config list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the list
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 31. 오후 2:43:41
     */
    public List<AuthReturnDTO> findByConfig(final UserAuthSearchDTO userAuthSearchDTO) {
        return authRepository.findByConfig(userAuthSearchDTO);
    }

    /**
     * Find by config for asset all list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the list
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 31. 오후 9:52:07
     */
    public List<AuthReturnDTO> findByConfigForAssetAll(final UserAuthSearchDTO userAuthSearchDTO) {
        return authRepository.findByConfigForAssetAll(userAuthSearchDTO);
    }

    /**
     * Gets auth list with depth.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @param auth              the auth
     * @return the auth list with depth
     * @author [이소정]
     * @implNote 권한 depth에 맞는 하위 목록
     * @since 2020. 8. 18. 오후 10:07:48
     */
    public List<AuthReturnDTO> getAuthListWithDepth(final UserAuthSearchDTO userAuthSearchDTO, final Auth auth, final String onlySkillCode) {
        log.info("AuthService.getAuthListWithDepth");
        List<AuthReturnDTO> allAuthList;
        if ("Y".equals(onlySkillCode)) {
            allAuthList = this.getAuthListWithoutN(userAuthSearchDTO);
        } else {
            allAuthList = this.getAuthList(userAuthSearchDTO);
        }

        List<AuthReturnDTO> transformAuthList = new ArrayList<>();

        if (!ObjectUtils.isEmpty(allAuthList) && !allAuthList.isEmpty()) {

            if (1 == auth.getAuthDepth()) {
                transformAuthList = allAuthList;
            } else if (2 == auth.getAuthDepth()) {
                for (AuthReturnDTO authReturnDTO : allAuthList) {
                    if (auth.getAuthSeq().equals(authReturnDTO.getAuthSeq())) {
                        transformAuthList.add(authReturnDTO);
                        break;
                    }

                    // 3depth인경우
                    AuthReturnDTO findAuth =this.findAuthDepthList(auth.getAuthSeq(), authReturnDTO.getSubAuths());
                    if (!ObjectUtils.isEmpty(findAuth.getAuthSeq())) {
                        transformAuthList.add(findAuth);
                        break;
                    }

                }
            }
        }
        return transformAuthList;
    }

    /**
     * Find auth depth list auth return dto.
     *
     * @param authSeq the auth seq
     * @param subList the sub list
     * @return the auth return dto
     * @author [이소정]
     * @implNote 권한 depth 구조에 맞는 auth 찾기
     * @since 2020. 8. 20. 오후 1:01:22
     */
    public AuthReturnDTO findAuthDepthList(final Long authSeq, final List<AuthReturnDTO> subList) {

        AuthReturnDTO findAuth = new AuthReturnDTO();
        if (!ObjectUtils.isEmpty(subList) && !subList.isEmpty()) {
            for (AuthReturnDTO authReturnDTO : subList) {
                if (authSeq.equals(authReturnDTO.getAuthSeq())) {
                    findAuth = authReturnDTO;
                    break;
                }

                if (!ObjectUtils.isEmpty(findAuth) && !ObjectUtils.isEmpty(authReturnDTO.getSubAuths()) && !authReturnDTO.getSubAuths().isEmpty()) {
                    findAuth = this.findAuthDepthList(authSeq, authReturnDTO.getSubAuths());
                }
            }
        }
        return findAuth;
    }

    /**
     * Gets auth list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the auth list
     * @author [오지훈]
     * @implNote 컨텐츠 권한 목록 (권한 N인것도 다 포함)
     * @since 2020. 7. 20. 오후 4:25:19
     */
    public List<AuthReturnDTO> getAuthList(final UserAuthSearchDTO userAuthSearchDTO) {
        log.info("AuthService.getAuthList");
        final List<AuthReturnDTO> findByConfig = this.findByConfig(userAuthSearchDTO);
        final List<AuthReturnDTO> auths = this.findAuths();

        for (AuthReturnDTO authReturnDTO : auths) {
            for (AuthReturnDTO config : findByConfig) {
                this.authConfig(authReturnDTO, config, userAuthSearchDTO.getCreateYn());

                for (AuthReturnDTO dto2 : authReturnDTO.getSubAuths()) {
                    this.authConfig(dto2, config, userAuthSearchDTO.getCreateYn());

                    for (AuthReturnDTO dto3 : dto2.getSubAuths()) {
                        this.authConfig(dto3, config, userAuthSearchDTO.getCreateYn());
                    }
                }
            }
        }

        for (AuthReturnDTO authReturnDTO : auths) {
            boolean check = true;
            for (AuthReturnDTO dto2 : authReturnDTO.getSubAuths()) {
                boolean check1 = true;
                for (AuthReturnDTO dto3 : dto2.getSubAuths()) {
                    if (check1 && dto3.getViewYn().equals("Y")) {
                        check1 = false;
                        if (!dto2.getViewYn().equals("Y")) {
                            dto2.setViewYn("Y");
                            dto2.setCheckBoxYn("N");
                        }
                    }
                }

                if (check && dto2.getViewYn().equals("Y")) {
                    check = false;
                    if (!authReturnDTO.getViewYn().equals("Y")) {
                        authReturnDTO.setViewYn("Y");
                        authReturnDTO.setCheckBoxYn("N");
                    }
                }
            }
        }

        return auths;
    }

    /**
     * Gets auth list 2.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the auth list 2
     * @author [이소정]
     * @implNote 권한 목록 조회 (권한 Y인것만)
     * @since 2020. 8. 31. 오후 2:49:32
     */
    public List<AuthReturnDTO> getAuthListWithoutN(final UserAuthSearchDTO userAuthSearchDTO) {
        log.info("AuthService.getAuthList2");
        final List<AuthReturnDTO> findByConfig = this.findByConfig(userAuthSearchDTO);
        final List<AuthReturnDTO> auths = this.findAuths();

        for (AuthReturnDTO authReturnDTO : auths) {
            for (AuthReturnDTO config : findByConfig) {
                this.authConfig(authReturnDTO, config, userAuthSearchDTO.getCreateYn());

                for (AuthReturnDTO dto2 : authReturnDTO.getSubAuths()) {
                    this.authConfig(dto2, config, userAuthSearchDTO.getCreateYn());

                    for (AuthReturnDTO dto3 : dto2.getSubAuths()) {
                        this.authConfig(dto3, config, userAuthSearchDTO.getCreateYn());
                    }
                }
            }
        }

        for (AuthReturnDTO authReturnDTO : auths) {
            boolean check = true;
            for (AuthReturnDTO dto2 : authReturnDTO.getSubAuths()) {
                boolean check1 = true;
                for (AuthReturnDTO dto3 : dto2.getSubAuths()) {
                    if (check1 && dto3.getViewYn().equals("Y")) {
                        check1 = false;
                        if (!dto2.getViewYn().equals("Y")) {
                            dto2.setViewYn("Y");
                            dto2.setCheckBoxYn("N");
                        }
                    }
                }

                if (check && dto2.getViewYn().equals("Y")) {
                    check = false;
                    if (!authReturnDTO.getViewYn().equals("Y")) {
                        authReturnDTO.setViewYn("Y");
                        authReturnDTO.setCheckBoxYn("N");
                    }
                }
            }
        }

        final List<AuthReturnDTO> returns = new ArrayList<>();
        for (AuthReturnDTO authReturnDTO : auths) {
            if (authReturnDTO.getViewYn().equals("Y")) {
                final AuthReturnDTO return1 = authReturnDTO;
                final List<AuthReturnDTO> return1sub = new ArrayList<>();
                for (AuthReturnDTO dto2 : return1.getSubAuths()) {
                    if (dto2.getViewYn().equals("Y")) {
                        final AuthReturnDTO return2 = dto2;
                        final List<AuthReturnDTO> return2sub = new ArrayList<>();
                        for (AuthReturnDTO dto3 : return2.getSubAuths()) {
                            if (dto3.getViewYn().equals("Y")) {
                                return2sub.add(dto3);
                            }
                        }
                        return2.setSubAuths(return2sub);
                        return1sub.add(return2);
                    }
                }
                return1.setSubAuths(return1sub);
                returns.add(return1);
            }
        }

        return returns;
    }

    public List<AuthReturnDTO> getAuthList3 () {
        log.info("AuthService.getAuthList3");
        final List<AuthReturnDTO> auths = this.findAuths();
        final List<AuthReturnDTO> returns = new ArrayList<>();
        for (AuthReturnDTO authReturnDTO : auths) {
            if (authReturnDTO.getUseYn().equals("Y")) {
                final AuthReturnDTO return1 = authReturnDTO;
                final List<AuthReturnDTO> return1sub = new ArrayList<>();
                for (AuthReturnDTO dto2 : return1.getSubAuths()) {
                    if (dto2.getUseYn().equals("Y")) {
                        final AuthReturnDTO return2 = dto2;
                        final List<AuthReturnDTO> return2sub = new ArrayList<>();
                        for (AuthReturnDTO dto3 : return2.getSubAuths()) {
                            if (dto3.getUseYn().equals("Y")) {
                                return2sub.add(dto3);
                            }
                        }
                        return2.setSubAuths(return2sub);
                        return1sub.add(return2);
                    }
                }
                return1.setSubAuths(return1sub);
                returns.add(return1);
            }
        }

        return returns;
    }

    /**
     * Auth config.
     *
     * @param target   the target
     * @param config   the config
     * @param createYn the create yn
     * @author [오지훈]
     * @implNote authConfig
     * @since 2020. 8. 14. 오후 5:23:07
     */
    private void authConfig(final AuthReturnDTO target, final AuthReturnDTO config, final String createYn) {
        if (target.getAuthSeq().equals(config.getAuthSeq())) {
            if ("Y".equals(createYn)) {
                target.setDetailAuthYn("Y");
                target.setEmailReceptionYn("Y");
            } else {
                target.setDetailAuthYn(config.getDetailAuthYn());
                target.setEmailReceptionYn(config.getEmailReceptionYn());
            }

            target.setViewYn("Y");
            target.setCheckBoxYn("Y");
        }
    }

    /**
     * Delete key.
     *
     * @param key the key
     * @author [오지훈]
     * @implNote redis key 삭제
     * @since 2020. 8. 25. 오후 6:49:41
     */
    public void deleteKey(final String key) {
        redisService.delete(key);
    }
}
