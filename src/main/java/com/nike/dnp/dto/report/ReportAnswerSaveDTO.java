package com.nike.dnp.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Report answer save dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 10. 오전 11:11:31
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportAnswerSaveDTO {

    /**
     * The Report seq
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "2", required = true)
    private Long reportSeq;

    /**
     * The Answer contents
     * @author [이소정]
     */
    @ApiModelProperty(name = "answerContents", value = "답글 내용", example = "Attract window graphic 1을 추가로 시공했으면 합니다.", required = true)
    private String answerContents;

}
