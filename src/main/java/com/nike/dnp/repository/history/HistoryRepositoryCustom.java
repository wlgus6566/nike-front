package com.nike.dnp.repository.history;

import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface History repository custom.
 *
 * @author [이소정]
 * @since 2020. 7. 23. 오후 12:16:19
 * @implNote
 */
@Repository
public interface HistoryRepositoryCustom {

    /**
     * Find all history page page.
     *
     * @param historySearchDTO the history search dto
     * @param pageRequest      the page request
     * @return the page
     * @author [이소정]
     * @since 2020. 7. 24. 오후 4:01:02
     * @implNote
     */
    Page<HistoryResultDTO> findAllHistoryPage(final HistorySearchDTO historySearchDTO, final PageRequest pageRequest);

}
