package com.nike.dnp.controller.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.dto.notice.NoticeUpdateDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The Class Notice controller.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 9. 오후 6:19:28
 * @Description
 */
@Slf4j
@RestController
@Api(description = "Customer Center", tags = "Customer")
@RequestMapping(value = "/api/customer", name = "Customer Center")
@RequiredArgsConstructor
public class NoticeController {

    /**
     * The Notice service
     *
     * @author [정주희]
     */
    private final NoticeService noticeService;
    private final ResponseService responseService;

    /**
     * Find all single result.
     *
     * @param noticeSearchDTO the notice search dto
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 13. 오후 11:07:03
     * @Description Customer Center 목록 조회
     */
    @ApiOperation(
            value = "Customer Center 목록 조회"
    )
    @GetMapping({"/{sectionCode}"})
    public SingleResult<Page<NoticeArticeListDTO>> findAll(@ModelAttribute NoticeSearchDTO noticeSearchDTO,
                                                           @PathVariable String sectionCode) {
        noticeSearchDTO.setNoticeArticleSectionCode(sectionCode.toUpperCase());
        return responseService.getSingleResult(noticeService.findNoticePages(noticeSearchDTO));
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
    @GetMapping("detail/{noticeSeq}")
    public SingleResult<NoticeArticle> findById(@PathVariable Long noticeSeq) {
        NoticeArticle noticeArticle = new NoticeArticle();
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
     * @CreatedOn 2020. 7. 20. 오후 9:21:31
     * @Description
     */
    @PostMapping({"/{sectionCode}"})
    public SingleResult<NoticeArticle> saveCustomerCenter(@RequestBody NoticeSaveDTO noticeSaveDTO,
                                                          @PathVariable String sectionCode) {
        noticeSaveDTO.setNoticeArticleSectionCode(sectionCode.toUpperCase());

        return responseService.getSingleResult(noticeService.save(noticeSaveDTO));
    }

    /**
     * Check notice yn cnt single result.
     *
     * @return the single result
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:26:05
     * @Description 공지사항 등록/수정시 상단 고정된 게시글 개수 확인
     */
    @GetMapping("/NOTICE/noticeYnCnt")
    public SingleResult<Long> checkNoticeYnCnt() {
        return responseService.getSingleResult(noticeService.checkNoticeYnCnt());
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
    @DeleteMapping({"/{noticeSeq}"})
    public SingleResult<Optional<NoticeArticle>> deleteCustomerCenter(@PathVariable Long noticeSeq){
        NoticeUpdateDTO noticeUpdateDTO = new NoticeUpdateDTO();
        noticeUpdateDTO.setNoticeArticleSeq(noticeSeq);
        noticeUpdateDTO.setUseYn("N");
        return responseService.getSingleResult(noticeService.deleteCustomerCenter(noticeUpdateDTO));
    }
}
