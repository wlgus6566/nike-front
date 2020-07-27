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
 * @Description Contents Repository Custom 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */
@Repository
public interface ReportRepositoryCustom {

    /**
     * Find page contents page.
     *
     * @param reportSearchDTO the report search dto
     * @param pageRequest     the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:50
     * @Description
     */
    Page<Report> findPageReport(final ReportSearchDTO reportSearchDTO, final PageRequest pageRequest);

    /**
     * Find recent report list.
     *
     * @param pageRequest the page request
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 27. 오후 6:19:35
     * @Description
     */
    List<ReportResultDTO> findRecentReport(final PageRequest pageRequest);

}
