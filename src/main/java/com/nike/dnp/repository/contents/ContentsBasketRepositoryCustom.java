package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.contents.ContentsBasketResultDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Contents basket repository custom.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 15. 오후 12:17:26
 * @Description
 */
@Repository
public interface ContentsBasketRepositoryCustom {

    List<ContentsBasketResultDTO> findAllWithContentsFile(final AuthUserDTO authUserDTO, final String useYn);
}
