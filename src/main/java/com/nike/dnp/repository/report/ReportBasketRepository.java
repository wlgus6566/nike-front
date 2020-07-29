package com.nike.dnp.repository.report;

import com.nike.dnp.entity.report.ReportBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportBasketRepository extends JpaRepository<ReportBasket, Long>, ReportBasketRepositoryCustom {

}
