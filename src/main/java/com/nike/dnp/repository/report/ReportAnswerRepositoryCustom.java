package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportAnswerResultDTO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Report answer repository custom.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:51:56
 */
@Repository
public interface ReportAnswerRepositoryCustom {


    /**
     * Find all report answer page.
     *
     * @param ReportSeq the report seq
     * @return the page
     * @author [이소정]
     * @implNote 보고서 댓글 조회
     * @since 2020. 8. 13. 오후 7:52:11
     */
    List<ReportAnswerResultDTO> findAllReportAnswerList(final Long ReportSeq);

}
