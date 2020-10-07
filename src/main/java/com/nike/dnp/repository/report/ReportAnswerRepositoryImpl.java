package com.nike.dnp.repository.report;

import com.nike.dnp.dto.report.ReportAnswerResultDTO;
import com.nike.dnp.entity.report.QReportAnswer;
import com.nike.dnp.entity.report.ReportAnswer;
import com.nike.dnp.entity.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Report answer repository.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 8:06:34
 */
@Repository
public class ReportAnswerRepositoryImpl extends QuerydslRepositorySupport implements ReportAnswerRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Report Answer repository.
     *
     * @author [이소정]
     * @implNote 생성자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     */
    public ReportAnswerRepositoryImpl() {
        super(ReportAnswer.class);
    }

    /**
     * Find all report answer list list.
     *
     * @param reportSeq the report seq
     * @return the list
     * @author [이소정]
     * @implNote 보고서 댓글 목록 조회
     * @since 2020. 8. 13. 오후 8:07:58
     */
    @Override
    public List<ReportAnswerResultDTO> findAllReportAnswerList(Long reportSeq) {
        final QReportAnswer qReportAnswer = QReportAnswer.reportAnswer;
        final QUser qUser = QUser.user;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        return queryFactory
                .select(Projections.bean(
                        ReportAnswerResultDTO.class
                        , qReportAnswer.answerSeq
                        , qReportAnswer.reportSeq
                        , qReportAnswer.answerContents
                        , qUser.nickname
                        , qUser.userId
                        , qReportAnswer.updateDt
                    )
                )
                .from(qReportAnswer)
                .leftJoin(qUser).on(qReportAnswer.registerSeq.eq(qUser.userSeq))
                .where(qReportAnswer.useYn.eq("Y")
                , qReportAnswer.reportSeq.eq(reportSeq))
                .fetch();
    }
}

