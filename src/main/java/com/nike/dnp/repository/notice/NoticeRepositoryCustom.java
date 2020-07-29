package com.nike.dnp.repository.notice;

import com.nike.dnp.dto.notice.CustomerListDTO;
import com.nike.dnp.dto.notice.CustomerSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * The Interface Notice repository custom.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 6:23:37
 * @Description
 */
public interface NoticeRepositoryCustom {

    Page<CustomerListDTO> findNoticePages(CustomerSearchDTO customerSearchDTO, PageRequest pageRequest);

    Long checkNoticeYnCnt();
}
