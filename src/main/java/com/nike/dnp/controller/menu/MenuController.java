package com.nike.dnp.controller.menu;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.dto.menu.MenuRoleResourceReturnDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.menu.MenuService;
import com.nike.dnp.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;


/**
 * The Class Menu controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:22:25
 * @Description
 */
@Slf4j
@RestController
@Api(description = "메뉴", tags = "MENU")
@RequestMapping(value = "/api/menu", name = "메뉴")
@RequiredArgsConstructor
public class MenuController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * The Menu service
     *
     * @author [오지훈]
     */
    private final MenuService menuService;
    private final AuthService authService;
    private final UserService userService;

    /**
     * The Basic operation
     *
     * @author [오지훈]
     */
    private final String BASIC_OPERATION = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n";

    /**
     * Find all single result.
     *
     * @return the basic menus
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 6:14:49
     * @Description 메뉴 관리 목록 조회(전체)
     */
    @ApiOperation(
            value = "메뉴 관리 목록 조회(전체)"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "메뉴 목록 조회(전체)"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Menu>> findAllMenus() {
        log.info("MenuController.findAllMenus");
        return responseService.getSingleResult(menuService.findAll());
    }

    @ApiOperation(
            value = "메뉴 관리 목록 조회(테스트1)"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "메뉴 목록 조회(테스트1)", value = "/list1"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<MenuRoleResourceReturnDTO>> getRedisResources(
            final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("MenuController.getRedisResources");
        Optional<Auth> auth = authService.findById(userService.findById(authUserDTO.getUserSeq()).get().getUserAuth().getAuthSeq());
        return responseService.getSingleResult(authService.getAuthsResourcesByRoleType(auth.get().getRoleType()));
    }

    @ApiOperation(
            value = "메뉴 관리 목록 조회(테스트2)"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "메뉴 목록 조회(테스트2)", value = "/list2"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<MenuReturnDTO>> getRedisMenus(
            final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        log.info("MenuController.getRedisMenus");
        Optional<Auth> auth = authService.findById(userService.findById(authUserDTO.getUserSeq()).get().getUserAuth().getAuthSeq());
        return responseService.getSingleResult(authService.getAuthsMenusByRoleType(auth.get().getRoleType()));
    }

}
