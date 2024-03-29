package com.nike.dnp.entity.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Locale;

/**
 * The Class Report file.
 *
 * @author [이소정]
 * @implNote 보고서 파일 entity
 * @since 2020. 7. 24. 오전 10:06:05
 */
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
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_FILE_SEQ")
    @ApiModelProperty(name = "reportFileSeq", value = "보고서 파일 시퀀스")
    private Long reportFileSeq;

    /**
     * 보고서 시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "REPORT_SEQ")
    @ApiModelProperty(name = "reportSeq", value = "보고서 시퀀스")
    private Long reportSeq;

    /**
     * The File name
     *
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * The File size
     *
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private String fileSize;

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "FILE", hidden = true)
    private String fileKindCode;

    /**
     * The File physical name
     *
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리명")
    private String filePhysicalName;

    /**
     * 파일 컨텐츠 타입
     *
     * @author [이소정]
     */
    @Column(name = "FILE_CONTENT_TYPE")
    @ApiModelProperty(name = "fileContentType", value = "파일 컨텐츠 타입", example = "image/jpeg")
    private String fileContentType;

    /**
     * 파일 확장자
     *
     * @author [이소정]
     */
    @Column(name = "FILE_EXTENSION")
    @ApiModelProperty(name = "fileExtension", value = "파일 확장자", example = "JPG")
    private String fileExtension;

    /**
     * 다운로드 수
     *
     * @author [이소정]
     */
    @Column(name = "DOWNLOAD_COUNT")
    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
    private long downloadCount;


    /**
     * 사용 여부
     *
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_NAME")
    @ApiModelProperty(name = "thumbnailFileName", value = "썸네일 명", example = "graphic_file_name_thumbnail.jpg")
    private String thumbnailFileName;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_SIZE")
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "300")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     *
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;


    /**
     * 상세 썸네일 명
     *
     * @author [이소정]
     */
    @Column(name = "DETAIL_THUMBNAIL_FILE_NAME")
    @ApiModelProperty(name = "detailThumbnailFileName", value ="상세 썸네일 명", example = "graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFileName;

    /**
     * 상세 썸네일 사이즈
     *
     * @author [이소정]
     */
    @Column(name = "DETAIL_THUMBNAIL_FILE_SIZE")
    @ApiModelProperty(name = "detailThumbnailFileSize", value ="상세 썸네일 사이즈", example = "700")
    private String detailThumbnailFileSize;

    /**
     * 상세 썸네일 물리 경로
     *
     * @author [이소정]
     */
    @Column(name = "DETAIL_THUMBNAIL_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "detailThumbnailFilePhysicalName", value ="상세 썸네일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFilePhysicalName;

    /**
     * The Report
     *
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
     * @param reportSeq         the report seq
     * @param reportFileSaveDTO the report file save dto
     * @return the report file
     * @author [이소정]
     * @implNote 보고서 파일 저장
     * @since 2020. 7. 8. 오후 5:47:08
     */
    public ReportFile save(final Long reportSeq, final ReportFileSaveDTO reportFileSaveDTO) {
        log.info("ReportFile.save");
        final ReportFile saveReportFile = new ReportFile();
        saveReportFile.setReportSeq(reportSeq);
        saveReportFile.setFileName(reportFileSaveDTO.getFileName());
        saveReportFile.setFileSize(reportFileSaveDTO.getFileSize());
        saveReportFile.setFilePhysicalName(reportFileSaveDTO.getFilePhysicalName());

        saveReportFile.setFileContentType(reportFileSaveDTO.getFileContentType());
        saveReportFile.setFileExtension(reportFileSaveDTO.getFileExtension().toUpperCase(Locale.KOREA));

        saveReportFile.setThumbnailFileName(reportFileSaveDTO.getThumbnailFileName());
        saveReportFile.setThumbnailFileSize(reportFileSaveDTO.getThumbnailFileSize());
        saveReportFile.setThumbnailFilePhysicalName(reportFileSaveDTO.getThumbnailFilePhysicalName());

        saveReportFile.setDetailThumbnailFileName(reportFileSaveDTO.getDetailThumbnailFileName());
        saveReportFile.setDetailThumbnailFileSize(reportFileSaveDTO.getDetailThumbnailFileSize());
        saveReportFile.setDetailThumbnailFilePhysicalName(reportFileSaveDTO.getDetailThumbnailFilePhysicalName());

        saveReportFile.setFileKindCode(ServiceCode.ContentsFileKindCode.FILE.toString());
        saveReportFile.setDownloadCount(0l);
        saveReportFile.setUseYn("Y");
        return saveReportFile;
    }

    /**
     * Update.
     *
     * @param reportFileSaveDTO the report file save dto
     * @author [이소정]
     * @implNote 보고서 파일 수정
     * @since 2020. 7. 9. 오후 6:50:26
     */
    public void update(final ReportFileSaveDTO reportFileSaveDTO) {
        log.info("ReportFile.update");
        if (!ObjectUtils.isEmpty(reportFileSaveDTO.getFilePhysicalName()) && reportFileSaveDTO.getFilePhysicalName().contains("/temp/")) {
            this.fileName = reportFileSaveDTO.getFileName();
            this.fileSize = reportFileSaveDTO.getFileSize();
            this.filePhysicalName = reportFileSaveDTO.getFilePhysicalName();
        }
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @implNote 보고서 파일 사용여부 수정
     * @since 2020. 7. 9. 오후 6:50:24
     */
    public void updateUseYn(final String useYn) {
        log.info("ReportFile.updateUseYn");
        this.useYn = useYn;
    }

    /**
     * Update download count.
     *
     * @param downloadCount the download count
     * @author [이소정]
     * @implNote 보고서 다운로드 수 1 더하기
     * @since 2020. 7. 8. 오후 5:47:39
     */
    public void updateDownloadCount(final Long downloadCount) {
        log.info("ReportFile.updateDownloadCount");
        this.downloadCount = downloadCount + 1;
    }
}
