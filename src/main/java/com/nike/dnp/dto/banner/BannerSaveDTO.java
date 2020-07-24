package com.nike.dnp.dto.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * The Class Banner update dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 21. 오후 3:54:32
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class BannerSaveDTO {

    /**
     * 제목
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.title")
    @ApiModelProperty(name = "title", value = "제목", example = "EXPLORE THE BEST OF AIR MAX")
    private String title;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.contents")
    @ApiModelProperty(name = "contents", value = "내용"
            , example = "고무 힐 탭 로고, 머드가드 그리고 올드스쿨 카세트 스타일의 에어유닛으로 미래지향적인 실루엣을 가진  에어맥")
    private String contents;

    /**
     * 이미지_파일_명
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.pcImageFile")
    @ApiModelProperty(name = "imageFileName", value = "이미지_파일_명", example = "NikeAir.jpg")
    private String imageFileName;

    /**
     * 이미지_파일_사이즈
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.pcImageFile")
    @ApiModelProperty(name = "imageFileSize", value = "이미지_파일_사이즈", example = "123456")
    private String imageFileSize;

    /**
     * 이미지_파일_물리_명(이미지 경로)
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.pcImageFile")
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지_파일_물리_명(이미지 경로)", example = "/images/banner/NikeAir.jpg")
    private String imageFilePhysicalName;

    /**
     * 모바일_이미지_파일_명
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.mobileImageFile")
    @ApiModelProperty(name = "mobileImageFileName", value = "모바일_이미지_파일_명", example = "NikeAir.jpg")
    private String mobileImageFileName;

    /**
     * 모바일_이미지_파일_사이즈
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.mobileImageFile")
    @ApiModelProperty(name = "mobileImageFileSize", value = "모바일_이미지_파일_사이즈", example = "123456")
    private String mobileImageFileSize;

    /**
     * 모바일_이미지_파일_물리_명(이미지 경로)
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.mobileImageFile")
    @ApiModelProperty(name = "mobileImageFilePhysicalName", value = "모바일_이미지_파일_물리_명(이미지 경로)", example = "/images/banner/m-NikeAir.jpg")
    private String mobileImageFilePhysicalName;

    /**
     * 링크_URL_타입_공통코드
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.linkUrlTypeCode")
    @ApiModelProperty(name = "linkUrlTypeCode", value = "링크_URL_타입_공통코드", allowableValues = "ASSET,URL")
    private String linkUrlTypeCode;

    /**
     * 링크_URL
     *
     * @author [오지훈]
     */
    @NotBlank(message = "banner.linkUrl")
    @ApiModelProperty(name = "linkUrl", value = "링크_URL", example = "https://nikespace.com/asset/all")
    private String linkUrl;

}
