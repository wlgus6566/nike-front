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

    /**
     * Find by notice article seq notice article.
     *
     * @param noticeSeq the notice seq
     * @return the notice article
     * @author [오지훈]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 4:12:17
     */
    NoticeArticle findByNoticeArticleSeq(Long noticeSeq);
}
