package com.nike.dnp.controller.example;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.example.manager.ManagerSaveDTO;
import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.dto.example.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.example.Manager;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.example.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

/**
 * ManagerController
 *
 * @since 2020.05.27
 * @author [오지훈]
 * @Description 사용자 컨트롤러 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@Api(description = "관리 정보", tags = "1_MANAGE")
@RequestMapping(value = "/api/manage/user", name = "사용자")
@RequiredArgsConstructor
public class ManagerController {

    /**
     * 응답 서비스
     */
    private final ResponseService responseService;

    /**
     * 사용자 서비스
     */
    private final ManagerService managerService;

    /**
     *
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * 사용자 전체목록 조회
     *
     * @param managerSearchDTO the manager search dto
     * @return all managers
     */
    @ApiOperation(
        value = "사용자 목록 조회"
        , notes = REQUEST_CHARACTER
        + "keyword|검색어|false|String\n"
        + "page|페이지|false|Integer\n"
        + "size|사이즈|false|Integer\n"
        + "[하위 Parameters 참조]\n\n\n\n"
        + "## Public/Paging Response ## \n"
        + "필드명||필드설명|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "content||본문내용|Array\n"
        + "totalPages||총페이지수|Integer\n"
        + "totalElements||총데이터수|Integer\n"
        + "first||첫페이지여부|Boolean\n"
        + "last||마지막페이지여부|Boolean\n"
        + "empty||빈값여부|Boolean\n"
        + "number||현재페이지|Integer\n"
        + "size||노출갯수|Integer\n\n\n\n"
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "사용자 목록 조회")
    public SingleResult<Page<Manager>> getAllManagers(
            final ManagerSearchDTO managerSearchDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(managerService.findAllPaging(managerSearchDTO));
    }

    /**
     * 사용자 상세정보 조회
     *
     * @param managerSeq the manager seq
     * @return the manager
     */
    @ApiOperation(
        value = "사용자 상세 조회"
        , notes = REQUEST_CHARACTER + "\n"
        + "managerSeq|사용자시퀀스|true|Long\n\n\n\n"
        + "## Response ## \n"
        + "[하위 Model 참조]\n"
    )
    @GetMapping(value = "/{managerSeq}", name = "사용자 상세 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Manager> getManager(
            final @PathVariable(name = "managerSeq") Long managerSeq
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(
                managerService.findById(managerSeq));
    }

    /**
     * 사용자 삭제
     *
     * @param managerSeq the manager seq
     * @return the response entity
     */
    @ApiOperation(
        value = "사용자 삭제"
        , notes = REQUEST_CHARACTER + "\n"
        + "managerSeq|사용자시퀀스|true|Long\n\n\n\n"
    )
    @DeleteMapping(value = "/{managerSeq}", name = "사용자 삭제"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResult deleteManager(final @PathVariable(name = "managerSeq") Long managerSeq) {
        managerService.delete(managerSeq);
        return responseService.getSuccessResult();
    }

    /**
     * 사용자 수정
     *
     * @param managerSeq       the manager seq
     * @param managerUpdateDTO the manager update dto
     * @return the response entity
     */
    @ApiOperation(
        value = "사용자 수정"
        , notes = REQUEST_CHARACTER + "\n"
        + "managerSeq|사용자시퀀스|true|Long\n\n\n\n"
        + "## Response ## \n"
        + "필드명||필드설명|데이터 타입(길이)\n" + "-|-|-|-\n\n\n\n"
    )
    @PutMapping(value = "/{managerSeq}", name = "사용자 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Manager>> updateManager(
            final @PathVariable(name = "managerSeq") Long managerSeq
            , final @RequestBody ManagerUpdateDTO managerUpdateDTO
    ) {
        return responseService.getSingleResult(managerService.update(managerSeq, managerUpdateDTO));
    }

    /**
     * 사용자 등록
     *
     * @param managerSaveDTO the manager dto
     * @return the response entity
     */
    @ApiOperation(
        value = "사용자 등록"
        , notes =
        "## Reqeust ## \n"
        + "[하위 Parameters 참조]\n\n\n\n"
        + "## Response ## \n"
        + "[하위 Model 참조]\n\n\n\n"
    )
    @PostMapping(name = "사용자 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Manager> insertManager(final @RequestBody ManagerSaveDTO managerSaveDTO) {
        return responseService.getSingleResult(
                managerService.save(
                        managerSaveDTO.getManagerId()
                        , managerSaveDTO.getManagerName()
                        , managerSaveDTO.getPassword()
                        , managerSaveDTO.getAuthSeq()
                        , 0
        ));
    }

}
