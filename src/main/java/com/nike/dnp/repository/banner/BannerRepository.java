package com.nike.dnp.repository.banner;

import com.nike.dnp.entity.banner.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BannerRepository
 *
 * @author [오지훈]
 * @since 2020. 7. 7. 오후 5:02:48
 * @implNote Banner(메인 비주얼) Repository Interface 작성
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}
