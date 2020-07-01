package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import java.util.List;


/**
 * The Class Contents save dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 3:25:23
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsSaveDTO {

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드", hidden = true)
    private String topMenuCode;

    /**
     * 메뉴 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "menuCode", value = "메뉴 공통코드", required = true, example = "A_SP")
    private String menuCode;

    /**
     * 이미지 파일명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일명", required = true, example = "asset_main_img.jpg")
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", required = true, example = "500")
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", required = true, example = "8080/cdn/contnets/")
    private String imageFilePhysicalName;

    /**
     * 폴더명
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderName", value = "폴더명", required = true, example = "SP20 NSW NIKE DIRECT AM90")
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
     * 메모
     * @author [이소정]
     */
    @ApiModelProperty(name = "memo", value = "메모", example = "메모 입력\n메모 입력\n메모 입력\n")
    private String memo;

    /**
     * 콘텐트 파일 리스트
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsFileList", value = "콘텐츠 파일 리스트")
    private List<ContentsFileSaveDTO> contentsFileList;


}
