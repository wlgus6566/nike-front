package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The Interface Report repository.
 *
 * @author [이소정]
 * @since 2020. 7. 7. 오후 2:45:25
 * @implNote
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {

    /**
     * Find by report seq report.
     *
     * @param reportSeq the report seq
     * @return the report
     * @author [이소정]
     * @since 2020. 7. 8. 오후 5:50:58
     * @implNote
     */
    Report findByReportSeq(Long reportSeq);

}
