package com.nike.dnp.dto.report;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * The Class Report answer result dto.
 *
 * @author [이소정]
 * @since 2020. 8. 13. 오후 7:53:42
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ReportAnswerResultDTO {

    /**
     * The Answer seq
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "answerSeq", value = "답글 시퀀스")
    private Long answerSeq;

    /**
     * The Report seq
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스", example = "3")
    private Long reportSeq;

    /**
     * The Answer contents
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "answerContents", value = "답글 내용", example = "Attract window graphic 1을 추가로 시공했으면 합니다.")
    private String answerContents;

    /**
     * 닉네임
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "nickname", value = "닉네임", example = "NIKE학동점")
    private String nickname;

}
