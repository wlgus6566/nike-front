package com.nike.dnp.controller.report;

import com.nike.dnp.dto.report.ReportBasketResultDTO;
import com.nike.dnp.entity.report.ReportBasket;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.report.ReportBasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The Class Report controller.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니
 * @since 2020. 7. 7. 오후 2:37:43
 */
@Slf4j
@RestController
@Api(description = "리포트 장바구니", tags = "REPORT_BASKET")
@RequestMapping(value = "/api/report/basket", name = "Report bakset")
@RequiredArgsConstructor
public class ReportBasketController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The Report basket service
     *
     * @author [이소정]
     */
    private final ReportBasketService reportBasketService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * Find all report basket single result.
     *
     * @return the single result
     * @author [이소정]
     * @implNote 보고서 장바구니 목록 조회
     * @since 2020. 7. 29. 오후 6:45:27
     */
    @ApiOperation(
        value = "Report 장바구니 목록 조회"
        , notes = REQUEST_CHARACTER
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Report 장바구니 목록 조회")
    public SingleResult<List<ReportBasketResultDTO>> findAllReportBasket() {
        log.info("ReportBasketController.findAllReportBasket");
        return responseService.getSingleResult(reportBasketService.findAllReportBasket());
    }

    /**
     * Save report basket single result.
     *
     * @param reportFileSeq the report file seq
     * @return the single result
     * @author [이소정]
     * @implNote Report 장바구니 등록
     * @since 2020. 7. 17. 오후 7:07:33
     */
    @ApiOperation(
            value = "Report 장바구니 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Report 장바구니 등록")
    public SingleResult<List<ReportBasket>> saveReportBasket(
            @RequestBody final List<Long> reportFileSeq
    ) {
        log.info("ReportBasketController.saveReportBasket");
        return responseService.getSingleResult(reportBasketService.save(reportFileSeq));
    }

    /**
     * Delete report basket single result.
     *
     * @param reportBasketSeq the report basket seq
     * @return the single result
     * @author [이소정]
     * @implNote Report 장바구니 삭제
     * @since 2020. 7. 17. 오후 7:07:35
     */
    @ApiOperation(value="Report 장바구니 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "Report 장바구니 삭제", value = "/{reportBasketSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<ReportBasket> deleteReportBasket(
            @ApiParam(name = "reportBasketSeq", value = "Report 장바구니 시퀀스", defaultValue = "2", required = true) @PathVariable final Long reportBasketSeq
    ) {
        log.info("ReportBasketController.deleteReportBasket");
        return responseService.getSingleResult(reportBasketService.delete(reportBasketSeq));
    }

}

