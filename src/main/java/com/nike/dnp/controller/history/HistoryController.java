package com.nike.dnp.controller.history;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.history.HistoryFolderCountResultDTO;
import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.history.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * The Class History controller.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오전 10:03:18
 */
@Slf4j
@RestController
@Api(description = "마이페이지", tags = "MYPAGE")
@RequestMapping(value = "/api/mypage", name = "마이페이지")
@RequiredArgsConstructor
public class HistoryController {

    /**
     * 응답 서비스
     *
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * The History service
     *
     * @author [이소정]
     */
    private final HistoryService historyService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [이소정]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * Find all upload folder single result.
     *
     * @param historySearchDTO the history search dto
     * @param authUserDTO      the auth user dto
     * @return the single result
     * @author [이소정]
     * @implNote 최근 업로드한 폴더 목록
     * @since 2020. 7. 31. 오전 10:03:22
     */
    @ApiOperation(
            value = "최근 업로드한 폴더 목록"
            , notes = REQUEST_CHARACTER
            + "page|페이지|false|Integer|0부터 시작\n"
            + "size|사이즈|false|Integer\n"
            + "typeCd|타입 코드(부모코드 : HISTORY_FOLDER_TAB)|false|String|ALL/ASSET/TOOLKIT/FOUNDATION/REPORT_MANAGE\n"
            + "mobileYn|모바일api 여부|false|String|Y/N\n"
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
    @GetMapping(name = "최근 업로드한 폴더 목록", value = "/history/uploadList"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<HistoryResultDTO>> findAllUploadFolder(
            final HistorySearchDTO historySearchDTO,
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("HistoryController.findAllUploadFolder");
        return responseService.getSingleResult(historyService.findAllUploadHistoryPaging(historySearchDTO, authUserDTO));
    }

    /**
     * Find all view folder single result.
     *
     * @param historySearchDTO the history search dto
     * @param authUserDTO      the auth user dto
     * @return the single result
     * @author [이소정]
     * @implNote 최근 본 폴더 목록
     * @since 2020. 7. 31. 오전 10:03:25
     */
    @ApiOperation(
            value = "최근 본 폴더 목록"
            , notes = REQUEST_CHARACTER
            + "page|페이지|false|Integer|0부터 시작\n"
            + "size|사이즈|false|Integer\n"
            + "typeCd|타입 코드(값 없을 경우 ALL)|false|String|ALL/ASSET/TOOLKIT/FOUNDATION/REPORT_MANAGE\n"
            + "mobileYn|모바일api 여부|false|String|Y/N\n"
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
    @GetMapping(name = "최근 본 폴더 목록", value = "/history/viewList"
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<HistoryResultDTO>> findAllViewFolder(
            final HistorySearchDTO historySearchDTO,
            @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO
    ) {
        log.info("HistoryController.findAllHistoryViewFolder");
        historySearchDTO.setRegisterSeq(authUserDTO.getUserSeq());
        return responseService.getSingleResult(historyService.findAllViewHistoryPaging(historySearchDTO, authUserDTO));
    }


    @ApiOperation(
            value = "최근 본 / 업로드 파일 갯수 조회"
            , notes = REQUEST_CHARACTER
            + "menuCode|상위메뉴|true|String|viewFolder/uploadFolder\n"
    )
    @GetMapping(name="최근 본 / 업로드 파일 갯수 조회" , value="/history/count/{menuCode}")
    public SingleResult<List<HistoryFolderCountResultDTO>> countHistoryFolder(
           final HistorySearchDTO historySearchDTO,
           @ApiIgnore @AuthenticationPrincipal final AuthUserDTO authUserDTO,
           @PathVariable String menuCode
    ){
        log.info("HistoryController.countHistoryFolder");
        historySearchDTO.setRegisterSeq(authUserDTO.getUserSeq());
        return responseService.getSingleResult(historyService.countHistoryFolder(historySearchDTO, authUserDTO,menuCode));
    }
}


