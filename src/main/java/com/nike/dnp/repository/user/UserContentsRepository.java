package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * UserContentsRepository
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:53:42
 * @implNote UserContents(유저 컨텐츠 권한) Repository Interface 작성
 */
public interface UserContentsRepository extends JpaRepository<UserContents, Long> {

    /**
     * Delete by auth seq.
     *
     * @param contentsSeq the contents seq
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 12:16:07
     * @implNote 컨텐츠의 권한 삭제
     */
    void deleteByContentsSeq(final Long contentsSeq);

    /**
     * Find all by contents seq and auth seq and detail auth yn list.
     *
     * @param contentsSeq  the contents seq
     * @param authSeq      the auth seq
     * @param detailAuthYn the detail auth yn
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 상세 권한 목록 조회
     * @since 2020. 8. 28. 오후 6:12:08
     */
    List<UserContents> findAllByContentsSeqAndAuthSeqAndDetailAuthYn(final Long contentsSeq, final Long authSeq, final String detailAuthYn); 

}
