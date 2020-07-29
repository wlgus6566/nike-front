package com.nike.dnp.entity.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.dto.report.ReportFileUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * The Class Report file.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 24. 오전 10:06:05
 * @Description
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
     * @author [이소정]
     */
    @Column(name = "DOWNLOAD_COUNT")
    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
    private long downloadCount;


    /**
     * 사용 여부
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_NAME")
    @ApiModelProperty(name = "thumbnailFileName", value = "썸네일 명", example = "graphic_file_name_thumbnail.jpg")
    private String thumbnailFileName;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_SIZE")
    @ApiModelProperty(name = "thumbnailFileSize", value = "썸네일 파일 사이즈", example = "300")
    private String thumbnailFileSize;

    /**
     * 썸네일 파일 물리 명
     * @author [이소정]
     */
    @Column(name = "THUMBNAIL_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_thumbnail.jpg")
    private String thumbnailFilePhysicalName;


    /**
     * 상세 썸네일 명
     */
    @Column(name = "DETAIL_THUMBNAIL_FILE_NAME")
    @ApiModelProperty(name = "detailThumbnailFileName", value ="상세 썸네일 명", example = "graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFileName;

    /**
     * 상세 썸네일 사이즈
     */
    @Column(name = "DETAIL_THUMBNAIL_FILE_SIZE")
    @ApiModelProperty(name = "detailThumbnailFileSize", value ="상세 썸네일 사이즈", example = "700")
    private String detailThumbnailFileSize;

    /**
     * 상세 썸네일 물리 경로
     */
    @Column(name = "DETAIL_THUMBNAIL_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "detailThumbnailFilePhysicalName", value ="상세 썸네일 물리 명", example = "http://cdnUrl/file/contents/graphic_file_name_detail_thumbnail.jpg")
    private String detailThumbnailFilePhysicalName;

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
     * @param reportSeq         the report seq
     * @param reportFileSaveDTO the report file save dto
     * @return the report file
     * @author [이소정]
     * @CreatedOn 2020. 7. 8. 오후 5:47:08
     * @Description
     */
    public ReportFile save(final Long reportSeq, final ReportFileSaveDTO reportFileSaveDTO) {
        return newReportFile(
                reportSeq
                , reportFileSaveDTO.getFileName()
                , reportFileSaveDTO.getFileSize()
                , reportFileSaveDTO.getFilePhysicalName()
                , reportFileSaveDTO.getFileContentType()
                , reportFileSaveDTO.getFileExtension()
                , reportFileSaveDTO.getThumbnailFileName()
                , reportFileSaveDTO.getThumbnailFileSize()
                , reportFileSaveDTO.getThumbnailFilePhysicalName()
                , reportFileSaveDTO.getDetailThumbnailFileName()
                , reportFileSaveDTO.getDetailThumbnailFileSize()
                , reportFileSaveDTO.getDetailThumbnailFilePhysicalName()
        );
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
    public ReportFile updateNewFile(final Long reportSeq, final ReportFileUpdateDTO reportFileUpdateDTO) {
        return newReportFile(
                reportSeq
                , reportFileUpdateDTO.getFileName()
                , reportFileUpdateDTO.getFileSize()
                , reportFileUpdateDTO.getFilePhysicalName()
                , reportFileUpdateDTO.getFileContentType()
                , reportFileUpdateDTO.getFileExtension()
                , reportFileUpdateDTO.getThumbnailFileName()
                , reportFileUpdateDTO.getThumbnailFileSize()
                , reportFileUpdateDTO.getThumbnailFilePhysicalName()
                , reportFileUpdateDTO.getDetailThumbnailFileName()
                , reportFileUpdateDTO.getDetailThumbnailFileSize()
                , reportFileUpdateDTO.getDetailThumbnailFilePhysicalName()
        );
    }

    /**
     * New report file report file.
     *
     * @param reportSeq        the report seq
     * @param fileName         the file name
     * @param fileSize         the file size
     * @param filePhysicalName the file physical name
     * @return the report file
     * @author [이소정]
     * @CreatedOn 2020. 7. 10. 오후 5:39:30
     * @Description
     */
    private ReportFile newReportFile(
            final Long reportSeq
            , final String fileName
            , final String fileSize
            , final String filePhysicalName
            , final String fileContentType
            , final String fileExtension
            , final String thumbnailFileName
            , final String thumbnailFileSize
            , final String thumbnailFilePhysicalName
            , final String detailThumbnailFileName
            , final String detailThumbnailFileSize
            , final String detailThumbnailFilePhysicalName
    ) {

        final ReportFile saveReportFile = new ReportFile();
        saveReportFile.setReportSeq(reportSeq);
        saveReportFile.setFileName(fileName);
        saveReportFile.setFileSize(fileSize);
        saveReportFile.setFilePhysicalName(filePhysicalName);

        saveReportFile.setFileContentType(fileContentType);
        saveReportFile.setFileExtension(fileExtension.toUpperCase());

        saveReportFile.setThumbnailFileName(thumbnailFileName);
        saveReportFile.setThumbnailFileSize(thumbnailFileSize);
        saveReportFile.setThumbnailFilePhysicalName(thumbnailFilePhysicalName);

        saveReportFile.setDetailThumbnailFileName(detailThumbnailFileName);
        saveReportFile.setDetailThumbnailFileSize(detailThumbnailFileSize);
        saveReportFile.setDetailThumbnailFilePhysicalName(detailThumbnailFilePhysicalName);


        saveReportFile.setDownloadCount(0l);
        saveReportFile.setUseYn("Y");
        return saveReportFile;
    }

    /**
     * Update.
     *
     * @param reportFileUpdateDTO the report file update dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 9. 오후 6:50:26
     * @Description
     */
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
    public void updateUseYn(final String useYn) {
        log.info("Report.updateUseYn");
        this.useYn = useYn;
    }
//
//    보고서파일은 다운로드수 조회 X by.sojeong.lee 2020.07.20
//    /**
//     * Update download count.
//     *
//     * @param downloadCount the download count
//     * @author [이소정]
//     * @CreatedOn 2020. 7. 8. 오후 5:47:39
//     * @Description
//     */
//    public void updateDownloadCount(final Long downloadCount) {
//        this.downloadCount = downloadCount + 1;
//    }
}
