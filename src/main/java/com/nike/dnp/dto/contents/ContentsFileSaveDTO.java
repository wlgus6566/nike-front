package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;


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
    @Column(name = "FILE_SECTION_CODE")
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드", required = true, example = "GUIDE")
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드codeSaveDTO
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드", required = true, example = "VIDEO")
    private String fileKindCode;

    /**
     * 타이틀
     * @author [이소정]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "타이틀", example = "Attract window graphic 1")
    private String title;

    /**
     * url
     * @author [이소정]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "url", example = "www.nike.co.kr")
    private String url;

    /**
     * 파일 명
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
    private String filePhysicalName;

}
