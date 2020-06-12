package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * Contents Repository Custom
 *
 * @since 2020.06.11
 * @author [이소정]
 * @Description Contents Repository Custom 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 *
 */
@Repository
public interface ContentsRepositoryCustom {

    /**
     * Find alls page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param pageRequest       the page request
     * @return the page
     */
    Page<Contents> findAlls(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest);

}
