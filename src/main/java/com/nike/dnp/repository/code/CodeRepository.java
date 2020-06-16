package com.nike.dnp.repository.code;

import com.nike.dnp.entity.code.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CodeRepository
 *
 * @author [오지훈]
 * @Description Code(공통 코드) Repository Interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, String>, CodeRepositoryCustom {

    /**
     * Find all by upper code list.
     *
     * @param upperCode the upper code
     * @return the list
     */
    List<Code> findAllByUpperCodeOrderByCodeOrderAsc(String upperCode);

}
