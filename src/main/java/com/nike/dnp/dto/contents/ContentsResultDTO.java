package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Contents list dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 2:52:03
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsResultDTO {

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드")
    private String topMenuCode;

    /**
     * 2depth 메뉴 코드
     */
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드")
    private String menuCode;

    /**
     * 이미지 파일명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일명", example = "main_img.jpg")
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈",  example = "500")
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", example = "8080/cdn/contnets/")
    private String imageFilePhysicalName;

    /**
     * 폴더명
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderName", value = "폴더명", example = "SP20 NSW NIKE DIRECT AM90")
    private String folderName;

    /**
     * 폴더 내용
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderContents", value = "폴더 내용", required = true, example = "SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료")
    private String folderContents;

    /**
     * 캠페인 기간 구분 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignPeriodSectionCode", value = "캠페인 기간 구분 공통코드", required = true, example = "EVERY")
    private String campaignPeriodSectionCode;

    /**
     * 캠페인 시작 일시
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignBeginDt", value = "캠페인 시작 일시", example = "2020-06-01")
    private String campaignBeginDt;

    /**
     * 캠페인 종료 일시
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignEndDt", value = "캠페인 종료 일시", example = "2020-09-01")
    private String campaignEndDt;

    /**
     * 조회수
     * @author [이소정]
     */
    @ApiModelProperty(name = "readCount", value = "조회수")
    private Long readCount;


}
