package com.nike.dnp.repository.report;

import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.repository.contents.ContentsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The Interface Report repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:45:25
 * @Description
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {

    /**
     * Find by report seq report.
     *
     * @param reportSeq the report seq
     * @return the report
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:50:58
     * @Description
     */
    Report findByReportSeq(Long reportSeq);

}
