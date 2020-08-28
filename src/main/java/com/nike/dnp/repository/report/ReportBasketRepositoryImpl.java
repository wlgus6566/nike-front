package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportBasketResultDTO;
import com.nike.dnp.entity.report.QReportBasket;
import com.nike.dnp.entity.report.QReportFile;
import com.nike.dnp.entity.report.ReportBasket;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Report repository.
 *
 * @author [이소정]
 * @implNote 보고서 장바구니 repository
 * @since 2020. 7. 7. 오후 2:46:09
 */
@Repository
public class ReportBasketRepositoryImpl extends QuerydslRepositorySupport implements ReportBasketRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     */
    public ReportBasketRepositoryImpl() {
        super(ReportBasket.class);
    }


    /**
     * Find all report basket list.
     *
     * @param userSeq the user seq
     * @return the list
     * @author [이소정]
     * @implNote 사용자에 따른 보고서 장바구니 목록 조회
     * @since 2020. 7. 17. 오후 6:45:22
     */
    @Override
    public List<ReportBasketResultDTO> findAllReportBasket(final Long userSeq) {
        final QReportBasket qReportBasket = QReportBasket.reportBasket;
        final QReportFile qReportFile = QReportFile.reportFile;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .select(Projections.bean(
                        ReportBasketResultDTO.class
                        , qReportFile.fileName
                        , qReportFile.fileSize
                        , qReportFile.filePhysicalName
                        , qReportFile.fileExtension
                        , qReportFile.fileKindCode
                        , qReportBasket.userSeq
                        , qReportBasket.reportFileSeq
                        , qReportBasket.reportBasketSeq
                )).from(qReportBasket)
                .innerJoin(qReportFile).on(qReportBasket.reportFileSeq.eq(qReportFile.reportFileSeq))
                .where(qReportBasket.userSeq.eq(userSeq))
                .orderBy(qReportBasket.registrationDt.desc())
                .fetch();
    }
}
