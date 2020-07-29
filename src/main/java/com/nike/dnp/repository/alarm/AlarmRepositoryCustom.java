package com.nike.dnp.repository.alarm;

import com.nike.dnp.dto.alarm.AlarmResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

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
