package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportBasketResultDTO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Report basket repository custom.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니 repository custom interface
 * @since 2020. 7. 17. 오후 6:34:33
 */
@Repository
public interface ReportBasketRepositoryCustom {

    /**
     * Find page contents page.
     *
     * @param userSeq 사용자 seq
     * @return the page
     * @author [이소정]
     * @implNote 사용자에 맞는 보고서 장바구니 목록 조회
     * @since 2020. 6. 19. 오후 5:57:50
     */
    List<ReportBasketResultDTO> findAllReportBasket(final Long userSeq);

}
