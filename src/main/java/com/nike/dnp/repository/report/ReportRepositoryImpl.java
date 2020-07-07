package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportSearchDTO;
import com.nike.dnp.entity.report.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


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

    @Override
    public Page<Report> findPageReport(final ReportSearchDTO reportSearchDTO, final PageRequest pageRequest) {
        return null;
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
