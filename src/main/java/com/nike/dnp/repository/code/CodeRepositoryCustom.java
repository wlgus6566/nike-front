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
 * @Description Code(공통 코드) Repository Custom Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface CodeRepositoryCustom {

    /**
     * Find alls page.
     *
     * @param codeSearchDTO the code search dto
     * @param pageRequest   the page request
     * @return the page
     */
    Page<Code> findAlls(final CodeSearchDTO codeSearchDTO, final PageRequest pageRequest);

}
