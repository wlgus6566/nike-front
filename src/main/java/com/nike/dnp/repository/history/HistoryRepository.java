package com.nike.dnp.repository.history;

import com.nike.dnp.entity.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface History repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:11
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, HistoryRepositoryCustom {

    /**
     * Find all by report seq list.
     *
     * @param reportSeq the report seq
     * @return the list
     * @author [이소정]
     * @implNote 보고서 seq 로 목록 조회
     * @since 2020. 8. 5. 오후 3:22:21
     */
    List<History> findAllByReportSeq(Long reportSeq);

    /**
     * Find all by contents seq list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 seq 로 목록 조회
     * @since 2020. 8. 5. 오후 3:22:55
     */
    List<History> findAllByContentsSeq(Long contentsSeq);

    List<History> findByRegisterSeq(Long registerSeq);




    /**
     * Find all by contents seq list.
     *
     * @param registerSeq register seq
     * @return Long
     * @author [최미영]
     * @implNote resister seq로 목록 갯수 조회
     * @since 2021. 12. 10. 오후 2:55
     */

    Long countByRegisterSeqAndTypeCd(Long registerSeq,String typeCd);
    Long countByRegisterSeq(Long registerSeq);
}
