package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Contents Repository
 *
 * @author [이소정]
 * @implNote 콘텐츠 repository interface
 * @since 2020. 7. 6. 오후 6:28:17
 */
@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long>, ContentsRepositoryCustom {

    /**
     * Find by contents seq contents.
     *
     * @param contentsSeq 콘텐츠 일련번호
     * @param topMenuCode 콘텐츠 상위메뉴코드
     * @param menuCode    콘텐츠 메뉴코드
     * @param useYn       콘텐츠 노출여부
     * @return contents contents
     * @author [이소정]
     * @implNote 콘텐츠 메뉴, 노출여부에 따른 콘텐츠 조회
     * @since 2020. 6. 19. 오후 5:57:43
     */
    Optional<Contents> findByContentsSeqAndTopMenuCodeAndMenuCodeAndUseYn(Long contentsSeq, String topMenuCode, String menuCode, String useYn);
}
