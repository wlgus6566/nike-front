package com.nike.dnp.repository.banner;

import com.nike.dnp.entity.banner.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * BannerRepository
 *
 * @author [오지훈]
 * @since 2020. 7. 7. 오후 5:02:48
 * @implNote Banner(메인 비주얼) Repository Interface 작성
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    /**
     * Find all by use yn list.
     *
     * @param useYn the use yn
     * @return the list
     * @author [오지훈]
     * @implNote 사용 여부 : Y > 조건 목록 조회
     * @since 2020. 8. 11. 오후 12:03:55
     */
    List<Banner> findAllByUseYn(final String useYn);

    /**
     * Find by banner seq and use yn optional.
     *
     * @param bannerSeq the banner seq
     * @param useYn     the use yn
     * @return the optional
     * @author [오지훈]
     * @implNote 사용 여부 : Y > 단일 조회
     * @since 2020. 8. 11. 오후 12:07:23
     */
    Optional<Banner> findByBannerSeqAndUseYn(final Long bannerSeq, final String useYn);

}
