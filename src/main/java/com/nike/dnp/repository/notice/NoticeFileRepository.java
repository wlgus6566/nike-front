package com.nike.dnp.repository.notice;

import com.nike.dnp.entity.notice.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The Interface Notice file repository.
 *
 * @author [이소정]
 * @since 2020. 12. 17. 오후 5:46:19
 */
public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {

    /**
     * Find all by notice article seq and use yn list.
     *
     * @param noticeArticleSeq the notice article seq
     * @param userYn           the user yn
     * @return the list
     * @author [이소정]
     * @implNote 게시물 seq로 파일 목록 조회
     * @since 2020. 12. 17. 오후 5:46:19
     */
    List<NoticeFile> findAllByNoticeArticleSeqAndUseYn(final Long noticeArticleSeq, final String userYn);

}
