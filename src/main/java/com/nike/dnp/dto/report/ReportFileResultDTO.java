package com.nike.dnp.dto.report;

import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;


/**
 * The Class Report file result dto.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:38:24
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportFileResultDTO {

    /**
     * The Report file seq
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스", example = "1")
    private Long reportFileSeq;

    /**
     * 보고서 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "2", hidden = true)
    private Long reportSeq;

    /**
     * The File name
     *
     * @author [이소정]
     */
    @NotBlank(message = "report.file")
    @ApiModelProperty(name = "fileName", value = "파일명", example = "file.jpg", required = true)
    private String fileName;

    /**
     * The File size
     *
     * @author [이소정]
     */
    @NotBlank(message = "report.file")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "150", required = true)
    private String fileSize;

    /**
     * The File physical name
     *
     * @author [이소정]
     */
    @NotBlank(message = "report.file")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리명", example = "http://cdnUrl/file/report/graphic_file_name.jpg", required = true)
    private String filePhysicalName;

    /**
     * 파일 컨텐츠 타입
     *
     * @author [이소정]
     */
    @NotBlank(message = "report.file")
    @ApiModelProperty(name = "fileContentType", value = "파일 컨텐츠 타입", example = "image/jpeg")
    private String fileContentType;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @NotBlank(message = "report.file")
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
    @ApiModelProperty(name = "detailThumbnailFilePhysicalName", value ="상세 썸네일 물리 명", example = "http://cdnUrl/file/report/graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFilePhysicalName;


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
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "100")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/report/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;

    /**
     * Gets file name.
     *
     * @return the file name
     * @author [이소정]
     * @implNote 파일 명 특수기호
     * @since 2020. 9. 7. 오후 6:19:08
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
     * Gets detail thumbnail file physical name.
     *
     * @return the detail thumbnail file physical name
     * @author [이소정]
     * @implNote cdnUrl + detailThumbnailFilePhysicalName
     * @since 2020. 7. 30. 오후 3:53:23
     */
    public String getDetailThumbnailFilePhysicalName() {
        return ObjectUtils.isEmpty(detailThumbnailFilePhysicalName) ? detailThumbnailFilePhysicalName : CloudFrontUtil.getSignedUrl(detailThumbnailFilePhysicalName);
    }

    /**
     * Gets thumbnail file physical name.
     *
     * @return the thumbnail file physical name
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 14. 오후 7:48:12
     */
    public String getThumbnailFilePhysicalName() {
        return ObjectUtils.isEmpty(thumbnailFilePhysicalName) ? thumbnailFilePhysicalName : CloudFrontUtil.getSignedUrl(thumbnailFilePhysicalName);
    }

    /**
     * Gets file physical name.
     *
     * @return the file physical name
     * @author [이소정]
     * @implNote
     * @since 2020. 8. 14. 오후 7:50:15
     */
    public String getFilePhysicalName() {
        return CloudFrontUtil.getSignedUrl(filePhysicalName);
    }

}
