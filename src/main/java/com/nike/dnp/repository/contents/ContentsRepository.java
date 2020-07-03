package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contents Repository
 *
 * @author [이소정]
 * @Description Contents Repository 작성
 */
@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long>, ContentsRepositoryCustom {

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @return contents contents
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:43
     * @Description
     */
    Contents findByContentsSeq(Long contentsSeq);

}
