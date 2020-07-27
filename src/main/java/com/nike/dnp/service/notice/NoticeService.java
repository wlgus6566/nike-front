package com.nike.dnp.service.notice;

import com.nike.dnp.common.ObjectMapperUtils;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.notice.NoticeArticleListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.dto.notice.NoticeUpdateDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.repository.notice.NoticeRepository;
import com.nike.dnp.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<NoticeArticleListDTO> findNoticePages(final NoticeSearchDTO noticeSearchDTO) {
        log.info("NoticeService.findNoticePages");

        Page<NoticeArticleListDTO> noticeArticles = noticeRepository.findNoticePages(
                noticeSearchDTO, PageRequest.of(noticeSearchDTO.getPage(), noticeSearchDTO.getSize()));

        return noticeArticles;
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
    public NoticeArticle findById(final Long noticeSeq) {
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
    public NoticeArticle save(final NoticeSaveDTO noticeSaveDTO) {
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

                noticeArticle.setThumbnailFileName(fileResultDTO.getFileName());
                noticeArticle.setThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
                noticeArticle.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
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
     * Update customer center notice article.
     *
     * @param noticeUpdateDTO the notice update dto
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 23. 오후 10:11:06
     * @Description Customer Center 게시글 수정
     */
    @Transactional
    public NoticeArticle updateCustomerCenter(final NoticeUpdateDTO noticeUpdateDTO) {
        log.info("NoticeService.updateCustomerCenter");

        final Optional<NoticeArticle> updateNotice = noticeRepository.findById(noticeUpdateDTO.getNoticeArticleSeq());

        final NoticeArticle noticeArticle = updateNotice.orElse(new NoticeArticle());

        noticeArticle.setNoticeArticleSectionCode(noticeUpdateDTO.getNoticeArticleSectionCode());
        noticeArticle.setTitle(noticeUpdateDTO.getTitle());
        noticeArticle.setContents(noticeUpdateDTO.getContents());
        noticeArticle.setUseYn(noticeUpdateDTO.getUseYn());

        if (StringUtils.equalsIgnoreCase(noticeUpdateDTO.getNoticeArticleSectionCode(), "NOTICE")) {
            noticeArticle.setNoticeYn(noticeUpdateDTO.getNoticeYn());
        }
        if (StringUtils.equalsIgnoreCase(noticeUpdateDTO.getNoticeArticleSectionCode(), "NEWS")) {
            if (!ObjectUtils.isEmpty(noticeUpdateDTO.getImageBase64())) {
                FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                        ServiceEnumCode.FileFolderEnumCode.FAQ.getFolder(), noticeUpdateDTO.getImageBase64());

                noticeArticle.setThumbnailFileName(fileResultDTO.getFileName());
                noticeArticle.setThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
                noticeArticle.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
            }
        }
        if (StringUtils.equalsIgnoreCase(noticeUpdateDTO.getNoticeArticleSectionCode(), "QNA")) {
            noticeArticle.setNoticeArticleCategoryCode(noticeUpdateDTO.getNoticeArticleCategoryCode());
        }

        return noticeRepository.save(noticeArticle);
    }

    /**
     * Delete customer center optional.
     *
     * @param noticeUpdateDTO the notice update dto
     * @return the optional
     * @author [정주희]
     * @CreatedOn 2020. 7. 20. 오후 10:06:54
     * @Description customer center 삭제 (사용 여부 == 'N')
     */
    @Transactional
    public NoticeArticle deleteCustomerCenter(final NoticeUpdateDTO noticeUpdateDTO) {
        log.info("NoticeService.deleteCustomerCenter");

        final Optional<NoticeArticle> deleteNnotice = noticeRepository.findById(noticeUpdateDTO.getNoticeArticleSeq());
        final NoticeArticle noticeArticle = deleteNnotice.orElse(new NoticeArticle());

        noticeArticle.setUseYn(noticeUpdateDTO.getUseYn());

        return noticeRepository.save(noticeArticle);
    }
}
