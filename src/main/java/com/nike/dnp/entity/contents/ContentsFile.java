package com.nike.dnp.entity.contents;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsFileSaveDTO;
import com.nike.dnp.dto.contents.ContentsFileUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.exception.CodeMessageHandleException;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * The Class Contents file Entity.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 3:56:22
 * @Description
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "TB_CONTENTS_FILE")
public class ContentsFile extends BaseTimeEntity {

    /**
     * 컨텐츠 파일 시퀀스
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_FILE_SEQ")
    @ApiModelProperty(name = "contentsFileSeq", value = "컨텐츠 파일 시퀀스")
    private Long contentsFileSeq;

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 파일 구분 공통코드
     * @author [이소정]
     */
    @Column(name = "FILE_SECTION_CODE")
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드", required = true)
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드", required = true)
    private String fileKindCode;

    /**
     * 타이틀
     * @author [이소정]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "타이틀")
    private String title;

    /**e
     * url
     * @author [이소정]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "url")
    private String url;

    /**
     * 파일 명
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
    private String filePhysicalName;

    /**
     * 다운로드 수
     * @author [이소정]
     */
    @Column(name = "DOWNLOAD_COUNT")
    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
    private long downloadCount;

    /**
     * 파일 순서
     * @author [이소정]
     */
    @Column(name = "FILE_ORDER")
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1")
    private long fileOrder;

    /**
     * 사용여부
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
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
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "썸네일 파일 물리 명", example = "/cdn/file/path")
    private String thumbnailFilePhysicalName;

    /**
     * The Contents
     * @author [이소정]
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENTS_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "contents", value = "The Contents", hidden = true)
    private Contents contents;




    /**
     * Save contents file.
     *
     * @param savedContents       the saved contents
     * @param contentsFileSaveDTO the contents file save dto
     * @return the contents file
     * @author [이소정]
     * @CreatedOn 2020. 7. 1. 오전 11:24:43
     * @Description
     */
    @Transactional
    public ContentsFile save(Contents savedContents, ContentsFileSaveDTO contentsFileSaveDTO) {
        log.info("ContentsFile.save");
        ContentsFile contentsFile = new ContentsFile();

        contentsFile.setThumbnailFileName(contentsFileSaveDTO.getThumbnailFileName());
        contentsFile.setThumbnailFileSize(contentsFileSaveDTO.getThumbnailFileSize());
        contentsFile.setThumbnailFilePhysicalName(contentsFileSaveDTO.getThumbnailFilePhysicalName());

        contentsFile.setDownloadCount(0l);
        contentsFile.setUseYn("Y");
        contentsFile.setContentsSeq(savedContents.getContentsSeq());
        return applyContentsFile(contentsFile
                , contentsFileSaveDTO.getFileSectionCode()
                , contentsFileSaveDTO.getFileKindCode()
                , contentsFileSaveDTO.getFileName()
                , contentsFileSaveDTO.getFileSize()
                , contentsFileSaveDTO.getFilePhysicalName()
                , contentsFileSaveDTO.getTitle()
                , contentsFileSaveDTO.getUrl()
                , contentsFileSaveDTO.getThumbnailFileName()
                , contentsFileSaveDTO.getThumbnailFileSize()
                , contentsFileSaveDTO.getThumbnailFilePhysicalName()
                , contentsFileSaveDTO.getFileOrder());
    }

    /**
     * New contents file contents file.
     *
     * @param contentsSeq           the contents seq
     * @param contentsFileUpdateDTO the contents file update dto
     * @return the contents file
     * @author [이소정]
     * @CreatedOn 2020. 7. 6. 오후 5:52:49
     * @Description
     */
    @Transactional
    public ContentsFile newContentsFile(Long contentsSeq, ContentsFileUpdateDTO contentsFileUpdateDTO) {
        log.info("ContentsFile.newContentsFile");
        ContentsFile contentsFile = new ContentsFile();

        contentsFile.setDownloadCount(0l);
        contentsFile.setUseYn("Y");
        contentsFile.setContentsSeq(contentsSeq);
        return applyContentsFile(contentsFile
                , contentsFileUpdateDTO.getFileSectionCode()
                , contentsFileUpdateDTO.getFileKindCode()
                , contentsFileUpdateDTO.getFileName()
                , contentsFileUpdateDTO.getFileSize()
                , contentsFileUpdateDTO.getFilePhysicalName()
                , contentsFileUpdateDTO.getTitle()
                , contentsFileUpdateDTO.getUrl()
                , contentsFileUpdateDTO.getThumbnailFileName()
                , contentsFileUpdateDTO.getThumbnailFileSize()
                , contentsFileUpdateDTO.getThumbnailFilePhysicalName()
                , contentsFileUpdateDTO.getFileOrder());
    }

    /**
     * Apply contents file contents file.
     *
     * @param contentsFile              the contents file
     * @param fileSectionCode           the file section code
     * @param fileKindCode              the file kind code
     * @param fileName                  the file name
     * @param fileSize                  the file size
     * @param filePhysicalName          the file physical name
     * @param title                     the title
     * @param url                       the url
     * @param thumbnailFileName         the thumbnail file name
     * @param thumbnailFileSize         the thumbnail file size
     * @param thumbnailFilePhysicalName the thumbnail file physical name
     * @return the contents file
     * @author [이소정]
     * @CreatedOn 2020. 7. 7. 오전 10:41:43
     * @Description
     */
    private ContentsFile applyContentsFile(ContentsFile contentsFile
            , String fileSectionCode
            , String fileKindCode
            , String fileName
            , Long fileSize
            , String filePhysicalName
            , String title
            , String url
            , String thumbnailFileName
            , String thumbnailFileSize
            , String thumbnailFilePhysicalName
            , Long fileOrder
    ) {
        contentsFile.setFileSectionCode(fileSectionCode);
        contentsFile.setFileKindCode(fileKindCode);
        contentsFile.setFileOrder(fileOrder);

        if (ServiceEnumCode.ContentsFileKindCode.FILE.equals(fileKindCode)) {
            this.checkStringValidation(fileName, ErrorEnumCode.ContentsError.NOT_EXIST_FILE_NAME.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_NAME.getMessage());

            contentsFile.setFileName(fileName);
            contentsFile.setFileSize(fileSize);
            contentsFile.setFilePhysicalName(filePhysicalName);

            contentsFile.setThumbnailFileName(thumbnailFileName);
            contentsFile.setThumbnailFileSize(thumbnailFileSize);
            contentsFile.setThumbnailFilePhysicalName(thumbnailFilePhysicalName);

        } else {
            this.checkStringValidation(title, ErrorEnumCode.ContentsError.NOT_EXIST_FILE_TITLE.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_TITLE.getMessage());
            this.checkStringValidation(url, ErrorEnumCode.ContentsError.NOT_EXIST_FILE_URL.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_URL.getMessage());

            contentsFile.setTitle(title);
            contentsFile.setUrl(url);
        }

        return contentsFile;
    }

    /**
     * Update.
     *
     * @param contentsFileUpdateDTO the contents file update dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 3. 오후 5:27:06
     * @Description
     */
    @Transactional
    public void update(final ContentsFileUpdateDTO contentsFileUpdateDTO) {
        log.info("ContentsFile.update");
        this.fileSectionCode = contentsFileUpdateDTO.getFileSectionCode();
        this.fileKindCode = contentsFileUpdateDTO.getFileKindCode();
        this.fileOrder = contentsFileUpdateDTO.getFileOrder();
        String fileKindCode = contentsFileUpdateDTO.getFileKindCode();

        if (ServiceEnumCode.ContentsFileKindCode.FILE.equals(fileKindCode)) {
            this.checkStringValidation(contentsFileUpdateDTO.getFileName(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_NAME.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_NAME.getMessage());

            this.fileName = contentsFileUpdateDTO.getFileName();
            this.fileSize = contentsFileUpdateDTO.getFileSize();
            this.filePhysicalName = contentsFileUpdateDTO.getFilePhysicalName();
            this.thumbnailFileName = contentsFileUpdateDTO.getThumbnailFileName();
            this.thumbnailFileSize = contentsFileUpdateDTO.getThumbnailFileSize();
            this.thumbnailFilePhysicalName = contentsFileUpdateDTO.getThumbnailFilePhysicalName();

            this.title = null;
            this.url = null;
        } else {
            this.checkStringValidation(contentsFileUpdateDTO.getTitle(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_TITLE.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_TITLE.getMessage());
            this.checkStringValidation(contentsFileUpdateDTO.getUrl(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_URL.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_URL.getMessage());

            this.title = contentsFileUpdateDTO.getTitle();
            this.url = contentsFileUpdateDTO.getUrl();

            this.fileName = null;
            this.fileSize = 0l;
            this.filePhysicalName = null;
            this.thumbnailFileName = null;
            this.thumbnailFileSize = null;
            this.thumbnailFilePhysicalName = null;
        }
    }

    /**
     * Update download count.
     *
     * @param downloadCount the download count
     * @author [이소정]
     * @CreatedOn 2020. 7. 3. 오후 5:28:11
     * @Description
     */
    @Transactional
    public void updateDownloadCount(final Long downloadCount) {
        log.info("ContentsFile.updateDownloadCount");
        this.downloadCount = downloadCount + 1;
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @CreatedOn 2020. 7. 6. 오후 12:02:25
     * @Description
     */
    @Transactional
    public void updateUseYn(final String useYn) {
        this.useYn = useYn;
    }

    /**
     * Check string validation boolean.
     *
     * @param value        the value
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @return the boolean
     * @author [이소정]
     * @CreatedOn 2020. 6. 26. 오후 5:30:51
     * @Description
     */
    @Transactional
    public Boolean checkStringValidation(String value, String errorCode, String errorMessage) {
        if (value.isEmpty() || value.trim().isEmpty()) {
            throw new CodeMessageHandleException(errorCode, errorMessage);
        }
        return true;
    }

}
