package com.nike.dnp.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The Class Report answer save dto.
 *
 * @author [이소정]
 * @since 2020. 7. 10. 오전 11:11:31
 * @implNote  보고서 댓글(Feedback) 저장 DTO
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
    @NotNull(message = "reportFeedback.reportSeq")
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "2", required = true)
    private Long reportSeq;

    /**
     * The Answer contents
     * @author [이소정]
     */
    @Length(max=100)
    @NotBlank(message = "reportFeedback.answerContents")
    @ApiModelProperty(name = "answerContents", value = "답글 내용", example = "Attract window graphic 1을 추가로 시공했으면 합니다.", required = true)
    private String answerContents;

}
