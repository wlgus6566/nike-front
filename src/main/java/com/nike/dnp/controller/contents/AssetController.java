package com.nike.dnp.controller.contents;

import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.contents.ContentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * 콘텐츠 > Asset Controller
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 19. 오후 5:56:03
 * @Description 콘텐츠 > Asset 컨트롤러 작성
  */
@Slf4j
@RestController
@Api(description = "콘텐츠", tags = "10_CONTENTS")
@RequestMapping(value = "/api/contents/asset", name = "콘텐츠 > Asset")
@RequiredArgsConstructor
public class AssetController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * 콘텐츠 서비스
     *
     * @author [이소정]
     */
    private final ContentsService contentsService;

    @Autowired
    MessageSource messageSource;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * 콘텐츠>에셋 전체목록 조회
     *
     * @param contentsSearchDTO the contents search dto
     * @param authUserDTO       the auth user dto
     * @return all managers
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:56:03
     * @Description
     */
    @ApiOperation(
        value = "콘텐츠 > Asset 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer|0부터 시작\n"
        + "size|사이즈|false|Integer\n"
        + "keyword|검색어|false|String\n"
        + "orderType|정렬 타입|false|String|최신순:LATEST/시작일 순:START_DATE\n"
        + "menuCode|2Depth 메뉴 코드|false|String\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "콘텐츠 > Asset 목록 조회")
    public SingleResult<Page<Contents>> getAllContents(
            final ContentsSearchDTO contentsSearchDTO
    ) {
        System.out.println("==========================");
        System.out.println(messageSource.getMessage("greeting", null, Locale.getDefault()));

        // Asset 메뉴 코드 넣어줌.
        contentsSearchDTO.setTopMenuCode("ASSET");
        return responseService.getSingleResult(contentsService.findAllPaging(contentsSearchDTO));
    }


    /**
     * Save contents single result.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the single result
     * @author [이소정]
     * @CreatedOn 2020. 6. 24. 오후 3:45:44
     * @Description
     */
    @ApiOperation(
            value = "콘텐츠 > Asset 등록"
            , notes = REQUEST_CHARACTER
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "콘텐츠 > Asset 등록")
    public SingleResult<Contents> saveContents(
            @RequestBody final ContentsSaveDTO contentsSaveDTO
    ) {
        contentsSaveDTO.setTopMenuCode("ASSET");
        Contents contents = contentsService.save(contentsSaveDTO);
        return responseService.getSingleResult(contents);
    }

}
