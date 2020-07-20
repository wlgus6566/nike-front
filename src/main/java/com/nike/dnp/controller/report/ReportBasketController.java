package com.nike.dnp.controller.report;

import com.nike.dnp.dto.auth.AuthUserDTO;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * The Class Report controller.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:37:43
 * @Description
 */
@Slf4j
@RestController
@Api(description = "리포트 장바구니", tags = "REPORT_BASKET")
@RequestMapping(value = "/api/report/basket", name = "Report bakset")
@RequiredArgsConstructor
public class ReportBasketController {

    /**
     * 응답 서비스
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The Report basket service
     * @author [이소정]
     */
    private final ReportBasketService reportBasketService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    @ApiOperation(
        value = "Report 장바구니 목록 조회"
        , notes = REQUEST_CHARACTER
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Report 장바구니 목록 조회")
    public SingleResult<List<ReportBasketResultDTO>> getAllReportBasket(
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("ReportBasketController.getAllReportBasket");
        return responseService.getSingleResult(reportBasketService.findAllReportBasket(authUserDTO));
    }

    /**
     * Save report basket single result.
     *
     * @param reportFileSeq the report file seq
     * @param authUserDTO   the auth user dto
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 17. 오후 7:07:33
     * @Description
     */
    @ApiOperation(
            value = "Report 장바구니 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "Report 장바구니 등록")
    public SingleResult<List<ReportBasket>> saveReportBasket(
            @RequestBody final List<Long> reportFileSeq,
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("ReportBasketController.saveReportBasket");
        List<ReportBasket> reportBasketList = reportBasketService.save(reportFileSeq, authUserDTO);
        return responseService.getSingleResult(reportBasketList);
    }

    /**
     * Delete report basket single result.
     *
     * @param reportBasketSeq the report basket seq
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 7. 17. 오후 7:07:35
     * @Description
     */
    @ApiOperation(value="Report 장바구니 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "Report 장바구니 삭제", value = "/{reportBasketSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<ReportBasket> deleteReportBasket(
            @ApiParam(name = "reportBasketSeq", value = "Report 장바구니 시퀀스", defaultValue = "2", required = true) @PathVariable final Long reportBasketSeq) {
        log.info("ReportBasketController.deleteReportBasket");
        return responseService.getSingleResult(reportBasketService.delete(reportBasketSeq));
    }

}

