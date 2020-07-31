package com.nike.dnp.controller.main;

import com.nike.dnp.dto.main.MainResultDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.main.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Main controller.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 27. 오후 5:04:19
 * @Description
 */
@Slf4j
@RestController
@Api(description = "메인", tags = "MAIN")
@RequestMapping(value = "/api/main", name = "메인")
@RequiredArgsConstructor
public class MainController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    private final MainService mainService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";


    /**
     * Find main info single result.
     *
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 27. 오후 5:38:53
     * @Description
     */
    @ApiOperation(
        value = "메인 조회(calendar 제외)"
        , notes = REQUEST_CHARACTER
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "메인 조회(calendar 제외)")
    public SingleResult<MainResultDTO> findMainInfo() {
        return responseService.getSingleResult(mainService.findMainInfo());
    }



}
