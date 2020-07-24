package com.nike.dnp.entity.report;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.dto.report.ReportSaveDTO;
import com.nike.dnp.dto.report.ReportUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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
     * 보고서 시퀀스
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportName", value = "보고서 시퀀스")
    private Long reportSeq;

    /**
     * 보고서 구분 코드
     * @author [이소정]
     */
    @Column(name = "REPORT_SECTION_CODE")
    @ApiModelProperty(name = "reportName", value = "보고서 구분 코드")
    private String reportSectionCode;

    /**
     * 보고서 명
     * @author [이소정]
     */
    @Column(name = "REPORT_NAME")
    @ApiModelProperty(name = "reportName", value = "보고서 명")
    private String reportName;

    /**
     * 이미지 파일 명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "이미지 파일 사이즈")
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "이미지 파일 물리명")
    private String imageFilePhysicalName;

    /**
     * The Read count
     * @author [이소정]
     */
    @Column(name = "READ_COUNT")
    @ApiModelProperty(name = "readCount", value = "조회 수")
    private Long readCount;

    /**
     * The Use yn
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * The Report file list
     * @author [이소정]
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "report")
    @ApiModelProperty(name="reportFileList", value = "보고서 파일 목록", required = true)
    private List<ReportFile> reportFileList;

//    /**
//     * The Report answer list
//     * @author [이소정]
//     */
//    @JsonManagedReference
//    @OneToMany(mappedBy = "report")
//    @ApiModelProperty(name = "reportAnswerList", value = "보고서 댓글 목록", required = true)
//    private List<ReportAnswer> reportAnswerList;

    /**
     * Save report.
     *
     * @param reportSaveDTO the report save dto
     * @return the report
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:33:21
     * @Description
     */
    @Transactional
    public Report save(final ReportSaveDTO reportSaveDTO) {
        log.info("Report.save");
        Report savedReport = new Report();
        savedReport.setReportSectionCode(reportSaveDTO.getReportSectionCode());
        savedReport.setReportName(reportSaveDTO.getReportName());
        savedReport.setImageFileName(reportSaveDTO.getImageFileName());
        savedReport.setImageFileSize(reportSaveDTO.getImageFileSize());
        savedReport.setImageFilePhysicalName(reportSaveDTO.getImageFilePhysicalName());
        savedReport.setReadCount(0l);
        savedReport.setUseYn("Y");
        return savedReport;
    }

    /**
     * Update.
     *
     * @param reportUpdateDTO the report update dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:30:48
     * @Description
     */
    @Transactional
    public void update(final ReportUpdateDTO reportUpdateDTO) {
        log.info("Report.update");
        this.reportSectionCode = reportUpdateDTO.getReportSectionCode();
        this.reportName = reportUpdateDTO.getReportName();
        this.imageFileName = reportUpdateDTO.getImageFileName();
        this.imageFileSize = reportUpdateDTO.getImageFileSize();
        this.imageFilePhysicalName = reportUpdateDTO.getImageFilePhysicalName();
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     */
    @Transactional
    public void updateUseYn(final String useYn) {
        log.info("Report.updateUseYn");
        this.useYn = useYn;
    }

    /**
     * Update read cound.
     *
     * @param readCount the read count
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:34:20
     * @Description
     */
    @Transactional
    public void updateReadCound(final Long readCount) {
        log.info("Report.updateReadCound");
        this.readCount = readCount + 1;
    }

}