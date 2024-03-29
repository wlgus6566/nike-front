package com.nike.dnp.dto.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The Class Alarm result dto.
 *
 * @author [이소정]
 * @implNote 알림 결과 DTO
 * @since 2020. 7. 24. 오후 8:08:11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class AlarmResultDTO {

    /**
     * 알림 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "alarmSeq", value ="알림 시퀀스", example = "1")
    private Long alarmSeq;

    /**
     * The Folder seq.
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderSeq", value ="폴더 시퀀스(콘텐츠, 리포트 시퀀스)", example = "4")
    private Long folderSeq;

    /**
     * 폴더명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderName", value = "폴더명", example = "SP20 NSW NIKE DIRECT AM90")
    private String folderName;

    /**
     * 유저 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "userSeq", value ="유저 시퀀스", example = "example")
    private Long userSeq;

    /**
     * 상태 타입(NEW, UPDATE, FEEDBACK)
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "typeAction", value ="상태 타입(NEW, UPDATE, FEEDBACK)", example = "example")
    private String typeAction;

    /**
     * 메뉴 타입 코드(ASSET, TOOLKIT, FOUNDATION, REPORT)
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "typeCd", value ="메뉴 타입 코드(ASSET, TOOLKIT, FOUNDATION, REPORT_MANAGE)", example = "example")
    private String typeCd;

    /**
     * 최초 작성일
     *
     * @author [오지훈]
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
    private LocalDateTime registrationDt;

    /**
     * 2depth 메뉴 코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드")
    private String menuCode;
}
