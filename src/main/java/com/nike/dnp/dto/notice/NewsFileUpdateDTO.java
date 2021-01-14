package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * The Class News file update dto.
 *
 * @author [이소정]
 * @since 2021. 1. 8. 오후 12:42:18
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class NewsFileUpdateDTO extends BasicDTO {

    /**
     * 게시물_파일_시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "noticeFileSeq" , value = "게시물_파일_시퀀스")
    private Long noticeFileSeq;

    /**
     * 게시물_시퀀스
     *
     * @author [이소정]
    */
    @ApiModelProperty(name = "noticeArticleSeq", value ="게시물 시퀀스")
    private Long noticeArticleSeq;

    /**
     * 파일 컨텐츠 타입
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileContentType", value = "파일 컨텐츠 타입", example = "image/jpeg")
    private String fileContentType;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 파일 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
    private String filePhysicalName;

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
    @ApiModelProperty(name = "detailThumbnailFilePhysicalName", value ="상세 썸네일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFilePhysicalName;

    /**
     * 사용 여부
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "useYn", value = "사용 여부", hidden = true)
    private String useYn = "Y";

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류(FILE/VIDEO)")
    private String fileKindCode;

    /**
     * 타이틀
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "title", value = "타이틀")
    private String title;

    /**
     * url
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "url", value = "url")
    private String url;

}
