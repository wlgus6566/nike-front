package com.nike.dnp.controller.mypage;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.SuccessCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.dto.user.UserCertDTO;
import com.nike.dnp.dto.user.UserResultDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.user.UserService;
import com.nike.dnp.util.MessageUtil;
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
 * @since 2020. 7. 6. 오후 2:56:56
 * @apiNote 마이페이지 User Controller
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
     * @return the redis menus
     * @author [오지훈]
     * @implNote [Description 작성]
     * @apiNote GNB 메뉴 목록
     * @since 2020. 7. 16. 오후 5:16:45
     */
    @ApiOperation(value = "GNB 메뉴 목록"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "GNB 메뉴", value = "/gnb"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<MenuReturnDTO>> getRedisMenus () {
        log.info("MenuController.getRedisMenus");
        return responseService.getSingleResult(authService.getMenus());
    }

    /**
     * Find user single result.
     *
     * @return the single result
     * @author [오지훈]
     * @implNote [Description 작성]
     * @apiNote MY INFO 상세 조회
     * @since 2020. 6. 23. 오후 5:19:29
     */
    @ApiOperation(value = "MY INFO 상세 조회"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "MY INFO 상세 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<UserResultDTO> getUser () {
        log.info("UserMyPageController.getUser");
        return responseService.getSingleResult(userService.getMyPage());
    }

    /**
     * Gets user session init.
     *
     * @return the user session init
     * @author [오지훈]
     * @implNote USER 세션 갱신
     * @since 2020. 8. 14. 오후 1:51:47
     */
    @ApiOperation(value = "USER 세션 갱신"
            , notes = OPERATION_CHARACTER)
    @GetMapping(name = "USER 세션 갱신", value = "/init"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Boolean> getUserSessionInit () {
        log.info("UserMyPageController.getUserSessionInit");
        return responseService.getSingleResult(true);
    }

    /**
     * Change password single result.
     *
     * @param authUserDTO the auth user dto
     * @param userCertDTO the user cert dto
     * @param result      the result
     * @return the single result
     * @author [오지훈]
     * @since 2020. 7. 2. 오후 4:08:39
     * @apiNote MY INFO 비밀번호 변경
     */
    @ApiOperation(value = "MY INFO 비밀번호 변경"
            , notes = OPERATION_CHARACTER)
    @PutMapping(name = "MY INFO 비밀번호 변경", value = "/change/password"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<UserResultDTO> changePassword (
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
            , @ApiParam(value = "유저 인증코드 DTO", required = true) @RequestBody @Validated({UserCertDTO.MyChangePassword.class}) final UserCertDTO userCertDTO
            , @ApiIgnore final BindingResult result) {
        log.info("UserMyPageController.changePassword");
        return responseService.getSingleResult(
                userService.confirmPassword(authUserDTO.getUserId(), userCertDTO)
                , SuccessCode.ConfigureSuccess.CHANGE_PASSWORD.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.CHANGE_PASSWORD.name())
                , true
        );
    }
}
