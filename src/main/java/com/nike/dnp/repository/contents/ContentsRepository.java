package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contents Repository
 *
 * @since 2020.06.11
 * @author [이소정]
 * @Description Contents Repository 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 *
 */
@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long>, ContentsRepositoryCustom {

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @return contents
     */
    Contents findByContentsSeq(String contentsSeq);

}
