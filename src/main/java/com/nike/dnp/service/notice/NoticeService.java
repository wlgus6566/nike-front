package com.nike.dnp.service.notice;

import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.dto.notice.NoticeUpdateDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The Class Notice service.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 6:15:37
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * Find notice pages page.
     *
     * @param noticeSearchDTO the notice search dto
     * @return the page
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 10:07:02
     * @Description Customer Center 게시글 목록 조회
     */
    public Page<NoticeArticeListDTO> findNoticePages(NoticeSearchDTO noticeSearchDTO) {
        log.info("NoticeService.findNoticePages");
        return noticeRepository.findNoticePages(
                noticeSearchDTO,
                PageRequest.of(noticeSearchDTO.getPage(), noticeSearchDTO.getSize()));
    }

    /**
     * Save notice article.
     *
     * @param noticeSaveDTO the notice save dto
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:21:49
     * @Description Customer Center 게시글 등록
     */
    @Transactional
    public NoticeArticle save(NoticeSaveDTO noticeSaveDTO) {
        log.info("NoticeService.save");

        NoticeArticle noticeArticle = new NoticeArticle();

        noticeArticle.setNoticeArticleSectionCode(noticeSaveDTO.getNoticeArticleSectionCode());
        noticeArticle.setTitle(noticeSaveDTO.getTitle());
        noticeArticle.setContents(noticeSaveDTO.getContents());
        noticeArticle.setUseYn(noticeSaveDTO.getUseYn());
        noticeArticle.setRegisterSeq(noticeSaveDTO.getRegisterSeq());
        noticeArticle.setUpdaterSeq(noticeSaveDTO.getRegisterSeq());

        if (StringUtils.equalsIgnoreCase(noticeSaveDTO.getNoticeArticleSectionCode(), "NOTICE")) {
            noticeArticle.setNoticeYn(noticeSaveDTO.getNoticeYn());
        }
        if (StringUtils.equalsIgnoreCase(noticeSaveDTO.getNoticeArticleSectionCode(), "NEWS")) {
            //TODO[jjh] 2020-07-19 썸네일 이미지 처리하기

        }
        if (StringUtils.equalsIgnoreCase(noticeSaveDTO.getNoticeArticleSectionCode(), "QNA")) {
            noticeArticle.setNoticeArticleCategoryCode(noticeSaveDTO.getNoticeArticleCategoryCode());
        }

        return noticeRepository.save(noticeArticle);
    }



    /**
     * Check notice yn cnt long.
     *
     * @return the long
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 9:21:57
     * @Description 공지사항 등록시 상단 고정된 게시글 개수 확인
     */
    public Long checkNoticeYnCnt() {
        log.info("NoticeService.checkNoticeYnCnt");

        return noticeRepository.checkNoticeYnCnt();
    }

    /**
     * Delete customer center optional.
     *
     * @param noticeUpdateDTO the notice update dto
     * @return the optional
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 10:06:54
     * @Description customer center 삭제 (사용여부 == 'N')
     */
    @Transactional
    public Optional<NoticeArticle> deleteCustomerCenter(NoticeUpdateDTO noticeUpdateDTO) {
        log.info("NoticeService.deleteCustomerCenter");

        Optional<NoticeArticle> noticeArticle = noticeRepository.findById(noticeUpdateDTO.getNoticeArticleSeq());
        noticeArticle.ifPresent(value -> value.delete());

        return noticeArticle;
    }
}
