package com.nike.dnp.controller;

import com.nike.dnp.dto.manage.manager.ManagerSaveDTO;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.dto.manage.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ErrorSampleService;
import com.nike.dnp.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * ErrorSample Controller
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description ErrorSample Controller 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */
@Slf4j
@RestController
@Api(description = "ErrorSample", tags = "99_Error")
@RequestMapping(value = "/api/error", name = "ErrorSample")
public class ErrorSampleController {

    /**
     * 응답 서비스
     */
    private final ResponseService responseService;

    /**
     * 사용자 서비스
     */
    private final ErrorSampleService errorSampleService;

    public ErrorSampleController(
            ResponseService responseService
            , ErrorSampleService errorSampleService
    ) {
        this.responseService = responseService;
        this.errorSampleService = errorSampleService;
    }


    /**
     * ErrorSample 목록 조회
     *
     * @param managerSearchDTO the manager search dto
     * @return all managers
     */
    @ApiOperation(
            value = "ErrorSample 목록 조회"
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "ErrorSample 목록 조회")
    public SingleResult<Page<Manager>> getAllManagers(ManagerSearchDTO managerSearchDTO) {
        return responseService.getSingleResult(errorSampleService.findAllPaging(managerSearchDTO));
    }

    /**
     * ErrorSample 상세정보 조회
     *
     * @param managerSeq the manager seq
     * @return the manager
     */
    @ApiOperation(
            value = "ErrorSample 상세 조회"
    )
    @GetMapping(value = "/{managerSeq}", name = "ErrorSample 상세 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Manager> getManager(@PathVariable(name = "managerSeq") Long managerSeq) {
        return errorSampleService.findById(managerSeq);
    }

    /**
     * 사용자 삭제
     *
     * @param managerSeq the manager seq
     * @return the response entity
     */
    @ApiOperation(
            value = "ErrorSample 삭제"
            , notes =
            "## Reqeust ## \n"
                    + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
                    + "managerSeq|사용자시퀀스|true|Long\n"
                    + "\n\n\n"
    )
    @DeleteMapping(value = "/{managerSeq}", name = "ErrorSample 삭제"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResult deleteManager(@PathVariable(name = "managerSeq") Long managerSeq) {
        errorSampleService.delete(managerSeq);
        return responseService.getSuccessResult();
    }

    /**
     * ErrorSample 수정
     *
     * @param managerSeq       the manager seq
     * @param managerUpdateDTO the manager update dto
     * @return the response entity
     */
    @ApiOperation(
            value = "ErrorSample 수정"
            , notes =
            "## Reqeust ## \n"
                    + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
                    + "managerSeq|사용자시퀀스|true|Long\n"
                    + "\n\n\n"
                    + "## Response ## \n"
                    + "필드명||필드설명|데이터 타입(길이)\n" + "-|-|-|-\n"

                    + "\n\n\n"
    )
    @PutMapping(value = "/{managerSeq}", name = "ErrorSample 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Manager> updateManager(
            @PathVariable(name = "managerSeq") Long managerSeq
            ,@RequestBody ManagerUpdateDTO managerUpdateDTO) {
        return errorSampleService.update(managerSeq, managerUpdateDTO);
    }

    /**
     * 사용자 등록
     *
     * @param managerSaveDTO the manager dto
     * @return the response entity
     */
    @ApiOperation(
            value = "ErrorSample 등록"
            , notes =
            "## Reqeust ## \n"
                    + "[하위 Parameters 참조]\n"
                    + "\n\n\n"
                    + "## Response ## \n"
                    + "[하위 Model 참조]\n"
                    + "\n\n\n"
    )
    @PostMapping(name = "ErrorSample 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Manager> insertManager(@RequestBody ManagerSaveDTO managerSaveDTO) {
        return errorSampleService.save(managerSaveDTO);
    }


}
