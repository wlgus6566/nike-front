package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Contents Repository
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 6. 오후 6:28:17
 * @Description Contents Repository 작성
 */
@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long>, ContentsRepositoryCustom {

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq the contents seq
     * @param topMenuCode the top menu code
     * @param menuCode    the menu code
     * @return contents contents
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:43
     * @Description
     */
    Optional<Contents> findByContentsSeqAndTopMenuCodeAndMenuCodeAndUseYn(Long contentsSeq, String topMenuCode, String menuCode, String useYn);
}
