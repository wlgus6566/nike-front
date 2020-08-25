package com.nike.dnp.service.banner;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.banner.BannerReturnDTO;
import com.nike.dnp.dto.banner.BannerSaveDTO;
import com.nike.dnp.entity.banner.Banner;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.repository.banner.BannerRepository;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.util.ObjectMapperUtil;
import com.nike.dnp.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
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
    public BannerReturnDTO getBanner() {
        BannerReturnDTO bannerDTO = (BannerReturnDTO) redisService.get(BANNER_REDIS_KEY);
        if (ObjectUtils.isEmpty(bannerDTO)) {
            bannerDTO = this.findBanner();
        }
        return bannerDTO;
    }

    /**
     * Find banner banner.
     *
     * @return the banner
     * @author [오지훈]
     * @implNote 배너 상세(DB)
     * @since 2020. 8. 12. 오후 2:12:13
     */
    public BannerReturnDTO findBanner() {
        final List<Banner> banners = bannerRepository.findAllByUseYnOrderByUpdateDt(ServiceCode.YesOrNoEnumCode.Y.name());
        return ObjectUtils.isEmpty(banners) ? new BannerReturnDTO() : ObjectMapperUtil.map(banners.get(0), BannerReturnDTO.class);
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
        return this.findById(bannerSeq).orElseThrow(NotFoundHandleException::new);
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
    public BannerReturnDTO save (final BannerSaveDTO bannerSaveDTO) {
        bannerSaveDTO.setImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        bannerSaveDTO.setMobileImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getMobileImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        final Banner banner = bannerRepository.save(new Banner().saveOrUpdate(bannerSaveDTO));
        final BannerReturnDTO bannerDTO = ObjectMapperUtil.map(banner, BannerReturnDTO.class);
        redisService.set(BANNER_REDIS_KEY, bannerDTO, 0);
        return bannerDTO;
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
    public BannerReturnDTO update (final Long bannerSeq, final BannerSaveDTO bannerSaveDTO) {
        final Banner banner = this.findByBannerSeq(bannerSeq);

        if (bannerSaveDTO.getImageFilePhysicalName().contains("temp/")) {
            bannerSaveDTO.setImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        } else {
            bannerSaveDTO.setImageFileName(banner.getImageFileName());
            bannerSaveDTO.setImageFileSize(banner.getImageFileSize());
            bannerSaveDTO.setImageFilePhysicalName(banner.getImageFilePhysicalName());
        }
        if (bannerSaveDTO.getMobileImageFilePhysicalName().contains("temp/")) {
            bannerSaveDTO.setMobileImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getMobileImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        } else {
            bannerSaveDTO.setMobileImageFilePhysicalName(banner.getMobileImageFilePhysicalName());
        }

        final BannerReturnDTO bannerDTO = ObjectMapperUtil.map(banner.saveOrUpdate(bannerSaveDTO), BannerReturnDTO.class);
        redisService.delete(BANNER_REDIS_KEY);
        redisService.set(BANNER_REDIS_KEY, bannerDTO, 0);
        return bannerDTO;
    }

    /**
     * Delete banner.
     *
     * @param bannerSeq the banner seq
     * @return the banner
     * @author [오지훈]
     * @implNote 배너 삭제
     * @since 2020. 8. 11. 오후 12:01:46
     */
    @Transactional
    public Banner delete (final Long bannerSeq) {
        redisService.delete(BANNER_REDIS_KEY);
        return this.findByBannerSeq(bannerSeq).delete();
    }

}
