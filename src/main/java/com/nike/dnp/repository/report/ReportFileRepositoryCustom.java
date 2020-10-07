package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportFileResultDTO;
import com.nike.dnp.dto.report.ReportFileSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;


/**
 * The Interface Report file repository custom.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:42:06
 */
@Repository
public interface ReportFileRepositoryCustom {


    /**
     * Find all report file paging page.
     *
     * @param reportFileSearchDTO the report file search dto
     * @param pageRequest         the page request
     * @return the page
     * @author [이소정]
     * @implNote 보고서 파일 페이징 처리 된 목록 조회
     * @since 2020. 8. 13. 오후 7:42:39
     */
    Page<ReportFileResultDTO> findAllReportFilePaging(final ReportFileSearchDTO reportFileSearchDTO, final PageRequest pageRequest);

}
