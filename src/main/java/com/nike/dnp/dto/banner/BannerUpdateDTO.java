package com.nike.dnp.dto.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class BannerUpdateDTO {

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

}
