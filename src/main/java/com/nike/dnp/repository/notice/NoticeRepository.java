package com.nike.dnp.repository.notice;

import com.nike.dnp.entity.notice.NoticeArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Notice repository.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 6:23:45
 * @Description
 */
public interface NoticeRepository extends JpaRepository<NoticeArticle, Long>, NoticeRepositoryCustom {

    NoticeArticle findByNoticeArticleSeq(Long noticeSeq);
}
