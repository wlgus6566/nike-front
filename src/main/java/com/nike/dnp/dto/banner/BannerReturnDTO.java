package com.nike.dnp.dto.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * BannerReturnDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:09:56
 * @implNote Banner(메인 비주얼) Return DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BannerReturnDTO {

    /**
     * 배너 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "bannerSeq", value = "배너 시퀀스", hidden = true)
    private Long bannerSeq;

    /**
     * 제목
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "title", value = "제목", required = true)
    private String title;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * 이미지_파일_명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "imageFileName", value = "이미지_파일_명", required = true)
    private String imageFileName;

    /**
     * 이미지_파일_사이즈
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "imageFileSize", value = "이미지_파일_사이즈", required = true)
    private String imageFileSize;

    /**
     * 이미지_파일_물리_명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지_파일_물리_명", required = true)
    private String imageFilePhysicalName;

    /**
     * 모바일_이미지_파일_명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "mobileImageFileName", value = "모바일_이미지_파일_명", required = true)
    private String mobileImageFileName;

    /**
     * 모바일_이미지_파일_사이즈
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "mobileImageFileSize", value = "모바일_이미지_파일_사이즈", required = true)
    private String mobileImageFileSize;

    /**
     * 모바일_이미지_파일_물리_명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "mobileImageFilePhysicalName", value = "모바일_이미지_파일_물리_명", required = true)
    private String mobileImageFilePhysicalName;

    /**
     * 링크_URL_타입_공통코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "linkUrlTypeCode", value = "링크_URL_타입_공통코드")
    private String linkUrlTypeCode;

    /**
     * 링크_URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "linkUrl", value = "링크_URL", required = true)
    private String linkUrl;

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "useYn", value = "사용_여부", required = true)
    private String useYn;

    /**
     * PC 이미지 전체 URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "pcImageUrl", value = "사용_여부", required = true)
    private String pcImageUrl;

    /**
     * MOBILE 이미지 전체 URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "mobileImageUrl", value = "사용_여부", required = true)
    private String mobileImageUrl;

    /**
     * Gets pc image url.
     *
     * @return the pc image url
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 8. 24. 오후 7:23:34
     */
    public String getPcImageUrl() {
        return CloudFrontUtil.getCustomSignedUrl(imageFilePhysicalName);
    }

    /**
     * Gets mobile image url.
     *
     * @return the mobile image url
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 8. 24. 오후 7:23:35
     */
    public String getMobileImageUrl() {
        return CloudFrontUtil.getCustomSignedUrl(mobileImageFilePhysicalName);
    }

//    /**
//     * Gets image file physical name.
//     *
//     * @return the image file physical name
//     * @author [오지훈]
//     * @implNote [Description 작성]
//     * @since 2020. 8. 24. 오후 4:09:53
//     */
//    public String getImageFilePhysicalName() {
//        return ObjectUtils.isEmpty(imageFilePhysicalName) ? ""
//                : imageFilePhysicalName.startsWith("http") ? imageFilePhysicalName
//                : CloudFrontUtil.getCustomSignedUrl(imageFilePhysicalName);
//    }
//
//    /**
//     * Gets mobile image file physical name.
//     *
//     * @return the mobile image file physical name
//     * @author [오지훈]
//     * @implNote [Description 작성]
//     * @since 2020. 8. 24. 오후 4:09:54
//     */
//    public String getMobileImageFilePhysicalName() {
//        return ObjectUtils.isEmpty(mobileImageFilePhysicalName) ? ""
//                : mobileImageFilePhysicalName.startsWith("http") ? mobileImageFilePhysicalName
//                : CloudFrontUtil.getCustomSignedUrl(mobileImageFilePhysicalName);
//    }

}
