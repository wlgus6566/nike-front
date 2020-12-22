package com.nike.dnp.dto.report;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;


/**
 * The Class Report file search dto.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:33:41
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportFileSearchDTO {

    /**
     * 보고서 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "2", hidden = true)
    private Long reportSeq;


    /**
     * The Page
     *
     * @author [오지훈]
     * @defaultValue 0
     */
    @ApiParam(value = "페이지", name = "page", defaultValue = "0")
    private Integer page = 0;

    /**
     * The Size
     *
     * @author [오지훈]
     * @defaultValue 20
     */
    @ApiParam(value = "사이즈", name = "size", defaultValue = "30")
    private int size = 30;
}
