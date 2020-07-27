package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.contents.ContentsUserEmailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contents Repository Custom
 *
 * @author [이소정]
 * @Description Contents Repository Custom 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */
@Repository
public interface ContentsRepositoryCustom {

    /**
     * Find page contents page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param pageRequest       the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 13. 오후 3:21:40
     * @Description
     */
    Page<ContentsResultDTO> findPageContents(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest);


    /**
     * Find recent contents list.
     *
     * @param topMenuCode the top menu code
     * @param pageRequest the page request
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 27. 오후 6:41:13
     * @Description
     */
    List<ContentsResultDTO> findRecentContents(final String topMenuCode, final PageRequest pageRequest);

    /**
     * Find all contents mail auth user list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     */
    List<ContentsUserEmailDTO> findAllContentsMailAuthUser(final long contentsSeq);
}
