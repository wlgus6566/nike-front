package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserContents;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
