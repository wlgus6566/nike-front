package com.nike.dnp.controller.history;

import com.nike.dnp.dto.auth.AuthUserDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
     * OPERATION_CHARACTER
     *
     * @author [오지훈]
     */
    private static final String REQUEST_CHARACTER
            = "## Request ##\n[하위 Parameters 참조]\n\n\n\n## Response ##\n[하위 Model 참조]\n\n\n\n";

    @ApiOperation(
            value = "최근 업로드한 폴더 목록"
            , notes = REQUEST_CHARACTER
            + "page|페이지|false|Integer|0부터 시작\n"
            + "size|사이즈|false|Integer\n"
            + "typeCd|타입 코드(값 없을 경우 ALL)|false|String|ALL/ASSET/TOOLKIT/FOUNDATION/REPORT\n"
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
        historySearchDTO.setRegisterSeq(authUserDTO.getUserSeq());
        return responseService.getSingleResult(historyService.findAllUploadFolderPaging(historySearchDTO));
    }

    @ApiOperation(
            value = "최근 본 폴더 목록"
            , notes = REQUEST_CHARACTER
            + "page|페이지|false|Integer|0부터 시작\n"
            + "size|사이즈|false|Integer\n"
            + "typeCd|타입 코드(값 없을 경우 ALL)|false|String|ALL/ASSET/TOOLKIT/FOUNDATION/REPORT\n"
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
}


