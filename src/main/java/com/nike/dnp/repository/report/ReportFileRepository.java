package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Report file repository.
 *
 * @author [이소정]
 * @since 2020. 7. 8. 오후 5:55:07
 * @implNote
 */
@Repository
public interface ReportFileRepository extends JpaRepository<ReportFile, Long> {

    /**
     * Find by report seq and use yn list.
     *
     * @param reportSeq the report seq
     * @param useYn     the use yn
     * @return the list
     * @author [이소정]
     * @since 2020. 7. 8. 오후 5:57:26
     * @implNote
     */
    List<ReportFile> findByReportSeqAndUseYn(Long reportSeq, String useYn);

}
