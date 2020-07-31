package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Report basket repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:27
 */
@Repository
public interface ReportBasketRepository extends JpaRepository<ReportBasket, Long>, ReportBasketRepositoryCustom {

}
