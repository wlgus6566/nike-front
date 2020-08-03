package com.nike.dnp.entity.report;

import com.nike.dnp.dto.report.ReportAnswerSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * The Class Report answer.
 *
 * @author [이소정]
 * @implNote 보고서 feedback entity
 * @since 2020. 7. 10. 오전 11:05:42
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT_ANSWER")
public class ReportAnswer extends BaseTimeEntity {

    /**
     * The Answer seq
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_SEQ")
    @ApiModelProperty(name = "answerSeq", value = "답글 시퀀스")
    private Long answerSeq;

    /**
     * The Report seq
     *
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스")
    private Long reportSeq;

    /**
     * The Answer contents
     *
     * @author [이소정]
     */
    @Column(name = "ANSWER_CONTENTS")
    @ApiModelProperty(name = "answerContents", value = "답글 내용")
    private String answerContents;

    /**
     * The Use yn
     *
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * Save report answer.
     *
     * @param reportAnswerSaveDTO the report answer save dto
     * @return the report answer
     * @author [이소정]
     * @implNote 보고서 feedback 저장
     * @since 2020. 7. 10. 오전 11:20:05
     */
    public ReportAnswer save(final ReportAnswerSaveDTO reportAnswerSaveDTO) {
        log.info("ReportAnswer.save");
        final ReportAnswer saveReportAnswer = new ReportAnswer();
        saveReportAnswer.setReportSeq(reportAnswerSaveDTO.getReportSeq());
        saveReportAnswer.setAnswerContents(reportAnswerSaveDTO.getAnswerContents());
        saveReportAnswer.setUseYn("Y");
        return saveReportAnswer;
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @implNote 보고서 feedback 사용여부 수정
     * @since 2020. 7. 10. 오후 5:47:03
     */
    public void updateUseYn(final String useYn) {
        log.info("ReportAnswer.updateUseYn");
        this.useYn = useYn;
    }


}
