package com.nike.dnp.controller.notice;

import com.nike.dnp.dto.notice.NoticeArticleListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.dto.notice.NoticeUpdateDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The Class Notice controller.
 *
 * @author [정주희]
 * @since 2020. 7. 9. 오후 6:19:28
 * @apiNote
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
     * @param noticeSearchDTO the notice search dto
     * @return the single result
     * @author [정주희]
     * @since 2020. 7. 13. 오후 11:07:03
     * @apiNote Customer Center 목록 조회
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
    public SingleResult<Page<NoticeArticleListDTO>> findAll(
                                @ApiParam(name = "sectionCode", value = "Customer Center 게시글 종류 코드",
                                        allowableValues = "NOTICE, NEWS, QNA", required = true)
                                @PathVariable final String sectionCode,
                                @Valid @ModelAttribute final NoticeSearchDTO noticeSearchDTO) {
        log.info("NoticeService.findAll");

        noticeSearchDTO.setNoticeArticleSectionCode(sectionCode.toUpperCase());
        return responseService.getSingleResult(noticeService.findNoticePages(noticeSearchDTO));
    }

    /**
     * Find by id single result.
     *
     * @param noticeSeq the notice seq
     * @return the single result
     * @author [정주희]
     * @since 2020. 7. 21. 오후 4:16:19
     * @apiNote Customer Center 상세 조회
     */
    @ApiOperation(
            value = "Customer Center 게시글 상세 조회",
            notes = REQUEST_CHARACTER + 
                    "noticeSeq|게시글 시퀀스|true|Long\n"
    )
    @GetMapping(value = "detail/{noticeSeq}",
            name = "Customer Center 게시글 상세 조회", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> findById(
                                @ApiParam(name = "noticeSeq", value = "Customer Center 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq) {
        log.info("NoticeService.findAll");

        final NoticeArticle noticeArticle = new NoticeArticle();
        noticeArticle.setNoticeArticleSeq(noticeSeq);

        return responseService.getSingleResult(noticeService.findById(noticeArticle.getNoticeArticleSeq()));
    }

    /**
     * Save customer center single result.
     *
     * @param noticeSaveDTO the notice save dto
     * @param sectionCode   the section code
     * @return the single result
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:21:31
     * @apiNote Customer Center 등록
     */
    @ApiOperation(
            value = "Customer Center 게시글 등록",
            notes = BASIC_CHARACTER
    )
    @PostMapping(value = "/{sectionCode}",
            name = "Customer Center 게시글 등록", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> saveCustomerCenter(
                                @ApiParam(name = "sectionCode", value = "Customer Center 게시글 종류 코드",
                                        allowableValues = "NOTICE, NEWS, QNA", required = true)
                                @PathVariable final String sectionCode,
                                @RequestBody final NoticeSaveDTO noticeSaveDTO) {
        log.info("NoticeService.findAll");

        noticeSaveDTO.setNoticeArticleSectionCode(sectionCode.toUpperCase());

        return responseService.getSingleResult(noticeService.save(noticeSaveDTO));
    }

    /**
     * Check notice yn cnt single result.
     *
     * @return the single result
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:26:05
     * @apiNote 공지사항 등록/수정시 상단 고정된 게시글 개수 확인
     */
    @ApiOperation(value = "공지사항 고정게시글 개수 조회")
    @GetMapping("/NOTICE/noticeYnCnt")
    public SingleResult<Long> checkNoticeYnCnt() {
        log.info("NoticeService.checkNoticeYnCnt");

        return responseService.getSingleResult(noticeService.checkNoticeYnCnt());
    }

    /**
     * Update notice single result.
     *
     * @param noticeUpdateDTO the notice update dto
     * @param noticeSeq       the notice seq
     * @return the single result
     * @author [정주희]
     * @since 2020. 7. 25. 오후 6:41:37
     * @apiNote Customer Center 게시글 수정
     */
    @ApiOperation(
            value = "Customer Center 게시글 수정",
            notes = BASIC_CHARACTER
    )
    @PutMapping(value = "/{noticeSeq}",
            name = "Customer Center 게시글 수정", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SingleResult<NoticeArticle> updateNotice(
                                @ApiParam(name = "sectionCode", value = "Customer Center 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq,
                                @RequestBody final NoticeUpdateDTO noticeUpdateDTO) {
        log.info("NoticeService.updateNotice");

        noticeUpdateDTO.setNoticeArticleSeq(noticeSeq);
        return responseService.getSingleResult(noticeService.updateCustomerCenter(noticeUpdateDTO));
    }

    /**
     * Delete customer center single result.
     *
     * @param noticeSeq   the notice seq
     * @return the single result
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:59:56
     * @apiNote Customer Center 게시글 삭제
     */
    @ApiOperation(value = "상품 삭제", notes = BASIC_CHARACTER)
    @DeleteMapping({"/{noticeSeq}"})
    public SingleResult<NoticeArticle> deleteCustomerCenter(
                                @ApiParam(name = "sectionCode", value = "Customer Center 게시글 시퀀스",
                                        defaultValue = "23", required = true)
                                @PathVariable final Long noticeSeq){
        log.info("NoticeService.deleteCustomerCenter");

        final NoticeUpdateDTO noticeUpdateDTO = new NoticeUpdateDTO();
        noticeUpdateDTO.setNoticeArticleSeq(noticeSeq);
        noticeUpdateDTO.setUseYn("N");
        return responseService.getSingleResult(noticeService.deleteCustomerCenter(noticeUpdateDTO));
    }
}
