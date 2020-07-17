package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ContentsFileResultDTO {

    /**
     * 컨텐츠 파일 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsFileSeq", value = "컨텐츠 파일 시퀀스")
    private Long contentsFileSeq;

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 파일 구분 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드 (ASSET/GUIDE/VIDEO)", required = true)
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true)
    private String fileKindCode;

    /**
     * 타이틀
     * @author [이소정]
     */
    @ApiModelProperty(name = "title", value = "타이틀")
    private String title;

    /**e
     * url
     * @author [이소정]
     */
    @ApiModelProperty(name = "url", value = "url")
    private String url;

    /**
     * 파일 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
    private String filePhysicalName;

    /**
     * 다운로드 수
     * @author [이소정]
     */
    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
    private long downloadCount;

    /**
     * 파일 순서
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1", required = true)
    private long fileOrder;

    /**
     * 사용여부
     * @author [이소정]
     */
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

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

}
