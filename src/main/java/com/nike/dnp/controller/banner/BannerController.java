package com.nike.dnp.controller.banner;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.banner.BannerUpdateDTO;
import com.nike.dnp.entity.banner.Banner;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * The Class Banner controller.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 5:22:14
 * @Description 메인 비주얼 컨트롤러 작성
 */
@Slf4j
@RestController
@Api(description = "메인비주얼", tags = "BANNER")
@RequestMapping(value = "/api/banner", name = "메인비주얼")
@RequiredArgsConstructor
public class BannerController {

    /**
     * The Response service
     *
     * @author [오지훈]
     */
    private final ResponseService responseService;

    /**
     * BannerService
     *
     * @author [오지훈]
     */
    private final BannerService bannerService;

    /**
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String OPERATION_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    /**
     * Gets banner.
     *
     * @return the banner
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오전 11:41:23
     * @Description 메인 비주얼 상세
     */
    @ApiOperation(
            value = "메인 비주얼 상세"
            , notes = OPERATION_CHARACTER
    )
    @GetMapping(name = "메인 비주얼 상세"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Banner> getBanner() {
        log.info("BannerController.getBanner");
        return responseService.getSingleResult(bannerService.getBanner());
    }

    /**
     * Save single result.
     *
     * @param bannerUpdateDTO the banner update dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오전 11:43:25
     * @Description 메인 비주얼 등록
     */
    @ApiOperation(
            value = "메인 비주얼 등록"
            , notes = OPERATION_CHARACTER
    )
    @PostMapping(name = "메인 비주얼 등록"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Banner> save(
            @ApiParam(value = "메인 비주얼 등록 DTO", required = true) @RequestBody final BannerUpdateDTO bannerUpdateDTO
    ) {
        log.info("BannerController.save");
        return responseService.getSingleResult(
                bannerService.save(bannerUpdateDTO)
                , ServiceEnumCode.ReturnTypeEnumCode.CREATE.toString()
                , ServiceEnumCode.ReturnTypeEnumCode.CREATE.getMessage()
                , true
        );
    }

    /**
     * Update single result.
     *
     * @param bannerSeq       the banner seq
     * @param bannerUpdateDTO the banner update dto
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오전 11:43:23
     * @Description 메인 비주얼 수정
     */
    @ApiOperation(
            value = "메인 비주얼 수정"
            , notes = OPERATION_CHARACTER
    )
    @PutMapping(name = "메인 비주얼 수정"
            , value = "/{bannerSeq}"
            , consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Banner> update(
            @ApiParam(value = "배너 시퀀스", required = true) @PathVariable final Long bannerSeq
            , @ApiParam(value = "메인 비주얼 수정 DTO", required = true) @RequestBody final BannerUpdateDTO bannerUpdateDTO
    ) {
        log.info("BannerController.update");
        return responseService.getSingleResult(
                bannerService.update(bannerSeq, bannerUpdateDTO)
                , ServiceEnumCode.ReturnTypeEnumCode.UPDATE.toString()
                , ServiceEnumCode.ReturnTypeEnumCode.UPDATE.getMessage()
                , true
        );
    }

}
