package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;


/**
 * The Class Contents file update dto.
 *
 * @author [이소정]
 * @implNote 컨텐츠 파일 수정 DTO
 * @since 2020. 7. 3. 오후 3:39:34
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsFileUpdateDTO {

    /**
     * 컨텐츠 파일 시퀀스
     *
     * @author [이소정]
     */
    @NotEmpty(message = "contentsFile.contentsFileSeq")
    @ApiModelProperty(name = "contentsFileSeq", value = "컨텐츠 파일 시퀀스", required = true, example = "7")
    private Long contentsFileSeq;


    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @NotEmpty(message = "contentsFile.contentsSeq")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스", example = "10")
    private Long contentsSeq;

    /**
     * 파일 구분 공통코드
     *
     * @author [이소정]
     */
    @NotEmpty(message = "contentsFile.fileSectionCode")
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드(ATTRACT/ENGAGE/RN/TR/NSW/FB/BB/JD/KIDS/OTHERS/DIGITAL/GUIDE/VIDEO/VR)", required = true, example = "GUIDE")
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드codeSaveDTO
     *
     * @author [이소정]
     */
    @NotEmpty(message = "contentsFile.fileKindCode")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true, example = "VIDEO")
    private String fileKindCode;

    /**
     * 타이틀
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "title", value = "타이틀", example = "Attract window graphic 1")
    private String title;

    /**
     * url
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "url", value = "url", example = "www.nike.co.kr")
    private String url;

    /**
     * 파일 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명", example = "graphic_file_name.jpg")
    private String fileName;

    /**
     * 파일 사이즈
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "600")
    private Long fileSize;

    /**
     * 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명", example = "/temp/graphic_img.jpg")
    private String filePhysicalName;

    /**
     * 파일 컨텐츠 타입
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension", value = "파일 컨텐츠 타입", example = "image/jpeg")
    private String fileContentType;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 상세 썸네일 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "detailThumbnailFileName", value ="상세 썸네일 명", example = "graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFileName;

    /**
     * 상세 썸네일 사이즈
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "detailThumbnailFileSize", value ="상세 썸네일 사이즈", example = "700")
    private String detailThumbnailFileSize;

    /**
     * 상세 썸네일 물리 경로
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "detailThumbnailFilePhysicalName", value ="상세 썸네일 물리 명", example = "/temp/graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFilePhysicalName;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFileName", value = "썸네일 명", example = "graphic_file_name_thumbnail.jpg")
    private String thumbnailFileName;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "300")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "/temp/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;

    /**
     * 파일 순서
     *
     * @author [이소정]
     */
    @NotEmpty(message = "contentsFile.fileOrder")
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1")
    private Long fileOrder;

}
