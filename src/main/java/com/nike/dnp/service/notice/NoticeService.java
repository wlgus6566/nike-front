package com.nike.dnp.service.notice;

import com.nike.dnp.dto.notice.NoticeArticeListDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import com.nike.dnp.entity.notice.NoticeArticle;
import com.nike.dnp.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<NoticeArticeListDTO> findNoticePages(NoticeSearchDTO noticeSearchDTO) {
        log.info("NoticeService.findNoticePages");
        return noticeRepository.findNoticePages(
                noticeSearchDTO,
                PageRequest.of(noticeSearchDTO.getPage(), noticeSearchDTO.getSize()));
    }

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

    public Long checkNoticeYnCnt() {
        log.info("NoticeService.checkNoticeYnCnt");

        return noticeRepository.checkNoticeYnCnt();
    }
}
