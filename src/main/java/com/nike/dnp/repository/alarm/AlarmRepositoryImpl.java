package com.nike.dnp.repository.alarm;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.alarm.AlarmResultDTO;
import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.entity.alarm.Alarm;
import com.nike.dnp.entity.alarm.QAlarm;
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
 * The Class Alarm repository.
 *
 * @author [이소정]
 * @implNote 알림 repository (implements AlarmRepositoryCustom)
 * @since 2020. 7. 24. 오후 7:53:49
 */
@Repository
public class AlarmRepositoryImpl extends QuerydslRepositorySupport implements AlarmRepositoryCustom {

    /**
     * Instantiates a new Alarm repository.
     *
     * @author [이소정]
     * @implNote 주입
     * @since 2020. 7. 24. 오후 7:53:51
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
     * @implNote 알림 페이징 처리 된 목록 조회
     * @since 2020. 7. 24. 오후 7:56:25
     */
    @Override
    public Page<AlarmResultDTO> findAllPaging(final Long userSeq, final PageRequest pageRequest) {
        final QAlarm qAlarm = QAlarm.alarm;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        JPAQuery<Alarm> query = queryFactory.selectFrom(qAlarm)
                .where(qAlarm.userSeq.eq(userSeq));

//        final List<Alarm> alarmList = queryFactory.selectFrom(qAlarm)
//                .where(qAlarm.userSeq.eq(userSeq)).fetch();

        final List<Alarm> alarmList = ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), Alarm.class);

        final List<AlarmResultDTO> alarmResultList = new ArrayList<>();
        for (final Alarm alarm : alarmList) {
            final AlarmResultDTO alarmResultDTO = new AlarmResultDTO();
            alarmResultDTO.setAlarmSeq(alarm.getAlarmSeq());
            alarmResultDTO.setUserSeq(userSeq);
            alarmResultDTO.setTypeAction(alarm.getTypeAction());
            alarmResultDTO.setTypeCd(alarm.getTypeCd());
            alarmResultDTO.setRegistrationDt(alarm.getRegistrationDt());

            if (ServiceCode.HistoryTabEnumCode.REPORT_MANAGE.toString().equals(alarm.getTypeCd())) {
                alarmResultDTO.setFolderSeq(alarm.getReport().getReportSeq());
                alarmResultDTO.setFolderName(alarm.getReport().getReportName());
            } else {
                alarmResultDTO.setFolderSeq(alarm.getContents().getContentsSeq());
                alarmResultDTO.setFolderName(alarm.getContents().getFolderName());
                alarmResultDTO.setMenuCode(alarm.getContents().getMenuCode());
            }

            alarmResultList.add(alarmResultDTO);
        }

        return new PageImpl<>(alarmResultList, pageRequest, query.fetchCount());
    }
}
