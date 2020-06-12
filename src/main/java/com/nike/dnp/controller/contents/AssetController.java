package com.nike.dnp.controller.contents;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.example.Manager;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.contents.ContentsService;
import com.nike.dnp.service.example.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 콘텐츠 > Asset Controller
 *
 * @since 2020.06.11
 * @author [이소정]
 * @Description 콘텐츠 > Asset 컨트롤러 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 *
 */

@Slf4j
@RestController
@Api(description = "콘텐츠", tags = "2_CONTENTS")
@RequestMapping(value = "/api/contents/asset", name = "콘텐츠 > Asset")
@RequiredArgsConstructor
public class AssetController {

    /**
     * 응답 서비스
     */
    private final ResponseService responseService;

    /**
     * 콘텐츠 서비스
     */
    private final ContentsService contentsService;

    /**
     *
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|ex\n" + "-|-|-|-|-\n";

    /**
     * 콘텐츠>에셋 전체목록 조회
     *
     * @param contentsSearchDTO the contents search dto
     * @param authUserDTO       the auth user dto
     * @return all managers
     */
    @ApiOperation(
        value = "콘텐츠>에셋 목록 조회"
        , notes = REQUEST_CHARACTER
        + "page|페이지|false|Integer\n"
        + "size|사이즈|false|Integer\n"
        + "keyword|검색어|false|String\n"
        + "orderType|정렬 타입|false|String|최신순:LATEST/시작일 순:START_DATE\n"
        + "topMenuSeq|최고 메뉴 시퀀스|false|String\n"
        + "menuSeq|메뉴 시퀀스|false|String\n"
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
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "사용자 목록 조회")
    public SingleResult<Page<Contents>> getAllManagers(
            final ContentsSearchDTO contentsSearchDTO
            , final @ApiIgnore @AuthenticationPrincipal AuthUserDTO authUserDTO
    ) {
        return responseService.getSingleResult(contentsService.findAllPaging(contentsSearchDTO));
    }


}
