package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.QReport;
import com.nike.dnp.entity.report.Report;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Report repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:46:09
 * @Description
 */
@Repository
public class ReportRepositoryImpl extends QuerydslRepositorySupport implements ReportRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 6:15:29
     * @Description
     */
    public ReportRepositoryImpl() {
        super(Report.class);
    }

    /**
     * Find page report page.
     *
     * @param reportSearchDTO the report search dto
     * @param pageRequest     the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 7. 오후 4:49:05
     * @Description
     */
    @Override
    public Page<Report> findPageReport(final ReportSearchDTO reportSearchDTO, final PageRequest pageRequest) {
        final QReport qReport = QReport.report;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<Report> query = queryFactory.selectFrom(qReport)
                .where(
                        ReportPredicateHelper.compareKeyword(reportSearchDTO)
                        , ReportPredicateHelper.eqSectionCode(reportSearchDTO)
                        , qReport.useYn.eq("Y")
                        , qReport.authSeq.in(reportSearchDTO.getAuthSeqList())
                );


        final List<Report> reportList = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(reportList, pageRequest, query.fetchCount());
    }

    /**
     * Find alls page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param pageRequest       the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:54:39
     * @Description
     */
//    @Override
//    public Page<Contents> findPageContents(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest) {
//        final QContents qContents = QContents.contents;
//        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
//        final JPAQuery<Contents> query = queryFactory.selectFrom(qContents)
//                .where(
//                        ContentsPredicateHelper.compareKeyword(contentsSearchDTO)
//                        , ContentsPredicateHelper.eqMenuCode(contentsSearchDTO)
//                        , ContentsPredicateHelper.eqExposureYn("Y")
//                        , qContents.useYn.eq("Y")
//                );
//
//        final List<Contents> contentsList = getQuerydsl().applyPagination(pageRequest, query).fetch();
//        return new PageImpl<>(contentsList, pageRequest, query.fetchCount());
//
//
//    }
}
