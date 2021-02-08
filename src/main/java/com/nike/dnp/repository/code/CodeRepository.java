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
 * @implNote Code(공통 코드) Repository Interface 작성
 * @since 2020. 6. 24. 오후 5:51:09
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, CodeRepositoryCustom {

    /**
     * Find all by upper code is null order by code order asc list.
     *
     * @return the list
     * @author [오지훈]
     * @implNote 최상위 코드 목록 조회
     * @since 2020. 6. 24. 오후 5:51:09
     */
    List<Code> findAllByUpperCodeIsNullOrderByCodeOrderAsc();

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return the code
     * @author [오지훈]
     * @implNote 상세 조회
     * @since 2020. 6. 24. 오후 5:51:09
     */
    Optional<Code> findByCode(String code);

    /**
     * Find by upper code list.
     *
     * @param upperCode the upper code
     * @param useYn     the use yn
     * @return the list
     * @author [오지훈]
     * @implNote 상위코드로 하위코드 목록 조회
     * @since 2020. 7. 7. 오전 11:50:44
     */
    List<Code> findByUpperCodeAndUseYnOrderByCodeOrder(String upperCode, String useYn);

}
