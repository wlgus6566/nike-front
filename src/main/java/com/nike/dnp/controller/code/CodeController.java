package com.nike.dnp.controller.code;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.code.CodeSaveDTO;
import com.nike.dnp.dto.code.CodeUpdateDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.code.CodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * CodeController
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 5:22:30
 * @apiNote Code(공통 코드) Controller 작성
 */
@Slf4j
@RestController
@Api(description = "코드 정보", tags = "CODE")
@RequestMapping(value = "/api/open/code", name = "코드")
@RequiredArgsConstructor
@CrossOrigin
public class CodeController {

    /**
     * ResponseService
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * CodeService
     *
     * @author [오지훈]
     */
    private final CodeService codeService;

    /**
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    /**
     * 하위 코드 목록
     *
     * @param upperCode the upper code
     * @return the conf codes
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 5:22:31
     * @apiNote
     */
    @ApiOperation(value = "하위 코드 목록 조회", notes = OPERATION_CHARACTER)
    @GetMapping(value = "/{upperCode}", name = "하위 코드 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Code>> getConfCodes(
            @ApiParam(name = "upperCode", value = "상위 코드", required = true) @PathVariable final String upperCode) {
        return responseService.getSingleResult(codeService.findCodesByUpperCode(upperCode));
    }

    /**
     * 코드 등록
     *
     * @param codeSaveDTO the code save dto
     * @param result      the result
     * @return the response entity
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 5:22:31
     * @apiNote
     */
    @ApiOperation(value = "코드 등록", notes = OPERATION_CHARACTER)
    @PostMapping(name = "코드 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Code> saveCode(
            @ApiParam(value = "코드 저장 DTO", name = "codeSaveDTO") @Valid @RequestBody final CodeSaveDTO codeSaveDTO
            , @ApiIgnore final BindingResult result) {
        return responseService.getSingleResult(codeService.save(codeSaveDTO));
    }

    /**
     * 코드 수정
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @param result        the result
     * @return the response entity
     * @author [오지훈]
     * @since 2020. 6. 22. 오후 5:22:31
     * @apiNote
     */
    @ApiOperation(value = "코드 수정", notes = OPERATION_CHARACTER)
    @PutMapping(value = "/{code}", name = "코드 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<Optional<Code>> updateCode(
            @ApiParam(name = "code", value = "코드", required = true) @PathVariable final String code
            , @ApiParam(value = "코드 수정 DTO", name = "codeUpdateDTO") @Valid @RequestBody final CodeUpdateDTO codeUpdateDTO
            , @ApiIgnore final BindingResult result) {
        return responseService.getSingleResult(codeService.update(code, codeUpdateDTO));
    }

}
