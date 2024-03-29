package com.nike.dnp.repository.history;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import com.nike.dnp.entity.history.History;
import com.nike.dnp.entity.history.QHistory;
import com.nike.dnp.util.ObjectMapperUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class History repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:12
 */
@Repository
public class HistoryRepositoryImpl extends QuerydslRepositorySupport implements HistoryRepositoryCustom {

    /**
     * Instantiates a new History repository.
     *
     * @author [이소정]
     * @since 2020. 7. 23. 오전 11:37:32
     * @implNote
     */
    public HistoryRepositoryImpl() {
        super(History.class);
    }

    @Override
    public Page<HistoryResultDTO> findAllHistoryPage(final HistorySearchDTO historySearchDTO, final PageRequest pageRequest) {
        final QHistory qHistory = QHistory.history;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<History> query = queryFactory
                .selectFrom(qHistory)
                .where(
                    HistoryPredicateHelper.eqTypeCdViewHistory(historySearchDTO),
                    qHistory.registerSeq.eq(historySearchDTO.getRegisterSeq()
                ));

        final List<History> historyList = ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), History.class);
        final List<HistoryResultDTO> historyResultList = new ArrayList<>();
        for (final History history : historyList) {
            final HistoryResultDTO historyResultDTO = new HistoryResultDTO();
            historyResultDTO.setHistorySeq(history.getHistorySeq());
            historyResultDTO.setTypeCd(history.getTypeCd());
            historyResultDTO.setUpdateDt(history.getUpdateDt());
//            report인 경우
            if (history.getTypeCd().equals(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString())) {
                historyResultDTO.setFolderSeq(history.getReportSeq());
                historyResultDTO.setImageFileName(history.getReport().getImageFileName());
                historyResultDTO.setMenuCode(history.getReport().getReportSectionCode());
                historyResultDTO.setImageFileSize(history.getReport().getImageFileSize());
                historyResultDTO.setImageFilePhysicalName(history.getReport().getImageFilePhysicalName());
                historyResultDTO.setFolderName(history.getReport().getReportName());
                historyResultDTO.setReadCount(history.getReport().getReadCount());
                historyResultDTO.setNickname(history.getReport().getUser().getNickname());
            } else {
                historyResultDTO.setFolderSeq(history.getContentsSeq());
                historyResultDTO.setImageFileName(history.getContents().getImageFileName());
                historyResultDTO.setImageFileSize(history.getContents().getImageFileSize());
                historyResultDTO.setImageFilePhysicalName(history.getContents().getImageFilePhysicalName());
                historyResultDTO.setFolderName(history.getContents().getFolderName());
                historyResultDTO.setReadCount(history.getContents().getReadCount());

                historyResultDTO.setTopMenuCode(history.getContents().getTopMenuCode());
                historyResultDTO.setMenuCode(history.getContents().getMenuCode());
                historyResultDTO.setFolderContents(history.getContents().getFolderContents());
                historyResultDTO.setCampaignPeriodSectionCode(history.getContents().getCampaignPeriodSectionCode());
                historyResultDTO.setCampaignBeginDt(history.getContents().getCampaignBeginDt());
                historyResultDTO.setCampaignEndDt(history.getContents().getCampaignEndDt());
            }
            historyResultList.add(historyResultDTO);
        }

        return new PageImpl<>(historyResultList, pageRequest, query.fetchCount());
    }

//    @Override
//    public Page<HistoryResultDTO> findAllHistoryPage(HistorySearchDTO historySearchDTO, PageRequest pageRequest) {
//        final QHistory qHistory = QHistory.history;
//
////        final QContents qContents = QContents.contents;
////        final QReport qReport = QReport.report;
////
////        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
////        final JPAQuery<HistoryResultDTO> query = queryFactory
////                .select(Projections.bean(
////                        HistoryResultDTO.class
////                        , qHistory.historySeq
////                        , qContents.topMenuCode
////                        , qContents.menuCode
////                        , qContents.folderContents
////                        , qContents.campaignPeriodSectionCode
////                        , qContents.campaignBeginDt
////                        , qContents.campaignEndDt
////                        , new CaseBuilder()
////                                .when(qHistory.typeCd.eq("REPORT")).then("REPORT")
////                                .when(qHistory.reportSeq.isNull()).then(qContents.topMenuCode)
////                                .otherwise("").as("typeCd")
////                        , new CaseBuilder()
////                                .when(qHistory.contentsSeq.isNull()).then(qHistory.reportSeq)
////                                .when(qHistory.reportSeq.isNull()).then(qHistory.contentsSeq)
////                                .otherwise(0l).as("folderSeq")
////                        , new CaseBuilder()
////                                .when(qHistory.contentsSeq.isNull()).then(qReport.imageFileName)
////                                .when(qHistory.reportSeq.isNull()).then(qContents.imageFileName)
////                                .otherwise("").as("imageFileName")
////                        , new CaseBuilder()
////                                .when(qHistory.contentsSeq.isNull()).then(qReport.imageFileSize)
////                                .when(qHistory.reportSeq.isNull()).then(qContents.imageFileSize)
////                                .otherwise("").as("imageFileSize")
////                        , new CaseBuilder()
////                                .when(qHistory.contentsSeq.isNull()).then(qReport.imageFilePhysicalName)
////                                .when(qHistory.reportSeq.isNull()).then(qContents.imageFilePhysicalName)
////                                .otherwise("").as("imageFilePhysicalName")
////                        , new CaseBuilder()
////                                .when(qHistory.contentsSeq.isNull()).then(qReport.reportName)
////                                .when(qHistory.reportSeq.isNull()).then(qContents.folderName)
////                                .otherwise("").as("folderName")
////                        , new CaseBuilder()
////                                .when(qHistory.contentsSeq.isNull()).then(qReport.readCount)
////                                .when(qHistory.reportSeq.isNull()).then(qContents.readCount)
////                                .otherwise(0l).as("readCount")
////
////                ))
////                .from(qHistory)
////                .leftJoin(qHistory).on(qHistory.contentsSeq.eq)
//    return null;
//    }
}
