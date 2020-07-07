package com.nike.dnp.controller.menu;

import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
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
     * The Basic operation
     *
     * @author [오지훈]
     */
    private final String BASIC_OPERATION = "## Reqeust ##\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n";

    @ApiOperation(
            value = "메뉴 관리 목록 조회"
            , notes = BASIC_OPERATION
    )
    @GetMapping(name = "메뉴 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Menu>> findAll() {
        log.info("MenuController.findAll");
        return responseService.getSingleResult(menuService.findAll());
    }

}
