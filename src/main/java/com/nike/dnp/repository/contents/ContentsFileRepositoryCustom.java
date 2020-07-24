package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * The Interface Contents file repository custom.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 5:57:19
 * @Description
 */
@Repository
public interface ContentsFileRepositoryCustom {


    /**
     * Find all contents file paging page.
     *
     * @param contentsFileSearchDTO the contents file search dto
     * @param pageRequest           the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 14. 오후 3:18:37
     * @Description
     */
    Page<ContentsFileResultDTO> findAllContentsFilePaging(final ContentsFileSearchDTO contentsFileSearchDTO, final PageRequest pageRequest);

}
