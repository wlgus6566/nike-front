package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface Report basket repository.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니 repository interface
 * @since 2020. 7. 30. 오후 3:17:14
 */
@Repository
public interface ReportBasketRepository extends JpaRepository<ReportBasket, Long>, ReportBasketRepositoryCustom {

    /**
     * Find all by report file seq list.
     *
     * @param reportFileSeq the report file seq
     * @return the list
     * @author [이소정]
     * @implNote 보고서 파일 seq 연관 장바구니 목록 조회
     * @since 2020. 8. 3. 오후 5:57:13
     */
    List<ReportBasket> findAllByReportFileSeq(Long reportFileSeq);

}
