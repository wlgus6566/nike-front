package com.nike.dnp.service.notice;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.dto.notice.NoticeUpdateDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.repository.notice.NoticeRepository;
import com.nike.dnp.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
     * Find by id notice article.
     *
     * @param noticeSeq the notice seq
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 21. 오후 4:07:10
     * @Description Customer Center 상세 조회
     */
    @Transactional
    public NoticeArticle findById(Long noticeSeq) {
        log.info("NoticeService.findById");

        return noticeRepository.findByNoticeArticleSeq(noticeSeq);
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
            if (!ObjectUtils.isEmpty(noticeSaveDTO.getImageBase64())) {
                FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                        ServiceEnumCode.FileFolderEnumCode.FAQ.getFolder(), noticeSaveDTO.getImageBase64());

                noticeSaveDTO.setThumbnailFileName(fileResultDTO.getThumbnailFileName());
                noticeSaveDTO.setThumbnailFilePhysicalName(fileResultDTO.getThumbnailPhysicalName());
                noticeSaveDTO.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
            }
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
        noticeArticle.ifPresent(value -> value.delete(noticeUpdateDTO));

        return noticeArticle;
    }
}
