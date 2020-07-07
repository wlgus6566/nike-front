package com.nike.dnp.controller.code;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CodeController
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 5:22:30
 * @Description Code(공통 코드) Controller 작성
 */
@Slf4j
@RestController
@Api(description = "코드 정보", tags = "CODE")
@RequestMapping(value = "/api/code", name = "코드")
@RequiredArgsConstructor
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
     * REQUEST_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n";

    /**
     * 하위 코드 목록
     *
     * @param upperCode the upper code
     * @return the conf codes
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
        value = "하위 코드 목록 조회"
        , notes = REQUEST_CHARACTER + "\n"
        + "[하위 Parameters 참조]\n\n\n\n"
        + "## Response ## \n"
        + "[하위 Model 참조]\n\n\n\n"
    )
    @GetMapping(value = "/codes/{upperCode}", name = "하위 코드 목록 조회"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<List<Code>> getConfCodes(
            @ApiParam(name = "upperCode", value = "상위 코드", required = true)
            @PathVariable final String upperCode) {
        return responseService.getSingleResult(codeService.findCodesByUpperCode(upperCode));
    }

    /**
     * 코드 등록
     *
     * @param codeSaveDTO the code save dto
     * @return the response entity
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
        value = "코드 등록"
        , notes = REQUEST_CHARACTER + "\n"
        + "[하위 Parameters 참조]\n\n\n\n"
        + "## Response ## \n"
        + "[하위 Model 참조]\n\n\n\n"
    )
    @PostMapping(name = "코드 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Code> saveCode(@ApiParam(value = "코드 저장 DTO") @RequestBody final CodeSaveDTO codeSaveDTO) {
        return responseService.getSingleResult(codeService.save(codeSaveDTO));
    }

    /**
     * 코드 수정
     *
     * @param code          the code
     * @param codeUpdateDTO the code update dto
     * @return the response entity
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
            value = "코드 수정"
            , notes = REQUEST_CHARACTER + "\n"
            + "codeCd|코드|true|String\n"
            + "[하위 Parameters 참조]\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @PutMapping(value = "/{code}", name = "코드 수정"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Code>> updateCode(
            @ApiParam(name = "code", value = "코드", required = true)
            @PathVariable final String code
            , @ApiParam(value = "코드 수정 DTO", required = true)
            @RequestBody final CodeUpdateDTO codeUpdateDTO
    ) {
        Optional<Code> codeEntity = codeService.update(code, codeUpdateDTO);
        return responseService.getSingleResult(codeEntity);
    }

    /**
     * 코드 삭제
     *
     * @param code the code
     * @return the response entity
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 5:22:31
     * @Description
     */
    @ApiOperation(
            value = "코드 삭제"
            , notes = "## Reqeust ## \n"
            + "codeCd|코드|true|String\n\n\n\n"
            + "## Response ## \n"
            + "[하위 Model 참조]\n\n\n\n"
    )
    @DeleteMapping(value = "/{code}", name = "코드 삭제"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<Code>> deleteCode(
            @ApiParam(name = "code", value = "코드", required = true)
            @PathVariable final String code
    ) {
        return responseService.getSingleResult(codeService.delete(code));
    }
}
