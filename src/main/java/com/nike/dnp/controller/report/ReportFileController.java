package com.nike.dnp.controller.report;

import com.nike.dnp.dto.report.ReportFileResultDTO;
import com.nike.dnp.dto.report.ReportFileSearchDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.report.ReportFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@Api(description = "리포트", tags = "REPORT")
@RequestMapping(value = "/api/report/file", name = "Report 파일")
@RequiredArgsConstructor
public class ReportFileController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The Report file service
     *
     * @author [이소정]
     */
    private final ReportFileService reportFileService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";


    /**
     * Find all contents file single result.
     *
     * @param reportFileSearchDTO the report file search dto
     * @return the single result
     * @author [이소정]
     * @implNote 보고서 파일 목록 조회
     * @since 2020. 8. 13. 오후 7:48:58
     */
    @ApiOperation(
        value = "보고서 파일 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "보고서 파일 목록 조회", value = "/{reportSeq}")
    public SingleResult<Page<ReportFileResultDTO>> findAllContentsFile(
            @ApiParam(name = "reportSeq", value = "보고서 시퀀스", defaultValue = "4", required = true) @PathVariable final Long reportSeq,
            final ReportFileSearchDTO reportFileSearchDTO
    ) {
        return responseService.getSingleResult(reportFileService.findAllReportFilePaging(reportFileSearchDTO));
    }

}

