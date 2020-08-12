package com.nike.dnp.controller.notice;


import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.notice.*;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.notice.NoticeService;
import com.nike.dnp.util.ObjectMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;

/**
 * The Class Notice controller.
 *
 * @author [정주희]
 * @apiNote
 * @since 2020. 7. 9. 오후 6:19:28
 */
@Slf4j
@RestController
@Api(description = "Customer Center", tags = "CUSTOMER")
@RequestMapping(value = "/api/customer", name = "Customer Center")
@RequiredArgsConstructor
public class NoticeController {

    /**
     * The Notice service
     *
     * @author [정주희]
     */
    private final NoticeService noticeService;
    /**
     * The Response service
     *
     * @author [정주희]
     */
    private final ResponseService responseService;

    /**
     * The constant REQUEST_CHARACTER
     *
     * @author [정주희]
     */
    private static final String REQUEST_CHARACTER = "## Reqeust ## \n" + "필드명|설명|필수여부|데이터 타입(길이)|추가\n" + "-|-|-|-|-|-\n";

    /**
     * The constant BASIC_CHARACTER
     *
     * @author [정주희]
     */
    private static final String BASIC_CHARACTER = "## Request ## \n" + "[하위 Parameters 참조] \n" + "## Request ## \n" + "[하위 Model 참조]\n\n";

    /**
     * Find all single result.
     *
     * @param customerSearchDTO the notice search dto
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @apiNote Customer Center 목록 조회
     * @since 2020. 7. 13. 오후 11:07:03
     */
    @ApiOperation(
            value = "Customer Center 목록 조회",
            notes = REQUEST_CHARACTER +
                    "keyword|키워드|false|String| \n" +
                    "page|페이지|false|Integer| \n" +
                    "size|사이즈|false|Integer| \n" +
                    "noticeArticleCategoryCode|QNA 카테고리 코드|false|String|ASSET/SUBSIDIARY/REPORT/INFO/USED/ETC"
    )
    @GetMapping(value = "/{noticeArticleSectionCode}",
            name = "Customer Center 게시글 목록 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<CustomerListDTO>> findAll(
            @ApiParam(name = "noticeArticleSectionCode", value = "Customer Center 게시글 종류 코드",
                    allowableValues = "NOTICE, NEWS, QNA", required = true)
            @PathVariable final String noticeArticleSectionCode,
            @ModelAttribute final CustomerSearchDTO customerSearchDTO) {
        log.info("NoticeController.findAll");
        return responseService.getSingleResult(noticeService.findNoticePages(customerSearchDTO));
    }

    /**
     * Find by id single result.
     *
     * @param noticeSeq the notice seq
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @apiNote Customer Center 상세 조회
     * @since 2020. 7. 21. 오후 4:16:19
     */
    @ApiOperation(
            value = "Customer Center 게시글 상세 조회",
            notes = REQUEST_CHARACTER +
                    "noticeSeq|게시글 시퀀스|true|Long\n"
    )
    @GetMapping(value = "/detail/{noticeSeq}",
            name = "Customer Center 게시글 상세 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> findById(@ApiParam(name = "noticeSeq", value = "Customer Center 게시글 시퀀스",
            defaultValue = "23", required = true) @PathVariable final Long noticeSeq) {
        log.info("NoticeController.findAll");
        return responseService.getSingleResult(noticeService.findById(noticeSeq));
    }

    /**
     * Save customer center single result.
     *
     * @param noticeSaveDTO the notice save dto
     * @param bindingResult the binding result
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @apiNote Customer Center 등록
     * @since 2020. 7. 20. 오후 9:21:31
     */
    @ApiOperation(
            value = "Customer Center 공지사항 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/NOTICE",
            name = "Customer Center 공지사항 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> saveNotice(@Valid @RequestBody final NoticeSaveDTO noticeSaveDTO,
                                                  @ApiIgnore final BindingResult bindingResult) {
        log.info("NoticeController.saveNotice");

        final CustomerSaveDTO customerSaveDTO = ObjectMapperUtil.map(noticeSaveDTO, CustomerSaveDTO.class);

        return responseService.getSingleResult(noticeService.save(customerSaveDTO));
    }

    /**
     * Save news single result.
     *
     * @param newsSaveDTO   the news save dto
     * @param bindingResult the binding result
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 3:47:54
     */
    @ApiOperation(
            value = "Customer Center 뉴스 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/NEWS",
            name = "Customer Center 뉴스 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> saveNews(@Valid @RequestBody final NewsSaveDTO newsSaveDTO,
                                                @ApiIgnore final BindingResult bindingResult) {
        log.info("NoticeController.saveNews");

        final CustomerSaveDTO customerSaveDTO = ObjectMapperUtil.map(newsSaveDTO, CustomerSaveDTO.class);

        return responseService.getSingleResult(noticeService.save(customerSaveDTO));
    }

    /**
     * Save qna single result.
     *
     * @param qnaSaveDTO    the qna save dto
     * @param bindingResult the binding result
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 3:47:55
     */
    @ApiOperation(
            value = "Customer Center QnA 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/QnA",
            name = "Customer Center QnA 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> saveQna(@Valid @RequestBody final QnaSaveDTO qnaSaveDTO,
                                               @ApiIgnore final BindingResult bindingResult) {
        log.info("NoticeController.saveQna");

        final CustomerSaveDTO customerSaveDTO = ObjectMapperUtil.map(qnaSaveDTO, CustomerSaveDTO.class);

        return responseService.getSingleResult(noticeService.save(customerSaveDTO));
    }

    /**
     * Check notice yn cnt single result.
     *
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @apiNote 공지사항 등록/수정시 상단 고정된 게시글 개수 확인
     * @since 2020. 7. 20. 오후 9:26:05
     */
    @ApiOperation(value = "공지사항 고정 게시글 개수 조회", notes = BASIC_CHARACTER)
    @GetMapping("/NOTICE/noticeYnCnt")
    public SingleResult<Long> checkNoticeYnCnt() {
        log.info("NoticeController.checkNoticeYnCnt");
        return responseService.getSingleResult(noticeService.checkNoticeYnCnt());
    }

    /**
     * Update notice single result.
     *
     * @param noticeSeq       the notice seq
     * @param noticeUpdateDTO the notice update dto
     * @param bindingResult   the binding result
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @apiNote Customer Center 게시글 수정
     * @since 2020. 7. 25. 오후 6:41:37
     */
    @ApiOperation(
            value = "Customer Center 공지사항 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "/NOTICE/{noticeSeq}",
            name = "Customer Center 공지사항 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> updateNotice(
            @ApiParam(name = "noticeSeq", value = "Customer Center 공지사항 게시글 시퀀스",
                    defaultValue = "23", required = true) @PathVariable final Long noticeSeq,
            @Valid @RequestBody final NoticeUpdateDTO noticeUpdateDTO, @ApiIgnore final BindingResult bindingResult) {
        log.info("NoticeController.updateNotice");

        final CustomerUpdateDTO customerUpdateDTO = ObjectMapperUtil.map(noticeUpdateDTO, CustomerUpdateDTO.class);

        return responseService.getSingleResult(noticeService.updateCustomerCenter(noticeSeq, customerUpdateDTO));
    }

    /**
     * Update news single result.
     *
     * @param noticeSeq     the notice seq
     * @param newsUpdateDTO the news update dto
     * @param bindingResult the binding result
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 3:47:55
     */
    @ApiOperation(
            value = "Customer Center NEWS 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "/NEWS/{noticeSeq}",
            name = "Customer Center NEWS 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> updateNews(
            @ApiParam(name = "noticeSeq", value = "Customer Center NEWS 게시글 시퀀스",
                    defaultValue = "23", required = true) @PathVariable final Long noticeSeq,
            @Valid @RequestBody final NewsUpdateDTO newsUpdateDTO, @ApiIgnore final BindingResult bindingResult) {
        log.info("NoticeController.updateNotice");

        final CustomerUpdateDTO customerUpdateDTO = ObjectMapperUtil.map(newsUpdateDTO, CustomerUpdateDTO.class);

        return responseService.getSingleResult(noticeService.updateCustomerCenter(noticeSeq, customerUpdateDTO));
    }

    /**
     * Update qna single result.
     *
     * @param noticeSeq     the notice seq
     * @param qnaUpdateDTO  the qna update dto
     * @param bindingResult the binding result
     * @return the single result
     * @author [정주희]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 3:47:56
     */
    @ApiOperation(
            value = "Customer Center QnA 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "/QNA/{noticeSeq}",
            name = "Customer Center QnA 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> updateQna(
            @ApiParam(name = "noticeSeq", value = "Customer Center QnA 게시글 시퀀스",
                    defaultValue = "23", required = true) @PathVariable final Long noticeSeq,
            @Valid @RequestBody final QnaUpdateDTO qnaUpdateDTO, @ApiIgnore final BindingResult bindingResult) {
        log.info("NoticeController.updateNotice");

        final CustomerUpdateDTO customerUpdateDTO = ObjectMapperUtil.map(qnaUpdateDTO, CustomerUpdateDTO.class);

        return responseService.getSingleResult(noticeService.updateCustomerCenter(noticeSeq, customerUpdateDTO));
    }

    /**
     * Delete customer center single result.
     *
     * @param noticeSeq the notice seq
     * @return the single result
     * @author [정주희]
     * @implNote Customer Center 게시글 삭제
     * @since 2020. 7. 20. 오후 9:59:56
     */
    @ApiOperation(value = "Customer Center 게시글 삭제", notes = BASIC_CHARACTER)
    @DeleteMapping(value = "/{noticeSeq}",
            name = "Customer Center 게시글 삭제", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> deleteCustomerCenter(
            @ApiParam(name = "noticeSeq", value = "Customer Center 게시글 시퀀스",
                    defaultValue = "23", required = true) @PathVariable final Long noticeSeq) {
        log.info("NoticeController.deleteCustomerCenter");
        return responseService.getSingleResult(noticeService.deleteCustomerCenter(noticeSeq));
    }

    /**
     * Upload editor images single result.
     *
     * @param noticeArticleSectionCode the section code
     * @param multiReq    the multi req
     * @return the single result
     * @throws IOException the io exception
     * @author [정주희]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 3:47:48
     */
    @ApiOperation(value = "Customer Center 에디터 이미지 업로드", notes = BASIC_CHARACTER)
    @PostMapping("/{noticeArticleSectionCode}/images")
    public SingleResult<String> uploadEditorImages(
            @ApiParam(name = "noticeArticleSectionCode", value = "Customer Center 게시글 종류 코드",
                    allowableValues = "NOTICE, NEWS, QNA", required = true)
            @PathVariable final String noticeArticleSectionCode,
            final MultipartHttpServletRequest multiReq) {
        log.info("NoticeController.uploadEditorImages");

        String uploadUrl = noticeService.uploadEditorImages(multiReq, noticeArticleSectionCode);
        return responseService.getSingleResult(uploadUrl);
    }
}
