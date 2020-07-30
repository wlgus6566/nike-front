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

}
