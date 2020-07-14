package com.nike.dnp.controller.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.dto.notice.NoticeSearchReqDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    @GetMapping("/{noticeArticleSectionCode}")
    public SingleResult<Page<NoticeArticeListDTO>> findAll(@ModelAttribute NoticeSearchReqDTO noticeSearchDTO,
                                                           @PathVariable String noticeArticleSectionCode ) {

        final NoticeSearchDTO noticeSearchDTO1 = objectMapper.convertValue(noticeSearchDTO, NoticeSearchDTO.class);
        noticeSearchDTO1.setNoticeArticleSectionCode(noticeArticleSectionCode);

        return responseService.getSingleResult(noticeService.findNoticePages(noticeSearchDTO1));
    }

}
