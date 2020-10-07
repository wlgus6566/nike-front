package com.nike.dnp.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.time.LocalDateTime;


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

    /**
     * The User id
     *
     * @author [오지훈]
     */
    @ApiParam(value = "유저 ID", name = "userId", required = true, defaultValue = "test@nike.co.kr")
    private String userId;


    /**
     * 최종 수정일
     *
     * @author [오지훈]
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "updateDt", value = "최종 수정일", hidden = true)
    private LocalDateTime updateDt;

}
