package com.nike.dnp.entity.report;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

/**
 * The Class Report.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 7. 오후 2:34:04
 * @Description
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT")
public class Report extends BaseTimeEntity {

    /**
     * The Report seq
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_SEQ")
    private Long reportSeq;

    /**
     * The Report section code
     * @author [이소정]
     */
    @Column(name = "REPORT_SECTION_CODE")
    private String reportSectionCode;

    /**
     * The Report name
     * @author [이소정]
     */
    @Column(name = "REPORT_NAME")
    private String reportName;

    /**
     * The Read count
     * @author [이소정]
     */
    @Column(name = "READ_COUNT")
    private Long readCount;

    /**
     * The Use yn
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    private String useYn;

    @JsonManagedReference
    @OneToMany(mappedBy = "reportSeq")
    @ApiModelProperty(name="reportFileList", value = "리포트 파일 목록", required = true)
    private List<ReportFile> reportFileList;
}
