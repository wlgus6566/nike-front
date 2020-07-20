package com.nike.dnp.repository.agency;

import com.nike.dnp.entity.agency.Agency;
import com.nike.dnp.entity.contents.ContentsBasket;
import com.nike.dnp.repository.contents.ContentsBasketRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    /**
     * Find all by use yn list.
     *
     * @param useYn the use yn
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 20. 오후 12:08:03
     * @Description
     */
    List<Agency> findAllByUseYn(String useYn);

    /**
     * Find by id and use yn agency.
     *
     * @param agencySeq the agency seq
     * @param useYn     the use yn
     * @return the agency
     * @author [이소정]
     * @CreatedOn 2020. 7. 20. 오후 12:21:10
     * @Description
     */
    Optional<Agency> findByAgencySeqAndUseYn(long agencySeq, String useYn);

}
