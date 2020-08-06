package com.nike.dnp.entity.contents;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.report.Report;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Recent upload.
 *
 * @author [이소정]
 * @implNote 최근 업로드 폴더 entity
 * @since 2020. 7. 27. 오후 2:07:16
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_RECENT_UPLOAD")
public class RecentUpload extends BaseTimeEntity {


    /**
     * The Recent upload seq
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECENT_UPLOAD_SEQ")
    @ApiModelProperty(name = "recentUploadSeq", value ="최근 업로드 시퀀스", example = "1")
    private Long recentUploadSeq;

    /**
     * The Report seq
     *
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportSeq", value ="reportSeq", example = "example")
    private Long reportSeq;

    /**
     * The Contents seq
     *
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value ="contentsSeq", example = "example")
    private Long contentsSeq;

    /**
     * The Type cd
     *
     * @author [이소정]
     */
    @Column(name = "TYPE_CD")
    @ApiModelProperty(name = "typeCd", value ="typeCd", example = "example")
    private String typeCd;

    /**
     * The Contents.
     *
     * @author [이소정]
     */
    @ManyToOne
    @JoinColumn(name = "CONTENTS_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "contents", value ="contents")
    private Contents contents;

    /**
     * The Report.
     *
     * @author [이소정]
     */
    @ManyToOne
    @JoinColumn(name = "REPORT_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "report", value ="report")
    private Report report;

}
