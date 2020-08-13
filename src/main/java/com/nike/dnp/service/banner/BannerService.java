package com.nike.dnp.service.banner;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.banner.BannerSaveDTO;
import com.nike.dnp.entity.banner.Banner;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.repository.banner.BannerRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.MessageUtil;
import com.nike.dnp.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;


/**
 * BannerService
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 2:40:43
 * @implNote Banner(메인 비주얼) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {

    /**
     * The constant BANNER_REDIS_KEY
     *
     * @author [오지훈]
     */
    private final static String BANNER_REDIS_KEY = "cache:visual";

    /**
     * The Redis service
     *
     * @author [오지훈]
     */
    private final RedisService redisService;

    /**
     * BannerRepository
     *
     * @author [오지훈]
     */
    private final BannerRepository bannerRepository;

    /**
     * Gets banner.
     *
     * @return the banner
     * @author [오지훈]
     * @since 2020. 7. 20. 오전 11:36:13
     * @implNote 배너 상세(캐시)
     */
    public Banner getBanner() {
        final Banner banner = (Banner) redisService.get(BANNER_REDIS_KEY);
        return ObjectUtils.isEmpty(banner) ? bannerRepository.findAllByUseYn(ServiceCode.YesOrNoEnumCode.Y.name()).get(0) : banner;
    }

    /**
     * Find banner banner.
     *
     * @return the banner
     * @author [오지훈]
     * @implNote 배너 상세(DB)
     * @since 2020. 8. 12. 오후 2:12:13
     */
    public Banner findBanner() {
        return bannerRepository.findAllByUseYn(ServiceCode.YesOrNoEnumCode.Y.name()).get(0);
    }

    /**
     * Find by id optional.
     *
     * @param bannerSeq the banner seq
     * @return the optional
     * @author [오지훈]
     * @since 2020. 7. 28. 오후 5:21:37
     * @implNote
     */
    public Optional<Banner> findById(final Long bannerSeq) {
        return bannerRepository.findByBannerSeqAndUseYn(bannerSeq, ServiceCode.YesOrNoEnumCode.Y.name());
    }

    /**
     * Find by id banner.
     *
     * @param bannerSeq the banner seq
     * @return the banner
     * @author [오지훈]
     * @since 2020. 7. 20. 오전 11:34:05
     * @implNote 배너 상세
     */
    public Banner findByBannerSeq(final Long bannerSeq) {
        return this.findById(bannerSeq).orElseThrow(
                () -> new CodeMessageHandleException(
                        FailCode.ExceptionError.NOT_FOUND.name()
                        , MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())));
    }

    /**
     * Save banner.
     *
     * @param bannerSaveDTO the banner update dto
     * @return the banner
     * @author [오지훈]
     * @since 2020. 7. 20. 오전 11:32:45
     * @implNote 배너 등록
     */
    @Transactional
    public Banner save (final BannerSaveDTO bannerSaveDTO) {
        bannerSaveDTO.setImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        bannerSaveDTO.setMobileImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getMobileImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        final Banner banner = bannerRepository.save(new Banner().saveOrUpdate(bannerSaveDTO));
        redisService.set(BANNER_REDIS_KEY, banner, 0);
        return banner;
    }

    /**
     * Update banner.
     *
     * @param bannerSeq       the banner seq
     * @param bannerSaveDTO the banner update dto
     * @return the banner
     * @author [오지훈]
     * @since 2020. 7. 20. 오전 11:32:46
     * @implNote 배너 수정
     */
    @Transactional
    public Banner update (final Long bannerSeq, final BannerSaveDTO bannerSaveDTO) {
        final Banner banner = this.findByBannerSeq(bannerSeq);
        bannerSaveDTO.setImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        bannerSaveDTO.setMobileImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getMobileImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        banner.saveOrUpdate(bannerSaveDTO);
        redisService.delete(BANNER_REDIS_KEY);
        redisService.set(BANNER_REDIS_KEY, banner, 0);
        return banner;
    }

    /**
     * Delete banner.
     *
     * @param bannerSeq the banner seq
     * @return the banner
     * @author [오지훈]
     * @implNoe 배너 삭제
     * @since 2020. 8. 11. 오후 12:01:46
     */
    @Transactional
    public Banner delete (final Long bannerSeq) {
        redisService.delete(BANNER_REDIS_KEY);
        return this.findByBannerSeq(bannerSeq).delete();
    }

}
