package com.nike.dnp.controller.report;

import com.nike.dnp.dto.report.ReportAnswerSaveDTO;
import com.nike.dnp.entity.report.ReportAnswer;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.report.ReportAnswerService;
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
 * The Class Report answer controller.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 10. 오전 11:07:25
 * @Description
 */
@Slf4j
@RestController
@Api(description = "리포트", tags = "REPORT")
@RequestMapping(value = "/api/report/answer", name = "Report")
@RequiredArgsConstructor
public class ReportAnswerController {

    /**
     * 응답 서비스
     * @author [이소정]
     */
    private final ResponseService responseService;


    /**
     * The Report service
     * @author [이소정]
     */
    private final ReportAnswerService reportAnswerService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * Gets all reports.
     *
     * @param reportSeq the report seq
     * @return the all reports
     * @author [이소정]
     * @CreatedOn 2020. 7. 10. 오후 6:50:40
     * @Description
     */
    @ApiOperation(
            value = "보고서 댓글 목록 조회"
            , notes = REQUEST_CHARACTER
            + "reportSeq|사이즈|false|Integer\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "보고서 댓글 목록 조회")
    public SingleResult<List<ReportAnswer>> getAllReportAnswer(
            final Long reportSeq
    ) {
        log.info("ReportAnswerController.getAllReportAnswer");
        return responseService.getSingleResult(reportAnswerService.findAll(reportSeq));
    }



    /**
     * Save report answer single result.
     *
     * @param answerSaveDTO the answer save dto
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 10. 오후 6:39:54
     * @Description
     */
    @ApiOperation(
            value = "보고서 댓글 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "보고서 댓글 등록")
    public SingleResult<ReportAnswer> saveReportAnswer(
            @RequestBody final ReportAnswerSaveDTO answerSaveDTO
    ) {
        log.info("ReportAnswerController.saveReportAnswer");
        ReportAnswer reportAnswer = reportAnswerService.save(answerSaveDTO);
        return responseService.getSingleResult(reportAnswer);
    }

    /**
     * Delete report single result.
     *
     * @param answerSeq the answer seq
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 10. 오후 6:39:49
     * @Description
     */
    @ApiOperation(value="보고서 댓글 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "보고서 댓글 삭제", value = "/{answerSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Optional<ReportAnswer>> deleteReport(
            @ApiParam(name = "answerSeq", value = "보고서 댓글 시퀀스", defaultValue = "1") @PathVariable final Long answerSeq) {
        log.info("ReportController.deleteReport");
        return responseService.getSingleResult(reportAnswerService.delete(answerSeq));
    }

}

