package com.nike.dnp.repository.history;

import com.nike.dnp.entity.contents.RecentUpload;
import com.nike.dnp.entity.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Recent upload repository.
 *
 * @author [이소정]
 * @since 2020. 8. 5. 오후 3:25:55
 */
@Repository
public interface RecentUploadRepository extends JpaRepository<RecentUpload, Long>, RecentUploadRepositoryCustom {

    /**
     * Find all by report seq list.
     *
     * @param reportSeq the report seq
     * @return the list
     * @author [이소정]
     * @implNote 보고서 seq 로 목록 조회
     * @since 2020. 8. 5. 오후 3:22:21
     */
    List<RecentUpload> findAllByReportSeq(Long reportSeq);

    /**
     * Find all by contents seq list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 seq 로 목록 조회
     * @since 2020. 8. 5. 오후 3:22:55
     */
    List<RecentUpload> findAllByContentsSeq(Long contentsSeq);
}
