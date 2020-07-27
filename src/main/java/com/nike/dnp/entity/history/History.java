package com.nike.dnp.entity.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.report.Report;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class History.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 23. 오전 11:04:28
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Entity
@Table(name = "TB_HISTORY")
public class History extends BaseTimeEntity {

    /**
     * The History seq
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_SEQ")
    @ApiModelProperty(name = "historySeq", value ="히스토리 시퀀스", example = "1")
    private Long historySeq;

    /**
     * The Report seq
     *
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportSeq", value ="보고서 시퀀스", example = "3")
    private Long reportSeq;

    /**
     * The Contents seq
     *
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value ="콘텐츠 시퀀스", example = "4")
    private Long contentsSeq;

    /**
     * The Type cd
     *
     * @author [이소정]
     */
    @Column(name = "TYPE_CD")
    @ApiModelProperty(name = "typeCd", value ="타입 코드(ALL/ASSET/TOOLKIT/FOUNDATION/REPORT_MANAGE)", example = "ALL")
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
