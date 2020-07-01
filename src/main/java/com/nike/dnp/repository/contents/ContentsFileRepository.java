package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Contents file repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 1. 오전 10:41:27
 * @Description
 */
@Repository
public interface ContentsFileRepository extends JpaRepository<ContentsFile, Long>, ContentsFileRepositoryCustom {

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @return contents contents
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:43
     * @Description
     */
    List<ContentsFile> findByContentsSeq(String contentsSeq);

}
