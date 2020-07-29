package com.nike.dnp.repository.history;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import com.nike.dnp.entity.contents.QRecentUpload;
import com.nike.dnp.entity.contents.RecentUpload;
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
 * The Class Recent upload repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 27. 오후 2:11:58
 * @Description
 */
@Repository
public class RecentUploadRepositoryImpl extends QuerydslRepositorySupport implements RecentUploadRepositoryCustom {

    /**
     * Instantiates a new Recent upload repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 7. 27. 오후 2:11:55
     * @Description
     */
    public RecentUploadRepositoryImpl() {
        super(RecentUpload.class);
    }

    @Override
    public Page<HistoryResultDTO> findAllRecentUpload(HistorySearchDTO historySearchDTO, PageRequest pageRequest) {
        final QRecentUpload qRecentUpload = QRecentUpload.recentUpload;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<RecentUpload> query = queryFactory
                .selectFrom(qRecentUpload)
                .where(
                        HistoryPredicateHelper.eqTypeCdUploadHistory(historySearchDTO),
                        qRecentUpload.registerSeq.eq(historySearchDTO.getRegisterSeq()
                        ));

        final List<RecentUpload> recentUploadList = ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), RecentUpload.class);
        List<HistoryResultDTO> recentUploadResultList = new ArrayList<>();
        for (RecentUpload recentUpload : recentUploadList) {
            HistoryResultDTO historyResultDTO = new HistoryResultDTO();
            historyResultDTO.setHistorySeq(recentUpload.getRecentUploadSeq());
            historyResultDTO.setTypeCd(recentUpload.getTypeCd());
            historyResultDTO.setRegistrationDt(recentUpload.getRegistrationDt());
//            report인 경우
            if (recentUpload.getTypeCd().equals(ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString())) {
                historyResultDTO.setFolderSeq(recentUpload.getReportSeq());
                historyResultDTO.setImageFileName(recentUpload.getReport().getImageFileName());
                historyResultDTO.setImageFileSize(recentUpload.getReport().getImageFileSize());
                historyResultDTO.setImageFilePhysicalName(recentUpload.getReport().getImageFilePhysicalName());
                historyResultDTO.setFolderName(recentUpload.getReport().getReportName());
                historyResultDTO.setReadCount(recentUpload.getReport().getReadCount());
            } else {
                historyResultDTO.setFolderSeq(recentUpload.getContentsSeq());
                historyResultDTO.setImageFileName(recentUpload.getContents().getImageFileName());
                historyResultDTO.setImageFileSize(recentUpload.getContents().getImageFileSize());
                historyResultDTO.setImageFilePhysicalName(recentUpload.getContents().getImageFilePhysicalName());
                historyResultDTO.setFolderName(recentUpload.getContents().getFolderName());
                historyResultDTO.setReadCount(recentUpload.getContents().getReadCount());

                historyResultDTO.setTopMenuCode(recentUpload.getContents().getTopMenuCode());
                historyResultDTO.setMenuCode(recentUpload.getContents().getMenuCode());
                historyResultDTO.setFolderContents(recentUpload.getContents().getFolderContents());
                historyResultDTO.setCampaignPeriodSectionCode(recentUpload.getContents().getCampaignPeriodSectionCode());
                historyResultDTO.setCampaignBeginDt(recentUpload.getContents().getCampaignBeginDt());
                historyResultDTO.setCampaignEndDt(recentUpload.getContents().getCampaignEndDt());
            }
            recentUploadResultList.add(historyResultDTO);
        }

        return new PageImpl<>(recentUploadResultList, pageRequest, recentUploadResultList.size());
    }
}
