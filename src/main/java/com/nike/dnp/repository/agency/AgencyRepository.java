package com.nike.dnp.repository.agency;

import com.nike.dnp.entity.agency.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The Interface Agency repository.
 *
 * @author [이소정]
 * @implNote 에이전시 repository Interface
 * @since 2020. 7. 30. 오후 3:10:44
 *
 */
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    /**
     * Find all by use yn list.
     *
     * @param useYn the use yn
     * @return the list
     * @author [이소정]
     * @implNote 사용중인 에이전시 목록 조회
     * @since 2020. 7. 20. 오후 12:08:03
     * @implNote
     */
    List<Agency> findAllByUseYnOrderByAgencyNameAsc(String useYn);

    /**
     * Find by id and use yn agency.
     *
     * @param agencySeq the agency seq
     * @param useYn     the use yn
     * @return the agency
     * @author [이소정]
     * @implNote 에이전시 seq 로 에이전시 상세 조회
     * @since 2020. 7. 20. 오후 12:21:10
     * @implNote
     */
    Optional<Agency> findByAgencySeqAndUseYn(long agencySeq, String useYn);

}
