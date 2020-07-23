package com.nike.dnp.controller.menu;

import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.auth.AuthService;
import com.nike.dnp.service.menu.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
     * Find all single result.
     *
     * @return the basic menus
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 6:14:49
     * @Description 메뉴 전체 목록 조회
     */
    @ApiOperation(
            value = "메뉴 전체 목록 조회"
            , notes = OPERATION_CHARACTER
    )
    @GetMapping(name = "메뉴 전체 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Menu>> findAllMenus() {
        log.info("MenuController.findAllMenus");
        return responseService.getSingleResult(menuService.findAll());
    }

    /**
     * Find menus single result.
     *
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 16. 오후 4:43:52
     * @Description 메뉴 관리 목록 조회
     */
    @ApiOperation(
            value = "메뉴 관리 목록 조회"
            , notes = OPERATION_CHARACTER
    )
    @GetMapping(name = "메뉴 관리 목록 조회", value = "/manage"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Menu>> findMenus() {
        log.info("MenuController.findMenus");
        return responseService.getSingleResult(menuService.findMenus());
    }

}
