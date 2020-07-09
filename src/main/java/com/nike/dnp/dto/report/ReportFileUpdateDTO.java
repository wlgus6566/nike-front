package com.nike.dnp.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Class Report file update dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 9. 오후 6:10:18
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportFileUpdateDTO {

    /**
     * The Report file seq
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스", required = true, example = "1")
    private Long reportFileSeq;

    /**
     * 보고서 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "1")
    private Long reportSeq;

    /**
     * The File name
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * The File size
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    private String fileSize;

    /**
     * The File physical name
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    private String filePhysicalName;

}
