package com.nike.dnp.service.notice;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.notice.CustomerListDTO;
import com.nike.dnp.dto.notice.CustomerSaveDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import com.nike.dnp.dto.notice.CustomerUpdateDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.notice.NoticeRepository;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Optional;

/**
 * The Class Notice service.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 6:15:37
 * @implNote
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    /**
     * The Notice repository
     *
     * @author [정주희]
     */
    private final NoticeRepository noticeRepository;

    /**
     * The Notice max count
     *
     * @author [정주희]
     */
    private final Integer NOTICE_MAX_COUNT = 3;

    /**
     * Find notice pages page.
     *
     * @param customerSearchDTO the notice search dto
     * @return the page
     * @author [정주희]
     * @since 2020. 7. 20. 오후 10:07:02
     * @implNote Customer Center 게시글 목록 조회
     */
    public Page<CustomerListDTO> findNoticePages(final CustomerSearchDTO customerSearchDTO) {
        log.info("NoticeService.findNoticePages");

        Page<CustomerListDTO> noticeArticles = noticeRepository.findNoticePages(
                customerSearchDTO, PageRequest.of(customerSearchDTO.getPage(), customerSearchDTO.getSize()));

        return noticeArticles;
    }

    /**
     * Find by id notice article.
     *
     * @param noticeSeq the notice seq
     * @return the notice article
     * @author [정주희]
     * @since 2020. 7. 21. 오후 4:07:10
     * @implNote Customer Center 상세 조회
     */
    @Transactional
    public NoticeArticle findById(final Long noticeSeq) {
        log.info("NoticeService.findById");
        return noticeRepository.findByNoticeArticleSeq(noticeSeq);
    }

    /**
     * Save notice article.
     *
     * @param customerSaveDTO the notice save dto
     * @return the notice article
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:21:49
     * @implNote Customer Center 게시글 등록
     */
    @Transactional
    public NoticeArticle save(final CustomerSaveDTO customerSaveDTO) {
        log.info("NoticeService.save");

        final NoticeArticle noticeArticle = noticeRepository.save(new NoticeArticle().save(customerSaveDTO));

        return noticeArticle;
    }

    /**
     * Check notice yn cnt long.
     *
     * @return the long
     * @author [정주희]
     * @since 2020. 7. 20. 오후 9:21:57
     * @implNote 공지사항 등록시 상단 고정된 게시글 개수 확인
     */
    public Long checkNoticeYnCnt() {
        log.info("NoticeService.checkNoticeYnCnt");

        final Integer count = Math.toIntExact(noticeRepository.checkNoticeYnCnt());

        if (count >= NOTICE_MAX_COUNT) {
            throw new CodeMessageHandleException(FailCode.ConfigureError.EXCEED_MAX_NOTICE.name(),
                    MessageUtil.getMessage(FailCode.ConfigureError.EXCEED_MAX_NOTICE.name()));
        }

        return noticeRepository.checkNoticeYnCnt();
    }

    /**
     * Update customer center notice article.
     *
     * @param customerUpdateDTO the notice update dto
     * @return the notice article
     * @author [정주희]
     * @since 2020. 7. 23. 오후 10:11:06
     * @implNote Customer Center 게시글 수정
     */
    @Transactional
    public NoticeArticle updateCustomerCenter(final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeService.updateCustomerCenter");

        final Optional<NoticeArticle> updateNotice = noticeRepository.findById(customerUpdateDTO.getNoticeArticleSeq());
        updateNotice.ifPresent(value -> value.update(customerUpdateDTO));
        NoticeArticle noticeArticle = updateNotice.get();

        return noticeArticle;
    }

    /**
     * Delete customer center optional.
     *
     * @param customerUpdateDTO the notice update dto
     * @return the optional
     * @author [정주희]
     * @since 2020. 7. 20. 오후 10:06:54
     * @implNote customer center 삭제 (사용 여부 == 'N')
     */
    @Transactional
    public NoticeArticle deleteCustomerCenter(final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeService.deleteCustomerCenter");

        final Optional<NoticeArticle> deleteNotice = noticeRepository.findById(customerUpdateDTO.getNoticeArticleSeq());
        final NoticeArticle noticeArticle = deleteNotice.orElse(new NoticeArticle());

        noticeArticle.setUseYn(customerUpdateDTO.getUseYn());

        return noticeRepository.save(noticeArticle);
    }

    public String uploadEditorImages(MultipartHttpServletRequest multiReq, String sectionCode) throws IOException {
        log.info("NoticeService.uploadEditorImages");

        MultipartFile mf = multiReq.getFile("editor");
        final String uploadUrl = S3Util.upload(mf, sectionCode);

        return uploadUrl;
    }
}
