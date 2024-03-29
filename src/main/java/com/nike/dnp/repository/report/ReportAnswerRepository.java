package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Report answer repository.
 *
 * @author [이소정]
 * @implNote 보고서 댓글 repository interface
 * @since 2020. 7. 10. 오후 6:50:14
 */
@Repository
public interface ReportAnswerRepository extends JpaRepository<ReportAnswer, Long>, ReportAnswerRepositoryCustom {

}
