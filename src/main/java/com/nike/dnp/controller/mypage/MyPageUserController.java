package com.nike.dnp.controller.mypage;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.validation.ValidationGroups;
import com.nike.dnp.common.variable.SuccessEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserReturnDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * The Class User my page controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 6. 오후 2:56:56
 * @Description 마이페이지 User Controller
 */
@Slf4j
@RestController
@Api(description = "마이페이지", tags = "MYPAGE")
@RequestMapping(value = "/api/mypage/user", name = "마이페이지 유저")
@RequiredArgsConstructor
public class MyPageUserController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * The User service
     *
     * @author [오지훈]
     */
    private final UserService userService;

    /**
     * The Auth service
     *
     * @author [오지훈]
     */
    private final AuthService authService;

    /**
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    /**
     * Gets redis menus.
     *
     * @param authUserDTO the auth user dto
     * @return the redis menus
     * @author [오지훈]
     * @CreatedOn 2020. 7. 16. 오후 5:16:45
     * @Description GNB 메뉴 목록
     */
    @ApiOperation(value = "GNB 메뉴 목록"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "GNB 메뉴", value = "/gnb"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<MenuReturnDTO>> getRedisMenus (
            final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("MenuController.getRedisMenus");
        return responseService.getSingleResult(authService.getAuthsMenusByRoleType(authUserDTO.getRole()));
    }

    /**
     * Find user single result.
     *
     * @param authUserDTO the auth user dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 5:19:29
     * @Description MY INFO 상세 조회
     */
    @ApiOperation(value = "MY INFO 상세 조회"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "MY INFO 상세 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserReturnDTO> getUser (
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO) {
        log.info("UserMyPageController.getUser");
        return responseService.getSingleResult(userService.getMyPage(authUserDTO.getUserSeq()));
    }

    /**
     * Change password single result.
     *
     * @param authUserDTO the auth user dto
     * @param userCertDTO the user cert dto
     * @param result      the result
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오후 4:08:39
     * @Description MY INFO 비밀번호 변경
     */
    @ApiOperation(value = "MY INFO 비밀번호 변경"
            , notes = OPERATION_CHARACTER)
    @PutMapping(name = "MY INFO 비밀번호 변경", value = "/change/password"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<UserReturnDTO> changePassword (
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
            , @ApiParam(value = "유저 인증코드 DTO", required = true) @RequestBody
                @Validated({ValidationGroups.group1.class, ValidationGroups.group2.class}) final UserCertDTO userCertDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserMyPageController.changePassword");
        return responseService.getSingleResult(
                userService.confirmPassword(authUserDTO.getUserId(), userCertDTO)
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.toString()
                , SuccessEnumCode.LoginSuccess.CHANGE_PASSWORD.getMessage()
                , true
        );
    }
}
