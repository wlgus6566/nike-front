package com.nike.dnp.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

/**
 * The Class Report file save dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 10. 오전 11:10:48
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportFileSaveDTO {

    /**
     * The File name
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileName", value = "파일명", example = "file.jpg", required = true)
    private String fileName;

    /**
     * The File size
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈", example = "150", required = true)
    private String fileSize;

    /**
     * The File physical name
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리명", example = "/upload/report/path", required = true)
    private String filePhysicalName;

}
