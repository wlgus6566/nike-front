package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportBasketResultDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Report basket repository custom.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 17. 오후 6:34:33
 * @Description
 */
@Repository
public interface ReportBasketRepositoryCustom {

    /**
     * Find page contents page.
     *
     * @param userSeq the user seq
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:50
     * @Description
     */
    List<ReportBasketResultDTO> findAllReportBasket(final Long userSeq);

}
