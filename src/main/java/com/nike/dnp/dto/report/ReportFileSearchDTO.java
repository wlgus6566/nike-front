package com.nike.dnp.dto.report;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
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
public class ReportFileSearchDTO extends SearchDTO {

    /**
     * 보고서 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "2", hidden = true)
    private Long reportSeq;
}
