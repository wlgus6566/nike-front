package com.nike.dnp.repository.notice;

import com.nike.dnp.entity.notice.NoticeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface Notice repository.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 6:23:45
 * @implNote
 */
public interface NoticeRepository extends JpaRepository<NoticeArticle, Long>, NoticeRepositoryCustom {

    NoticeArticle findByNoticeArticleSeq(Long noticeSeq);
}
