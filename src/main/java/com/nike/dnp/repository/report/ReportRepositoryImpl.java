package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportResultDTO;
import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.QReport;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.entity.user.QUser;
import com.nike.dnp.util.ObjectMapperUtil;
import com.querydsl.core.types.Projections;
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
 * @implNote 보고서 repository
 * @since 2020. 7. 7. 오후 2:46:09
 */
@Repository
public class ReportRepositoryImpl extends QuerydslRepositorySupport implements ReportRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     */
    public ReportRepositoryImpl() {
        super(Report.class);
    }

    /**
     * Find page report page.
     *
     * @param reportSearchDTO 보고서 검색 DTO
     * @param pageRequest     the page request
     * @return the page
     * @author [이소정]
     * @implNote 보고서 페이지 처리 후 목록
     * @since 2020. 7. 7. 오후 4:49:05
     */
    @Override
    public Page<ReportResultDTO> findPageReport(final ReportSearchDTO reportSearchDTO, final PageRequest pageRequest) {
        final QReport qReport = QReport.report;
        final QUser qUser = QUser.user;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<ReportResultDTO> query = queryFactory
                .select(Projections.bean(
                        ReportResultDTO.class
                        , qReport.reportSeq
                        , qReport.reportSectionCode
                        , qReport.reportName
                        , qReport.imageFilePhysicalName
                        , qReport.readCount
                        , qUser.nickname
                        , qReport.updateDt
                    )
                )
                .from(qReport)
                .leftJoin(qUser).on(qReport.registerSeq.eq(qUser.userSeq))
                .where(
                        ReportPredicateHelper.compareKeyword(reportSearchDTO)
                        , ReportPredicateHelper.eqSectionCode(reportSearchDTO)
                        , qReport.useYn.eq("Y")
                        , qReport.authSeq.in(reportSearchDTO.getAuthSeqList())
                );

        final List<ReportResultDTO> reportList = ObjectMapperUtil.mapAll(
                getQuerydsl().applyPagination(pageRequest, query).fetch(), ReportResultDTO.class
        );
        return new PageImpl<>(reportList, pageRequest, query.fetchCount());
    }

    /**
     * Find recent report list.
     *
     * @param pageRequest the page request
     * @return the list
     * @author [이소정]
     * @implNote 최근 보고서 목록
     * @since 2020. 7. 27. 오후 6:31:34
     */
    @Override
    public List<ReportResultDTO> findRecentReport(final PageRequest pageRequest) {
        final QReport qReport = QReport.report;
        final JPAQuery<Report> query = new JPAQueryFactory(this.getEntityManager()).selectFrom(qReport)
                .where(QReport.report.useYn.eq("Y"));
        return ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ReportResultDTO.class);
    }

    /**
     * Find report with user name report result dto.
     *
     * @param reportSeq the report seq
     * @return the report result dto
     * @author [이소정]
     * @implNote 보고서 상세 조회 with 계정명
     * @since 2020. 8. 13. 오후 7:29:29
     */
    @Override
    public ReportResultDTO findReportWithUserName(Long reportSeq) {
        final QReport qReport = QReport.report;
        final QUser qUser = QUser.user;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        return queryFactory
                .select(Projections.bean(
                        ReportResultDTO.class
                        , qReport.reportSeq
                        , qReport.reportSectionCode
                        , qReport.reportName
                        , qReport.imageFilePhysicalName
                        , qReport.readCount
                        , qUser.nickname
                        , qReport.updateDt
                        )
                )
                .from(qReport)
                .leftJoin(qUser).on(qReport.registerSeq.eq(qUser.userSeq))
                .where( qReport.reportSeq.eq(reportSeq) ).fetchOne();
    }

}
