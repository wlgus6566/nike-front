package com.nike.dnp.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;


/**
 * The Class Report basket result dto.
 *
 * @author [이소정]
 * @since 2020. 7. 17. 오후 5:54:19
 * @implNote 보고서 장바구니 결과 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ReportBasketResultDTO {

    /**
     * The Report basket seq
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportBasketSeq", value = "보고서 장바구니 시퀀스", example = "1")
    private Long reportBasketSeq;

    /**
     * The User seq
     * @author [이소정]
     */
    @ApiModelProperty(name = "userSeq", value = "등록자 시퀀스", example = "1")
    private Long userSeq;

    /**
     * The Report file seq
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스", example = "1")
    private Long reportFileSeq;

    /**
     * The File name
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * The File size
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private String fileSize;

    /**
     * The File physical name
     * @author [이소정]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리명")
    private String thumbnailFilePhysicalName;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true)
    private String fileKindCode;

    public String getThumbnailFilePhysicalName() {
        return CloudFrontUtil.getCustomSignedUrl(thumbnailFilePhysicalName);
    }

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


}
