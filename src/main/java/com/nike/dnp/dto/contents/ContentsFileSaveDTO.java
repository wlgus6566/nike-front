package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * The type Contents file save dto.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsFileSaveDTO {

    /**
     * 파일 구분 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드(ASSET/GUIDE/VIDEO)", required = true, example = "GUIDE")
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드codeSaveDTO
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true, example = "FILE")
    private String fileKindCode;

    /**
     * 타이틀
     * @author [이소정]
     */
    @ApiModelProperty(name = "title", value = "타이틀", example = "Attract window graphic 1")
    private String title;

    /**
     * url
     * @author [이소정]
     */
    @ApiModelProperty(name = "url", value = "url", example = "www.nike.co.kr")
    private String url;

    /**
     * 파일 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명", example = "graphic_file_name.jpg")
    private String fileName;

    /**
     * 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "600")
    private Long fileSize;

    /**
     * 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명", example = "/cdn/file/path")
    private String filePhysicalName;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFileName", value = "썸네일 명", example = "graphic_file_name_thumbnail.jpg")
    private String thumbnailFileName;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "300")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "/cdn/file/path")
    private String thumbnailFilePhysicalName;

    /**
     * 파일 순서
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1")
    private Long fileOrder;

}
