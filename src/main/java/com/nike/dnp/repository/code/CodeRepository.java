package com.nike.dnp.repository.code;

import com.nike.dnp.entity.code.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CodeRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:51:09
 * @Description Code(공통 코드) Repository Interface 작성
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, CodeRepositoryCustom {

    /**
     * Find all by upper code is null order by code order asc list.
     *
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:51:09
     * @Description 상위 코드 목록
     */
    List<Code> findAllByUpperCodeIsNullOrderByCodeOrderAsc();

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return the code
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 5:51:09
     * @Description 상세 조회
     */
    Optional<Code> findByCode(String code);

}
