package com.nike.dnp.entity.alarm;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.report.Report;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * The Class Alarm.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 24. 오후 7:13:34
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_ALARM")
public class Alarm extends BaseTimeEntity {

    /**
     * 알림 시퀀스
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALARM_SEQ")
    @ApiModelProperty(name = "alarmSeq", value ="알림 시퀀스", example = "1")
    private Long alarmSeq;

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value ="컨텐츠 시퀀스", example = "4")
    private Long contentsSeq;

    /**
     * 보고서 시퀀스
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportSeq", value ="보고서 시퀀스", example = "2")
    private Long reportSeq;

    /**
     * 유저 시퀀스
     * @author [이소정]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value ="유저 시퀀스", example = "example")
    private Long userSeq;

    /**
     * 상태 타입(NEW, UPDATE, FEEDBACK)
     * @author [이소정]
     */
    @Column(name = "TYPE_ACTION")
    @ApiModelProperty(name = "typeAction", value ="상태 타입(NEW, UPDATE, FEEDBACK)", example = "example")
    private String typeAction;

    /**
     * 메뉴 타입 코드(ASSET, TOOLKIT, FOUNDATION, REPORT)
     * @author [이소정]
     */
    @Column(name = "TYPE_CD")
    @ApiModelProperty(name = "typeCd", value ="메뉴 타입 코드(ASSET, TOOLKIT, FOUNDATION, REPORT)", example = "example")
    private String typeCd;

    /**
     * The Contents.
     */
    @ManyToOne
    @JoinColumn(name = "CONTENTS_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "contents", value ="contents")
    private Contents contents;

    /**
     * The Report.
     */
    @ManyToOne
    @JoinColumn(name = "REPORT_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "report", value ="report")
    private Report report;

}
