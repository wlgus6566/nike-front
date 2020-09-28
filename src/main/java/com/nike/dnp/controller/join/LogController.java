package com.nike.dnp.controller.join;

import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.common.variable.SuccessCode;
import com.nike.dnp.dto.log.DownloadLogSaveDTO;
import com.nike.dnp.entity.log.DownloadLog;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.DownloadLogService;
import com.nike.dnp.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;


/**
 * The type Log controller.
 *
 * @author [오지훈]
 * @implNote log 관련
 * @since 2020. 9. 28. 오전 9:24:50
 */
@Slf4j
@RestController
@Api(description = "로그 관련", tags = "LOG")
@RequestMapping(value = "/api/log", name = "로그 관련")
@RequiredArgsConstructor
public class LogController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * DownloadLogService
     *
     * @author [오지훈]
     */
    private final DownloadLogService downloadLogService;

    /**
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    /**
     * 다운로드 로그 기록
     *
     * @param downloadLogSaveDTO the download log save dto
     * @param result             the result
     * @return the single result
     * @author [오지훈]
     * @implNote 다운로드 로그 기록
     * @since 2020. 8. 26. 오후 3:16:18
     */
    @ApiOperation(value = "다운로드 로그 기록", notes = OPERATION_CHARACTER)
    @PostMapping(value = "/download", name = "다운로드 로그 기록")
    @ValidField
    public SingleResult<List<DownloadLog>> downloadLog(
            @ApiParam(value = "다운로드 로그 기록", required = true) @Valid @RequestBody DownloadLogSaveDTO downloadLogSaveDTO
            , @ApiIgnore final BindingResult result
    ) {
        log.info("LogController.downloadLog");
        return responseService.getSingleResult(
                downloadLogService.save(downloadLogSaveDTO)
                , SuccessCode.ConfigureSuccess.SUCCESS.name()
                , MessageUtil.getMessage(SuccessCode.ConfigureSuccess.SUCCESS.name())
                , false
        );
    }

}
