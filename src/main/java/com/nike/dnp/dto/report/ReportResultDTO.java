package com.nike.dnp.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.entity.report.ReportFile;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;


/**
 * The Class Report result dto.
 *
 * @author [이소정]
 * @since 2020. 7. 27. 오후 7:11:04
 * @implNote 보고서 결과 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportResultDTO {

    /**
     * 보고서 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportName", value = "보고서 시퀀스")
    private Long reportSeq;


    /**
     * 보고서 구분 코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportName", value = "보고서 구분 코드")
    private String reportSectionCode;

    /**
     * 보고서 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportName", value = "보고서 명")
    private String reportName;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "이미지 파일 물리명")
    private String imageFilePhysicalName;

    /**
     * The Read count
     * @author [이소정]
     */
    @ApiModelProperty(name = "readCount", value = "조회 수")
    private Long readCount;

    /**
     * 닉네임
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "nickname", value = "닉네임", required = true, example = "Nike이모션점")
    private String nickname;

    /**
     * The Report file list
     *
     * @author [이소정]
     */
    @ApiModelProperty(name="reportFileList", value = "보고서 파일 목록", required = true)
    private List<ReportFile> reportFileList;

    /**
     * Gets image file physical name.
     *
     * @return the image file physical name
     * @author [이소정]
     * @implNote url 추가
     * @since 2020. 8. 12. 오후 4:43:34
     */
    public String getImageFilePhysicalName() {
        return ObjectUtils.isEmpty(imageFilePhysicalName) ? imageFilePhysicalName : CloudFrontUtil.getCustomSignedUrl(imageFilePhysicalName);
    }
}
