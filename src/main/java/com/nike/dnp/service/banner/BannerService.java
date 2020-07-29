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
 * @CreatedOn 2020. 6. 22. 오후 2:40:43
 * @Description Banner(메인 비주얼) Service 작성
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {

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
     * @CreatedOn 2020. 7. 20. 오전 11:36:13
     * @Description 배너 상세(캐시)
     */
    public Banner getBanner() {
        Banner banner = (Banner) redisService.get("cache:visual");

        if (ObjectUtils.isEmpty(banner)) {
            banner = bannerRepository.findAll().get(0);
        }

        return banner;
    }

    /**
     * Find by id optional.
     *
     * @param bannerSeq the banner seq
     * @return the optional
     * @author [오지훈]
     * @CreatedOn 2020. 7. 28. 오후 5:21:37
     * @Description
     */
    public Optional<Banner> findById(final Long bannerSeq) {
        return bannerRepository.findById(bannerSeq);
    }

    /**
     * Find by id banner.
     *
     * @param bannerSeq the banner seq
     * @return the banner
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오전 11:34:05
     * @Description 배너 상세
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
     * @CreatedOn 2020. 7. 20. 오전 11:32:45
     * @Description 배너 등록
     */
    @Transactional
    public Banner save (final BannerSaveDTO bannerSaveDTO) {
        bannerSaveDTO.setImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        bannerSaveDTO.setMobileImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getMobileImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        Banner banner = bannerRepository.save(new Banner().saveOrUpdate(bannerSaveDTO));
        redisService.set("cache:visual", banner, 0);
        return banner;
    }

    /**
     * Update banner.
     *
     * @param bannerSeq       the banner seq
     * @param bannerSaveDTO the banner update dto
     * @return the banner
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오전 11:32:46
     * @Description 배너 수정
     */
    @Transactional
    public Banner update (final Long bannerSeq, final BannerSaveDTO bannerSaveDTO) {
        Banner banner = this.findByBannerSeq(bannerSeq);
        bannerSaveDTO.setImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        bannerSaveDTO.setMobileImageFilePhysicalName(S3Util.fileCopyAndOldFileDelete(bannerSaveDTO.getMobileImageFilePhysicalName(), ServiceCode.FileFolderEnumCode.BANNER.name()));
        banner.saveOrUpdate(bannerSaveDTO);
        redisService.delete("cache:visual");
        redisService.set("cache:visual", banner, 0);
        return banner;
    }

}
