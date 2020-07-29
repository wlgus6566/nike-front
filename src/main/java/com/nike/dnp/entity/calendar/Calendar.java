package com.nike.dnp.entity.calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Calendar Entity.
 *
 * @author [김형욱]
 * @since 2020. 6. 29. 오후 8:30:58
 * @implNote 달력 Entity
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CALENDAR")
public class Calendar  implements Serializable {

    /**
     * 달력 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALENDAR_SEQ")
    @ApiModelProperty(name = "calendarSeq", value = "달력 시퀀스")
    private Long calendarSeq;

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @Column(name = "CALENDAR_SECTION_CODE")
    @ApiModelProperty(name = "calendarSectionCode", value = "메뉴 코드", required = true)
    private String calendarSectionCode;

    /**
     * 일정 명
     *
     * @author [오지훈]
     */
    @Column(name = "SCHEDULE_NAME")
    @ApiModelProperty(name = "scheduleName", value = "일정 명", required = true)
    private String scheduleName;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @Lob
    @Column(name = "CONTENTS")
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * 시작 일시
     *
     * @author [오지훈]
     */
    @Column(name = "BEGIN_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "beginDt", value = "시작 일시")
    private LocalDateTime beginDt;

    /**
     * 종료 일시
     *
     * @author [오지훈]
     */
    @Column(name = "END_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "endDt", value = "종료 일시")
    private LocalDateTime endDt;

    /**
     * 최초 작성자
     *
     * @author [김형욱]
     */
    @CreatedBy
    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true, required = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     *
     * @author [김형욱]
     */
    @LastModifiedBy
    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(name = "updaterSeq", value = "최종 수정자 시퀀스", hidden = true, required = true)
    private Long updaterSeq;

    /**
     * 최초 작성일
     *
     * @author [김형욱]
     */
    @Column(name = "REGISTRATION_DT")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @CreationTimestamp
    @ApiModelProperty(name = "registrationDt", value = "최초 작성일", hidden = true)
    private LocalDateTime registrationDt;

    /**
     * 최종 수정일
     *
     * @author [김형욱]
     */
    @Column(name = "UPDATE_DT")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @UpdateTimestamp
    @ApiModelProperty(name = "updateDt", value = "최종 수정일", hidden = true)
    private LocalDateTime updateDt;
}
