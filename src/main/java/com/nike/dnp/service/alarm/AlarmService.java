package com.nike.dnp.service.alarm;

import com.nike.dnp.dto.SearchDTO;
import com.nike.dnp.dto.alarm.AlarmResultDTO;
import com.nike.dnp.entity.alarm.Alarm;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.alarm.AlarmRepository;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class Alarm service.
 *
 * @author [이소정]
 * @implNote 알림 서비스
 * @since 2020. 7. 24. 오후 7:44:29
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
     * @param searchDTO the search dto
     * @return the page
     * @author [이소정]
     * @implNote 알림 페이징 처리 된 목록 조회
     * @since 2020. 7. 24. 오후 8:09:49
     */
    public Page<AlarmResultDTO> findAllPaging(final SearchDTO searchDTO) {
        return alarmRepositoryl.findAllPaging(
                SecurityUtil.currentUser().getUserSeq(),
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
     * @implNote 전송할 알림 목록
     * @since 2020. 7. 24. 오후 7:16:33
     */
    @Transactional
    public List<Alarm> sendAlarmTargetList(
            final String actionCode, final String typeCd
            , final Long contentsSeq, final Long reportSeq
            , final List<Long> targetUserList
    ) {
        final List<Alarm> savedAlarmList = new ArrayList<>();
        for (final Long userSeq : targetUserList) {
            final Alarm alarm = new Alarm();
            alarm.setUserSeq(userSeq);
            alarm.setTypeAction(actionCode);
            alarm.setTypeCd(typeCd);
            alarm.setContentsSeq(contentsSeq);
            alarm.setReportSeq(reportSeq);
            savedAlarmList.add(alarmRepositoryl.save(alarm));
        }
        return savedAlarmList;
    }

    /**
     * Delete alarm.
     *
     * @param alarmSeq the alarm seq
     * @return the alarm
     * @author [이소정]
     * @implNote 알림 삭제
     * @since 2020. 8. 14. 오후 8:33:46
     */
    @Transactional
    public Alarm delete(final Long alarmSeq) {
        final Optional<Alarm> alarm = Optional.ofNullable(
                alarmRepositoryl.findById(alarmSeq).orElseThrow(() -> new NotFoundHandleException())
        );
        alarmRepositoryl.delete(alarm.get());
        return alarm.get();
    }
}
