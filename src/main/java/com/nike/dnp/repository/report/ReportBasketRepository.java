package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The Interface Report basket repository.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니 repository interface
 * @since 2020. 7. 30. 오후 3:17:14
 */
@Repository
public interface ReportBasketRepository extends JpaRepository<ReportBasket, Long>, ReportBasketRepositoryCustom {

}
