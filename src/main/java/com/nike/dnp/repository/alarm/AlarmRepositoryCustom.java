package com.nike.dnp.repository.alarm;

import com.nike.dnp.dto.alarm.AlarmResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * The Interface Alarm repository custom.
 *
 * @author [이소정]
 * @implNote 알림 repository custom interface
 * @since 2020. 7. 30. 오후 3:14:17
 */
@Repository
public interface AlarmRepositoryCustom {

    /**
     * Find all paging page.
     *
     * @param userSeq     the user seq
     * @param pageRequest the page request
     * @return the page
     * @author [이소정]
     * @implNote 알림 페이징 처리 한 목록 조회
     * @since 2020. 7. 24. 오후 7:54:36
     */
    Page<AlarmResultDTO> findAllPaging(Long userSeq, PageRequest pageRequest);
}
