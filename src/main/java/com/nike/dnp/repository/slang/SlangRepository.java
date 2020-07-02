package com.nike.dnp.repository.slang;

import com.nike.dnp.entity.slang.Slang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SlangRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 2. 오전 11:38:22
 * @Description Slang(금칙어) Repository 작성
 */
@Repository
public interface SlangRepository extends JpaRepository<Slang, Long> {

    /**
     * Count by slang contains int.
     *
     * @param slang the slang
     * @return the int
     * @author [오지훈]
     * @CreatedOn 2020. 7. 2. 오전 11:38:50
     * @Description 금칙어 검색
     */
    int countBySlangContains(String slang);

}
