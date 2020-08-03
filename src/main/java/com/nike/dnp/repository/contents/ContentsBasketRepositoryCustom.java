package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Contents basket repository custom.
 *
 * @author [이소정]
 * @implNote 콘텐츠 장바구니 repository custom interface
 * @since 2020. 7. 15. 오후 12:17:26
 */
@Repository
public interface ContentsBasketRepositoryCustom {

    /**
     * Find all with contents file list.
     *
     * @param userSeq the user seq
     * @return the list
     * @author [이소정]
     * @implNote
     * @since 2020. 7. 17. 오후 6:46:50
     */
    List<ContentsBasketResultDTO> findAllWithContentsFile(final Long userSeq);
}
