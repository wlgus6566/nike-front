package com.nike.dnp.repository.alarm;

import com.nike.dnp.dto.alarm.AlarmResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * The Interface Alarm repository custom.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:08
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
     * @since 2020. 7. 24. 오후 7:54:36
     * @implNote
     */
    Page<AlarmResultDTO> findAllPaging(Long userSeq, PageRequest pageRequest);
}
