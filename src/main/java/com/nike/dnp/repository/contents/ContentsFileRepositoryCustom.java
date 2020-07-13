package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * Find all contents file list.
     *
     * @param contentsFileSearchDTO the contents file search dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 13. 오후 5:57:15
     * @Description
     */
    List<ContentsFileResultDTO> findAllContentsFile(final ContentsFileSearchDTO contentsFileSearchDTO);

}
