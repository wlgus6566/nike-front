package com.nike.dnp.entity.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.dto.report.ReportFileUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_REPORT_FILE")
public class ReportFile extends BaseTimeEntity {

    /**
     * The Report file seq
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_FILE_SEQ")
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스")
    private Long reportFileSeq;

    /**
     * 보고서 시퀀스
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스")
    private Long reportSeq;

//    /**
//     * The File kind code
//     * @author [이소정]
//     */
//    @Column(name = "FILE_KIND_CODE")
//    private String fileKindCode;

    /**
     * The File name
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * The File size
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private String fileSize;

    /**
     * The File physical name
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리명")
    private String filePhysicalName;

    /**
     * The Download count
     * @author [이소정]
     */
    @Column(name = "DOWNLOAD_COUNT")
    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
    private Long downloadCount;

    /**
     * The Use yn.
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * The Report
     * @author [이소정]
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "report", value = "The Report", hidden = true)
    private Report report;

    /**
     * Save report file.
     *
     * @param reportFileSaveDTO the report file save dto
     * @return the report file
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:47:08
     * @Description
     */
    @Transactional
    public ReportFile save(final ReportFileSaveDTO reportFileSaveDTO) {
        final ReportFile savedReportFile = new ReportFile();
        savedReportFile.setFileName(reportFileSaveDTO.getFileName());
        savedReportFile.setFileSize(reportFileSaveDTO.getFileSize());
        savedReportFile.setFilePhysicalName(reportFileSaveDTO.getFilePhysicalName());
        savedReportFile.setDownloadCount(0l);
        return savedReportFile;
    }

    /**
     * New report file report file.
     *
     * @param reportFileUpdateDTO the report file update dto
     * @return the report file
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:47:01
     * @Description
     */
    @Transactional
    public ReportFile newReportFile(final Long reportSeq, final ReportFileUpdateDTO reportFileUpdateDTO) {
        final ReportFile savedReportFile = new ReportFile();
        savedReportFile.setReportSeq(reportSeq);
        savedReportFile.setFileName(reportFileUpdateDTO.getFileName());
        savedReportFile.setFileSize(reportFileUpdateDTO.getFileSize());
        savedReportFile.setFilePhysicalName(reportFileUpdateDTO.getFilePhysicalName());
        savedReportFile.setDownloadCount(0l);
        return savedReportFile;
    }

    /**
     * Update.
     *
     * @param reportFileUpdateDTO the report file update dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:50:26
     * @Description
     */
    @Transactional
    public void update(final ReportFileUpdateDTO reportFileUpdateDTO) {
        this.fileName = reportFileUpdateDTO.getFileName();
        this.fileSize = reportFileUpdateDTO.getFileSize();
        this.filePhysicalName = reportFileUpdateDTO.getFilePhysicalName();
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:50:24
     * @Description
     */
    @Transactional
    public void updateUseYn(final String useYn) {
        log.info("Report.updateUseYn");
        this.useYn = useYn;
    }

    /**
     * Update download count.
     *
     * @param downloadCount the download count
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:47:39
     * @Description
     */
    @Transactional
    public void updateDownloadCount(final Long downloadCount) {
        this.downloadCount = downloadCount + 1;
    }
}
