package com.nike.dnp.controller.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
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

/**
 * The Class Notice controller.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 9. 오후 6:19:28
 * @Description
 */
@Slf4j
@RestController
@Api(description = "Customer Center", tags = "NOTICE")
@RequestMapping(value = "/api/notice", name = "Customer Center")
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
    @GetMapping({"/{sectionCode}", "/{sectionCode}/{categoryCode}"})
    public SingleResult<Page<NoticeArticeListDTO>> findAll(@ModelAttribute NoticeSearchDTO noticeSearchDTO,
                                                           @PathVariable String sectionCode,
                                                           @PathVariable(required = false) String categoryCode) {
        noticeSearchDTO.setNoticeArticleSectionCode(sectionCode.toUpperCase());
        if (!StringUtils.isEmpty(categoryCode)) {
            noticeSearchDTO.setNoticeArticleCategoryCode(categoryCode.toUpperCase());
        }

        return responseService.getSingleResult(noticeService.findNoticePages(noticeSearchDTO));
    }

    @PostMapping({"/{sectionCode}", "/{sectionCode}/{categoryCode}"})
    public SingleResult<NoticeArticle> saveCustomerCenter(@RequestBody NoticeSaveDTO noticeSaveDTO,
                                                          @PathVariable String sectionCode,
                                                          @PathVariable(required = false) String categoryCode) {
        noticeSaveDTO.setNoticeArticleSectionCode(sectionCode.toUpperCase());
        if (!StringUtils.isEmpty(categoryCode)) {
            noticeSaveDTO.setNoticeArticleCategoryCode(categoryCode.toUpperCase());
        }
        
        return responseService.getSingleResult(noticeService.save(noticeSaveDTO));
    }

    @GetMapping("/NOTICE/noticeYnCnt")
    public SingleResult<Long> checkNoticeYnCnt() {
        return responseService.getSingleResult(noticeService.checkNoticeYnCnt());
    }
}
