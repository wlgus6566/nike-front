package com.nike.dnp.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * The Class Report basket result dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 17. 오후 5:54:19
 * @Description
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
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리명")
    private String filePhysicalName;



}
