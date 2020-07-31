package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * PasswordHistoryRepository
 *
 * @author [오지훈]
 * @since 2020. 7. 1. 오후 4:31:19
 * @implNote 패스워드 히스토리 Repository
 */
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    /**
     * Find top 6 by user seq list.
     *
     * @param userSeq the user seq
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 1. 오후 4:32:02
     * @implNote 상위 6개 항목 가져오기
     */
    List<PasswordHistory> findTop6ByUserSeqOrderByRegistrationDtDesc(Long userSeq);

}
