package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

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
     * 파일 순서
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1", required = true)
    private long fileOrder;

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
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;

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
     * The constant cdnUrl.
     */
    @ApiModelProperty(name = "cdnUrl", value = "cdnUrl", hidden = true)
    private static String cdnUrl;

    /**
     * Sets cdn url.
     *
     * @param cdnUrl the cdn url
     */
    @Value("${nike.file.cdnUrl:}")
    public void setCdnUrl(final String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    public String getThumbnailFilePhysicalName() {
        return this.cdnUrl + thumbnailFilePhysicalName;
    }



//    TODO[lsj] 상세페이지에서 필요 없어서 주석 추후 삭제 예정
//     by.2020.07.17 sojeong.lee
//    /**
//     * 파일 명
//     * @author [이소정]
//     */
//    @ApiModelProperty(name = "fileName", value = "파일 명")
//    private String fileName;
//
//    /**
//     * 파일 사이즈
//     * @author [이소정]
//     */
//    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
//    private Long fileSize;
//
//    /**
//     * 파일 물리 명
//     * @author [이소정]
//     */
//    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
//    private String filePhysicalName;
//
//    /**
//     * 다운로드 수
//     * @author [이소정]
//     */
//    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
//    private long downloadCount;

}
