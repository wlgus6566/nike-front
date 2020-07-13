package com.nike.dnp.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportSaveDTO {

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
    @ApiModelProperty(name = "reportName", value = "보고서 명", example = "FA20 RN NIKE DIRECT PEGASUS 37 시공보고서", required = true)
    private String reportName;

    /**
     * 이미지 파일 명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일 명", example = "imageFile.jpg")
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", example = "500")
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", example = "/file/report/img")
    private String imageFilePhysicalName;

    /**
     * 보고서 파일 목록
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportFileSaveDTOList", value = "보고서 파일 목록")
    private List<ReportFileSaveDTO> reportFileSaveDTOList;

}
