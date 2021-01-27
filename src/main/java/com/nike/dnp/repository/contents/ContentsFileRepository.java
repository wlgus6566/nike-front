package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.ContentsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface Contents file repository.
 *
 * @author [이소정]
 * @implNote 콘텐츠 파일 repository interface
 * @since 2020. 7. 1. 오전 10:41:27
 */
@Repository
public interface ContentsFileRepository extends JpaRepository<ContentsFile, Long>, ContentsFileRepositoryCustom {

    /**
     * Find by contents seq and use yn optional.
     *
     * @param contentsSeq 콘텐츠 seq
     * @param useYn       사용여부(Y, N)
     * @return the optional
     * @author [이소정]
     * @implNote 콘텐츠seq, 사용여부에 따른 파일 목록 조회
     * @since 2020. 7. 6. 오후 6:30:42
     */
    List<ContentsFile> findByContentsSeqAndUseYn(Long contentsSeq, String useYn);

    /**
     * Find by contents seq list.
     *
     * @param contentsSeq the contents seq
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 seq 파일 목록 조회
     * @since 2020. 8. 3. 오후 6:09:30
     */
    List<ContentsFile> findByContentsSeq(Long contentsSeq);

    /**
     * Count by contents seq and file section code and use yn long.
     *
     * @param ContentsSeq     the contents seq
     * @param fileSectionCode the file section code
     * @param useYn           the use yn
     * @return the long
     * @author [이소정]
     * @implNote파일 sectionCode 별 파일 갯수 조회
     * @since 2021. 1. 27. 오후 3:28:34
     */
    Long countByContentsSeqAndFileSectionCodeAndUseYn(Long ContentsSeq, String fileSectionCode, String useYn);

}
