package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportResultDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contents Repository Custom
 *
 * @author [이소정]
 * @implNote Contents Repository Custom 작성
 * @since 2020.06.11 7. 30. 오후 3:22:29
 */
@Repository
public interface ReportRepositoryCustom {

    /**
     * Find page contents page.
     *
     * @param reportSearchDTO 보고서 검색 DTO
     * @param pageRequest     the page request
     * @return the page
     * @author [이소정]
     * @implNote 보고서 페이지 처리 된 목록 조회
     * @since 2020. 6. 19. 오후 5:57:50
     */
    Page<ReportResultDTO> findPageReport(final ReportSearchDTO reportSearchDTO, final PageRequest pageRequest);

    /**
     * Find recent report list.
     *
     * @param pageRequest the page request
     * @return the list
     * @author [이소정]
     * @implNote 최근 보고서 조회
     * @since 2020. 7. 27. 오후 6:19:35
     */
    List<ReportResultDTO> findRecentReport(final PageRequest pageRequest);

    /**
     * Find report with user name report result dto.
     *
     * @param reportSeq the report seq
     * @return the report result dto
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 13. 오후 7:12:59
     */
    ReportResultDTO findReportWithUserName(final Long reportSeq);

}
