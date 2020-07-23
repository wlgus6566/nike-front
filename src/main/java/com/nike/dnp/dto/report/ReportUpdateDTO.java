package com.nike.dnp.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * The Class Report update dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 9. 오후 6:09:16
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportUpdateDTO {

    /**
     * 보고서 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportName", value = "보고서 시퀀스", required = true, example = "1")
    private Long reportSeq;

    /**
     * 구분 코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSectionCode", value = "구분 코드", required = true, example = "SP")
    private String reportSectionCode;

    /**
     * 보고서 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportName", value = "보고서 명", example = "Update FA20 RN NIKE DIRECT PEGASUS 37 시공보고서", required = true)
    private String reportName;

    /**
     * 이미지 파일 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일 명", example = "update_imageFile.jpg")
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", example = "1500")
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", example = "/file/report/img/update")
    private String imageFilePhysicalName;

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", required = true, example = "1", hidden = true)
    private Long authSeq;

    /**
     * 보고서 파일 목록
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportFileSaveDTOList", value = "보고서 파일 목록")
    private List<ReportFileUpdateDTO> reportFileUpdateDTOList;

}
