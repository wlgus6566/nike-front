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
 * @implNote 콘텐츠 repository custom interface
 * @since 2020.06.11 7. 30. 오후 3:38:36
 */
@Repository
public interface ContentsRepositoryCustom {

    /**
     * Find page contents page.
     *
     * @param contentsSearchDTO 콘텐츠 검색 DTO
     * @param pageRequest       the page request
     * @return the page
     * @author [이소정]
     * @implNote 콘텐츠 페이징 처리 된 목록 조회
     * @since 2020. 7. 13. 오후 3:21:40
     */
    Page<ContentsResultDTO> findPageContents(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest);


    /**
     * Find recent contents list.
     *
     * @param topMenuCode the top menu code
     * @param pageRequest the page request
     * @return the list
     * @author [이소정]
     * @implNote 메뉴 코드에 따른 콘텐츠 목록 조회
     * @since 2020. 7. 27. 오후 6:41:13
     */
    List<ContentsResultDTO> findRecentContents(final String topMenuCode, final PageRequest pageRequest);

    /**
     * Find all contents mail auth user list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 메일 수신자 email 목록 조회
     * @since 2020. 7. 30. 오후 3:38:36
     */
    List<ContentsUserEmailDTO> findAllContentsMailAuthUser(final long contentsSeq);
}
