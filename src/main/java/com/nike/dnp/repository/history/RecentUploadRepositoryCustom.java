package com.nike.dnp.repository.history;

import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.dto.history.HistorySearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * The Interface Recent upload repository custom.
 *
 * @author [이소정]
 * @since 2020. 7. 27. 오후 2:13:46
 * @implNote
 */
@Repository
public interface RecentUploadRepositoryCustom {

    /**
     * Find all recent upload page.
     *
     * @param historySearchDTO the history search dto
     * @param pageRequest      the page request
     * @return the page
     * @author [이소정]
     * @since 2020. 7. 27. 오후 2:13:43
     * @implNote
     */
    Page<HistoryResultDTO> findAllRecentUpload(final HistorySearchDTO historySearchDTO, final PageRequest pageRequest);
}
