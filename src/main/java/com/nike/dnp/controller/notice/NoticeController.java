package com.nike.dnp.controller.notice;

import com.nike.dnp.common.ObjectMapperUtils;
import com.nike.dnp.common.aspect.ValidField;
import com.nike.dnp.dto.notice.*;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.notice.NoticeService;
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

import javax.validation.Valid;
import java.io.IOException;

/**
 * The Class Notice controller.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 9. 오후 6:19:28
 * @Description
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

    private static final String BASIC_CHARACTER = "## Request ## \n" + "[하위 Parameters 참조] \n" + "## Request ## \n" + "[하위 Model 참조]\n\n";
    
    /**
     * Find all single result.
     *
     * @param customerSearchDTO the notice search dto
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 13. 오후 11:07:03
     * @Description Customer Center 목록 조회
     */
    @ApiOperation(
            value = "Customer Center 목록 조회",
            notes = REQUEST_CHARACTER +
                    "keyword|키워드|false|String| \n" +
                    "page|페이지|false|Integer| \n" +
                    "size|사이즈|false|Integer| \n" +
                    "noticeArticleCategoryCode|QNA 카테고리 코드|false|String|ASSET/SUBSIDIARY/REPORT/INFO/USED/ETC"
    )
    @GetMapping(value = "/{sectionCode}",
            name = "Customer Center 게시글 목록 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<Page<CustomerListDTO>> findAll(
                                @ApiParam(name = "sectionCode", value = "Customer Center 게시글 종류 코드",
                                        allowableValues = "NOTICE, NEWS, QNA", required = true)
                                @PathVariable final String sectionCode,
                                @ModelAttribute final CustomerSearchDTO customerSearchDTO) {
        log.info("NoticeController.findAll");

        customerSearchDTO.setNoticeArticleSectionCode(sectionCode);
        return responseService.getSingleResult(noticeService.findNoticePages(customerSearchDTO));
    }

    /**
     * Find by id single result.
     *
     * @param noticeSeq the notice seq
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 21. 오후 4:16:19
     * @Description Customer Center 상세 조회
     */
    @ApiOperation(
            value = "Customer Center 게시글 상세 조회",
            notes = REQUEST_CHARACTER + 
                    "noticeSeq|게시글 시퀀스|true|Long\n"
    )
    @GetMapping(value = "/detail/{noticeSeq}",
            name = "Customer Center 게시글 상세 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> findById(
                                @ApiParam(name = "noticeSeq", value = "Customer Center 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq) {
        log.info("NoticeController.findAll");

        return responseService.getSingleResult(noticeService.findById(noticeSeq));
    }

    /**
     * Save customer center single result.
     *
     * @param customerSaveDTO the notice save dto
     * @param sectionCode   the section code
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:21:31
     * @Description Customer Center 등록
     */
    @ApiOperation(
            value = "Customer Center 공지사항 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/NOTICE",
            name = "Customer Center 공지사항 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> saveNotice(@Valid @RequestBody final NoticeSaveDTO noticeSaveDTO,
                                                  BindingResult bindingResult) {
        log.info("NoticeController.saveNotice");

        CustomerSaveDTO customerSaveDTO = ObjectMapperUtils.map(noticeSaveDTO, CustomerSaveDTO.class);

        return responseService.getSingleResult(noticeService.save(customerSaveDTO));
    }

    @ApiOperation(
            value = "Customer Center 뉴스 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/NEWS",
            name = "Customer Center 뉴스 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> saveNews(@Valid @RequestBody final NewsSaveDTO newsSaveDTO,
                                                BindingResult bindingResult) {
        log.info("NoticeController.saveNews");

        CustomerSaveDTO customerSaveDTO = ObjectMapperUtils.map(newsSaveDTO, CustomerSaveDTO.class);

        return responseService.getSingleResult(noticeService.save(customerSaveDTO));
    }

    @ApiOperation(
            value = "Customer Center QnA 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/QnA",
            name = "Customer Center QnA 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> saveQna(@Valid @RequestBody final QnaSaveDTO qnaSaveDTO,
                                               BindingResult bindingResult) {
        log.info("NoticeController.saveQna");

        CustomerSaveDTO customerSaveDTO = ObjectMapperUtils.map(qnaSaveDTO, CustomerSaveDTO.class);

        return responseService.getSingleResult(noticeService.save(customerSaveDTO));
    }

    /**
     * Check notice yn cnt single result.
     *
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:26:05
     * @Description 공지사항 등록/수정시 상단 고정된 게시글 개수 확인
     */
    @ApiOperation(value = "공지사항 고정 게시글 개수 조회")
    @GetMapping("/NOTICE/noticeYnCnt")
    public SingleResult<Long> checkNoticeYnCnt() {
        log.info("NoticeController.checkNoticeYnCnt");

        return responseService.getSingleResult(noticeService.checkNoticeYnCnt());
    }

    /**
     * Update notice single result.
     *
     * @param customerUpdateDTO the notice update dto
     * @param noticeSeq       the notice seq
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 25. 오후 6:41:37
     * @Description Customer Center 게시글 수정
     */
    @ApiOperation(
            value = "Customer Center 공지사항 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "NOTICE/{noticeSeq}",
            name = "Customer Center 공지사항 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ValidField
    public SingleResult<NoticeArticle> updateNotice(
                                @ApiParam(name = "noticeSeq", value = "Customer Center 공지사항 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq,
                                @Valid @RequestBody final NoticeUpdateDTO noticeUpdateDTO,
                                BindingResult bindingResult) {
        log.info("NoticeController.updateNotice");

        CustomerUpdateDTO customerUpdateDTO = ObjectMapperUtils.map(noticeUpdateDTO, CustomerUpdateDTO.class);
        customerUpdateDTO.setNoticeArticleSeq(noticeSeq);

        return responseService.getSingleResult(noticeService.updateCustomerCenter(customerUpdateDTO));
    }

    @ApiOperation(
            value = "Customer Center NEWS 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "NEWS/{noticeSeq}",
            name = "Customer Center NEWS 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> updateNews(
                                @ApiParam(name = "noticeSeq", value = "Customer Center NEWS 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq,
                                @RequestBody final NewsUpdateDTO newsUpdateDTO,
                                BindingResult bindingResult) {
        log.info("NoticeController.updateNotice");

        CustomerUpdateDTO customerUpdateDTO = ObjectMapperUtils.map(newsUpdateDTO, CustomerUpdateDTO.class);
        customerUpdateDTO.setNoticeArticleSeq(noticeSeq);

        return responseService.getSingleResult(noticeService.updateCustomerCenter(customerUpdateDTO));
    }

    @ApiOperation(
            value = "Customer Center QnA 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "QNA/{noticeSeq}",
            name = "Customer Center QnA 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> updateQna(
                                @ApiParam(name = "noticeSeq", value = "Customer Center QnA 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq,
                                @RequestBody final QnaUpdateDTO qnaUpdateDTO,
                                BindingResult bindingResult) {
        log.info("NoticeController.updateNotice");

        CustomerUpdateDTO customerUpdateDTO = ObjectMapperUtils.map(qnaUpdateDTO, CustomerUpdateDTO.class);
        customerUpdateDTO.setNoticeArticleSeq(noticeSeq);

        return responseService.getSingleResult(noticeService.updateCustomerCenter(customerUpdateDTO));
    }

    /**
     * Delete customer center single result.
     *
     * @param noticeSeq   the notice seq
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:59:56
     * @Description Customer Center 게시글 삭제
     */
    @ApiOperation(value = "Customer Center 게시글 삭제", notes = BASIC_CHARACTER)
    @DeleteMapping({"/{noticeSeq}"})
    public SingleResult<NoticeArticle> deleteCustomerCenter(
                                @ApiParam(name = "sectionCode", value = "Customer Center 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq){
        log.info("NoticeController.deleteCustomerCenter");

        final CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO();
        customerUpdateDTO.setNoticeArticleSeq(noticeSeq);
        customerUpdateDTO.setUseYn("N");

        return responseService.getSingleResult(noticeService.deleteCustomerCenter(customerUpdateDTO));
    }

    @PostMapping("/{sectionCode}/images")
    public void uploadEditorImages(@PathVariable String sectionCode,
                                   MultipartHttpServletRequest multiReq) throws IOException {
        log.info("NoticeController.uploadEditorImages");
        noticeService.uploadEditorImages(sectionCode, multiReq);
    }

}
