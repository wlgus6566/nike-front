package com.nike.dnp.entity.report;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT_ANSWER")
public class ReportAnswer extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_SEQ")
    @ApiModelProperty(name = "answerSeq", value = "답글 시퀀스")
    private Long answerSeq;

    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "answerSeq", value = "답글 시퀀스")
    private Long reportSeq;
    @Column(name = "ANSWER_CONTENTS")
    private String answerContents;
    @Column(name = "USE_YN")
    private String useYn;
}
