package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Report answer repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 10. 오후 6:50:14
 * @Description
 */
@Repository
public interface ReportAnswerRepository extends JpaRepository<ReportAnswer, Long> {

    /**
     * Find all by answer seq and use yn list.
     *
     * @param reportSeq the report seq
     * @param useYn     the use yn
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 10. 오후 6:50:10
     * @Description
     */
    List<ReportAnswer> findAllByReportSeqAndUseYn(final Long reportSeq, final String useYn);

}