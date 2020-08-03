package com.nike.dnp.repository.notice;

import com.nike.dnp.dto.notice.CustomerListDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * The Interface Notice repository custom.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 6:23:37
 * @implNote
 */
public interface NoticeRepositoryCustom {

    /**
     * Find notice pages page.
     *
     * @param customerSearchDTO the customer search dto
     * @param pageRequest       the page request
     * @return the page
     * @author [오지훈]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 4:12:18
     */
    Page<CustomerListDTO> findNoticePages(CustomerSearchDTO customerSearchDTO, PageRequest pageRequest);

    /**
     * Check notice yn cnt long.
     *
     * @return the long
     * @author [오지훈]
     * @implNote [method 설명]
     * @since 2020. 7. 31. 오후 4:12:22
     */
    Long checkNoticeYnCnt();
}
