package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Report file repository.
 *
 * @author [이소정]
 * @implNote 보고서 파일 repository interface
 * @since 2020. 7. 8. 오후 5:55:07
 */
@Repository
public interface ReportFileRepository extends JpaRepository<ReportFile, Long> {

    /**
     * Find by report seq and use yn list.
     *
     * @param reportSeq 보고서 일련번호(seq)
     * @param useYn     사용여부(Y, N)
     * @return the list
     * @author [이소정]
     * @implNote 보고서 seq, 사용여부에 따른 보고서 파일 상세 조회
     * @since 2020. 7. 8. 오후 5:57:26
     */
    List<ReportFile> findByReportSeqAndUseYn(Long reportSeq, String useYn);

}
