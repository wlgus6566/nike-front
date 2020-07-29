package com.nike.dnp.repository.code;

import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.entity.code.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * CodeRepositoryCustom
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:51:23
 * @implNote Code(공통 코드) Repository Custom Interface 작성
 */
@Repository
public interface CodeRepositoryCustom {

    /**
     * Find pages page.
     *
     * @param codeSearchDTO the code search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 5:51:23
     * @implNote 조회(페이징)
     */
    Page<Code> findPages(final CodeSearchDTO codeSearchDTO, final PageRequest pageRequest);

}
