package com.nike.dnp.repository.notice;

import com.nike.dnp.dto.notice.NoticeArticleListDTO;
import com.nike.dnp.dto.notice.NoticeSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * The Interface Notice repository custom.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 6:23:37
 * @implNote
 */
public interface NoticeRepositoryCustom {

    Page<NoticeArticleListDTO> findNoticePages(NoticeSearchDTO noticeSearchDTO, PageRequest pageRequest);

    Long checkNoticeYnCnt();
}
