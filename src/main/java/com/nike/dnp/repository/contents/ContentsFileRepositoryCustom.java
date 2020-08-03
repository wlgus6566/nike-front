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
 * @implNote 콘텐츠 파일 repository custom repository
 * @since 2020. 7. 13. 오후 5:57:19
 */
@Repository
public interface ContentsFileRepositoryCustom {

    /**
     * Find all contents file paging page.
     *
     * @param contentsFileSearchDTO 콘텐츠 파일 조회 DTO
     * @param pageRequest           the page request
     * @return the page
     * @author [이소정]
     * @implNote 콘텐츠 파일 페이징 처리 후 조회
     * @since 2020. 7. 14. 오후 3:18:37
     */
    Page<ContentsFileResultDTO> findAllContentsFilePaging(final ContentsFileSearchDTO contentsFileSearchDTO, final PageRequest pageRequest);

}
