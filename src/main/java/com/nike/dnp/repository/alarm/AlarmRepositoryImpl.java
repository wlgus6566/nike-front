package com.nike.dnp.repository.alarm;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.controller.alarm.AlarmResultDTO;
import com.nike.dnp.entity.alarm.Alarm;
import com.nike.dnp.entity.alarm.QAlarm;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.report.Report;
import com.nike.dnp.repository.report.ReportPredicateHelper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Alarm repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 24. 오후 7:53:49
 * @Description
 */
@Repository
public class AlarmRepositoryImpl extends QuerydslRepositorySupport implements AlarmRepositoryCustom{

    /**
     * Instantiates a new Alarm repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 7:53:51
     * @Description
     */
    public AlarmRepositoryImpl() {
        super(Alarm.class);
    }


    /**
     * Find all paging page.
     *
     * @param userSeq     the user seq
     * @param pageRequest the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 7:56:25
     * @Description
     */
    @Override
    public Page<AlarmResultDTO> findAllPaging(Long userSeq, PageRequest pageRequest) {
        final QAlarm qAlarm = QAlarm.alarm;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        final List<Alarm> alarmList =  queryFactory.selectFrom(qAlarm)
                .where(qAlarm.userSeq.eq(userSeq)).fetch();

        final List<AlarmResultDTO> alarmResultList = new ArrayList<>();
        for (Alarm alarm : alarmList) {
            AlarmResultDTO alarmResultDTO = new AlarmResultDTO();
            alarmResultDTO.setUserSeq(userSeq);
            alarmResultDTO.setTypeAction(alarm.getTypeAction());
            alarmResultDTO.setTypeCd(alarm.getTypeCd());

            if (ServiceEnumCode.HistoryTabEnumCode.REPORT_MANAGE.toString().equals(alarm.getTypeCd())) {
                alarmResultDTO.setFolderSeq(alarm.getReport().getReportSeq());
                alarmResultDTO.setFolderName(alarm.getReport().getReportName());
            } else {
                alarmResultDTO.setFolderSeq(alarm.getContents().getContentsSeq());
                alarmResultDTO.setFolderName(alarm.getContents().getFolderName());
            }

            alarmResultList.add(alarmResultDTO);
        }

        return new PageImpl<>(alarmResultList, pageRequest, alarmResultList.size());
    }
}
