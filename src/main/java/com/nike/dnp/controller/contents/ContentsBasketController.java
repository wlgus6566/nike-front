package com.nike.dnp.controller.contents;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import com.nike.dnp.entity.contents.ContentsBasket;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.contents.ContentsBasketService;
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
import java.util.Optional;

/**
 * The Class Contents basket controller.
 *
 * @author [이소정]
 * @since 2020. 7. 14. 오후 4:50:17
 * @apiNote
 */
@Slf4j
@RestController
@Api(description = "컨텐츠 장바구니", tags = "CONTENTS_BASKET")
@RequestMapping(value = "/api/contents/basket", name = "컨텐츠 장바구니")
@RequiredArgsConstructor
public class ContentsBasketController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * 컨텐츠 서비스
     *
     * @author [이소정]
     */
    private final ContentsBasketService contentsBasketService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * Gets all contents basket.
     *
     * @param authUserDTO the auth user dto
     * @return the all contents basket
     * @author [이소정]
     * @since 2020. 7. 15. 오후 12:16:20
     * @apiNote
     */
    @ApiOperation(
            value = "컨텐츠 장바구니 목록 조회"
            , notes = REQUEST_CHARACTER
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "컨텐츠 장바구니 목록 조회")
    public SingleResult<List<ContentsBasketResultDTO>> findAllContentsBasket(
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
            ) {
        return responseService.getSingleResult(contentsBasketService.findAllContentsBasket(authUserDTO));
    }

    /**
     * Save contents single result.
     *
     * @param contentsBasketSeqList the contents basket seq list
     * @param authUserDTO           the auth user dto
     * @return the single result
     * @author [이소정]
     * @since 2020. 7. 15. 오후 12:16:18
     * @apiNote
     */
    @ApiOperation(
            value = "컨텐츠 장바구니 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "컨텐츠 장바구니 등록", value = "/{topMenuCode}/{menuCode}")
    public SingleResult<List<ContentsBasket>> saveContentsBasket(
            @ApiParam(name = "topMenuCode", value = "상위 메뉴", defaultValue = "ASSET", required = true) @PathVariable final String topMenuCode,
            @ApiParam(name = "menuCode", value = "파일구분(2depth menu)", defaultValue = "SP", required = true) @PathVariable final String menuCode,
            @RequestBody final List<Long> contentsBasketSeqList,
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        List<ContentsBasket> contentsBasketList = contentsBasketService.save(contentsBasketSeqList, authUserDTO);
        return responseService.getSingleResult(contentsBasketList);
    }

    /**
     * Delete contents single result.
     *
     * @param contentsBasketSeq the contents basket seq
     * @return the single result
     * @author [이소정]
     * @since 2020. 7. 15. 오후 2:41:27
     * @apiNote
     */
    @ApiOperation(value="컨텐츠 장바구니 삭제", notes = REQUEST_CHARACTER)
    @DeleteMapping(name = "컨텐츠 장바구니 삭제", value = "/{contentsBasketSeq}"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<ContentsBasket> deleteContentsBasket(
            @ApiParam(name = "contentsBasketSeq", value = "컨텐츠 장바구니 시퀀스", defaultValue = "2", required = true) @PathVariable final Long contentsBasketSeq) {
        log.info("ContentsController.delete");
        return responseService.getSingleResult(contentsBasketService.delete(contentsBasketSeq));
    }


}

