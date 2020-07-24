package com.nike.dnp.service.alarm;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.controller.alarm.AlarmResultDTO;
import com.nike.dnp.dto.SearchDTO;
import com.nike.dnp.entity.alarm.Alarm;
import com.nike.dnp.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Alarm service.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 24. 오후 7:44:29
 * @Description
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

    /**
     * The Alarm repositoryl
     *
     * @author [이소정]
     */
    private final AlarmRepository alarmRepositoryl;

    /**
     * Find all paging page.
     *
     * @param userSeq   the user seq
     * @param searchDTO the search dto
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 8:09:49
     * @Description
     */
    public Page<AlarmResultDTO> findAllPaging(final Long userSeq, final SearchDTO searchDTO) {
        return alarmRepositoryl.findAllPaging(
                userSeq,
                PageRequest.of(searchDTO.getPage()
                        , searchDTO.getSize()
                        , Sort.by("registrationDt").descending()));
    }

    /**
     * Send alarm list.
     *
     * @param actionCode     the action code
     * @param typeCd         the type cd
     * @param contentsSeq    the contents seq
     * @param reportSeq      the report seq
     * @param targetUserList the target user list
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 24. 오후 7:16:33
     * @Description
     */
    public List<Alarm> sendAlarmTargetList(
            final String actionCode, final String typeCd
            , final Long contentsSeq, final Long reportSeq
            , final List<Long> targetUserList
    ) {
        List<Alarm> savedAlarmList = new ArrayList<>();
        for (Long userSeq : targetUserList) {
            Alarm alarm = new Alarm();
            alarm.setUserSeq(userSeq);
            alarm.setTypeAction(actionCode);
            alarm.setTypeCd(typeCd);
            alarm.setContentsSeq(contentsSeq);
            alarm.setReportSeq(reportSeq);
            savedAlarmList.add(alarmRepositoryl.save(alarm));
        }
        return savedAlarmList;
    }
}
