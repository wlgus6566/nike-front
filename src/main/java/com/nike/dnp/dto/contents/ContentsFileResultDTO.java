package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.util.ObjectUtils;

/**
 * The Class Contents list dto.
 *
 * @author [이소정]
 * @implNote 컨텐츠 파일 결과 DTO
 * @since 2020. 7. 13. 오후 2:52:03
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
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsFileSeq", value = "컨텐츠 파일 시퀀스")
    private Long contentsFileSeq;

    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 파일 구분 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드 (ASSET/GUIDE/VIDEO)", required = true)
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true)
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

    /**
     * 파일 순서
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1", required = true)
    private long fileOrder;

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
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;

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
     * Gets file name.
     *
     * @return the file name
     * @author [이소정]
     * @implNote 파일명 특수기호 원복
     * @since 2020. 9. 7. 오후 5:56:01
     */
    public String getFileName() {
        if (!ObjectUtils.isEmpty(fileName)) {
            fileName = fileName.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            fileName = fileName.replaceAll("#40;", "(").replaceAll("#41;", ")");
            fileName = fileName.replaceAll("#39;", "'");
            return fileName;
        } else {
            return null;
        }
    }

    /**
     * Gets thumbnail file physical name.
     *
     * @return the thumbnail file physical name
     * @author [이소정]
     * @implNote signedUrl + 썸네일 파일 경로
     * @since 2020. 7. 30. 오후 3:43:38
     */
    public String getThumbnailFilePhysicalName() {
        return ObjectUtils.isEmpty(thumbnailFilePhysicalName) ? thumbnailFilePhysicalName : CloudFrontUtil.getCustomSignedUrl(thumbnailFilePhysicalName);
    }

    /**
     * Gets file physical name.
     *
     * @return the file physical name
     * @author [이소정]
     * @implNote signedUrl + 파일 경로
     * @since 2020. 8. 14. 오후 7:50:43
     */
    public String getFilePhysicalName() {
        return ObjectUtils.isEmpty(filePhysicalName) ? filePhysicalName : CloudFrontUtil.getCustomSignedUrl(filePhysicalName);
    }

    /**
     * Gets detail thumbnail file physical name.
     *
     * @return the detail thumbnail file physical name
     * @author [이소정]
     * @implNote signedUrl + 상세 이미지 경로
     * @since 2020. 8. 14. 오후 7:50:44
     */
    public String getDetailThumbnailFilePhysicalName() {
        //TODO[ojh] 2020/09/18 : 동영상 인코딩 이슈 해결 후 수정 예정
        if (this.getFileKindCode().equals(ServiceCode.ContentsFileKindCode.FILE.toString())
                && this.getFileExtension().toUpperCase().contains("VIDEO/")
                && ObjectUtils.isEmpty(detailThumbnailFilePhysicalName)) {
            return CloudFrontUtil.getCustomSignedUrl(filePhysicalName, 30);
        } else if (ObjectUtils.isEmpty(detailThumbnailFilePhysicalName)) {
            return detailThumbnailFilePhysicalName;
        } else {
            return CloudFrontUtil.getCustomSignedUrl(detailThumbnailFilePhysicalName, 30);
        }
    }

}
