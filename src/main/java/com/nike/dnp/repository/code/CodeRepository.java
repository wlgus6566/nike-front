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
 * @Description Code(공통 코드) Repository Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, CodeRepositoryCustom {

    /**
     * 상위 코드 목록
     *
     * @return the list
     * @author [오지훈]
     */
    List<Code> findAllByUpperCodeIsNullOrderByCodeOrderAsc();

    /**
     * 상세 조회
     *
     * @param code the code
     * @return the code
     * @author [오지훈]
     */
    Optional<Code> findByCode(String code);

}
