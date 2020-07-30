package com.nike.dnp.entity.contents;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.contents.ContentsFileSaveDTO;
import com.nike.dnp.dto.contents.ContentsFileUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.util.S3Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

/**
 * The Class Contents file Entity.
 *
 * @author [이소정]
 * @implNote 콘텐츠 파일 entity
 * @since 2020. 6. 24. 오후 3:56:22
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
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_FILE_SEQ")
    @ApiModelProperty(name = "contentsFileSeq", value = "컨텐츠 파일 시퀀스")
    private Long contentsFileSeq;

    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 파일 구분 공통코드
     *
     * @author [이소정]
     */
    @Column(name = "FILE_SECTION_CODE")
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드 (ASSET/GUIDE/VIDEO)", required = true)
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO/VR)", required = true)
    private String fileKindCode;

    /**
     * 타이틀
     *
     * @author [이소정]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "타이틀")
    private String title;

    /**
     * e
     * url
     *
     * @author [이소정]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "url")
    private String url;

    /**
     * 파일 명
     *
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     *
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     *
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
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
     * 파일 순서
     *
     * @author [이소정]
     */
    @Column(name = "FILE_ORDER")
    @ApiModelProperty(name = "fileOrder", value = "파일 순서", example = "1", required = true)
    private long fileOrder;

    /**
     * 사용 여부
     *
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
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
     * The Contents
     *
     * @author [이소정]
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENTS_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "contents", value = "The Contents", hidden = true)
    private Contents contents;

    /**
     * The constant cdnUrl.
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "cdnUrl", value = "cdnUrl", hidden = true)
    private static String cdnUrl;

    /**
     * Sets cdn url.
     *
     * @param cdnUrl the cdn url
     * @author [이소정]
     * @implNote cdnUrl 셋팅
     * @since 2020. 7. 30. 오후 3:49:08
     */
    @Value("${nike.file.cdnUrl:}")
    public void setCdnUrl(final String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    /**
     * Gets file physical name.
     *
     * @return the file physical name
     * @author [이소정]
     * @implNote cdnUrl + filePhysicalName
     * @since 2020. 7. 30. 오후 3:49:08
     */
    public String getFilePhysicalName() {
        return this.cdnUrl + filePhysicalName;
    }

    /**
     * Gets thumbnail file physical name.
     *
     * @return the thumbnail file physical name
     * @author [이소정]
     * @implNote cdnUrl + thumbnailFilePhysicalName
     * @since 2020. 7. 30. 오후 3:49:08
     */
    public String getThumbnailFilePhysicalName() {
        return this.cdnUrl + thumbnailFilePhysicalName;
    }

    /**
     * Gets detail thumbnail file physical name.
     *
     * @return the detail thumbnail file physical name
     * @author [이소정]
     * @implNote cdnUrl + detailThumbnailFilePhysicalName
     * @since 2020. 7. 30. 오후 3:49:08
     */
    public String getDetailThumbnailFilePhysicalName() {
        return this.cdnUrl + detailThumbnailFilePhysicalName;
    }


    /**
     * Save contents file.
     *
     * @param savedContents       the saved contents
     * @param contentsFileSaveDTO the contents file save dto
     * @return the contents file
     * @author [이소정]
     * @implNote 콘텐츠 파일 저장
     * @since 2020. 7. 1. 오전 11:24:43
     */
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
                , contentsFileSaveDTO.getFileContentType()
                , contentsFileSaveDTO.getFileExtension()
                , contentsFileSaveDTO.getTitle()
                , contentsFileSaveDTO.getUrl()
                , contentsFileSaveDTO.getThumbnailFileName()
                , contentsFileSaveDTO.getThumbnailFileSize()
                , contentsFileSaveDTO.getThumbnailFilePhysicalName()
                , contentsFileSaveDTO.getDetailThumbnailFileName()
                , contentsFileSaveDTO.getDetailThumbnailFileSize()
                , contentsFileSaveDTO.getDetailThumbnailFilePhysicalName()
                , contentsFileSaveDTO.getFileOrder());
    }

    /**
     * New contents file contents file.
     *
     * @param contentsSeq           the contents seq
     * @param contentsFileUpdateDTO the contents file update dto
     * @return the contents file
     * @author [이소정]
     * @implNote 새로운 콘텐츠 파일 저장
     * @since 2020. 7. 6. 오후 5:52:49
     */
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
                , contentsFileUpdateDTO.getFileContentType()
                , contentsFileUpdateDTO.getFileExtension()
                , contentsFileUpdateDTO.getTitle()
                , contentsFileUpdateDTO.getUrl()
                , contentsFileUpdateDTO.getThumbnailFileName()
                , contentsFileUpdateDTO.getThumbnailFileSize()
                , contentsFileUpdateDTO.getThumbnailFilePhysicalName()
                , contentsFileUpdateDTO.getDetailThumbnailFileName()
                , contentsFileUpdateDTO.getDetailThumbnailFileSize()
                , contentsFileUpdateDTO.getDetailThumbnailFilePhysicalName()
                , contentsFileUpdateDTO.getFileOrder());
    }

    /**
     * Apply contents file contents file.
     *
     * @param contentsFile                    the contents file
     * @param fileSectionCode                 the file section code
     * @param fileKindCode                    the file kind code
     * @param fileName                        the file name
     * @param fileSize                        the file size
     * @param filePhysicalName                the file physical name
     * @param fileContentType                 the file content type
     * @param fileExtension                   the file extension
     * @param title                           the title
     * @param url                             the url
     * @param thumbnailFileName               the thumbnail file name
     * @param thumbnailFileSize               the thumbnail file size
     * @param thumbnailFilePhysicalName       the thumbnail file physical name
     * @param detailThumbnailFileName         the detail thumbnail file name
     * @param detailThumbnailFileSize         the detail thumbnail file size
     * @param detailThumbnailFilePhysicalName the detail thumbnail file physical name
     * @param fileOrder                       the file order
     * @return the contents file
     * @author [이소정]
     * @implNote 콘텐츠 파일 셋팅
     * @since 2020. 7. 7. 오전 10:41:43
     */
    private ContentsFile applyContentsFile(ContentsFile contentsFile
            , String fileSectionCode
            , String fileKindCode
            , String fileName
            , Long fileSize
            , String filePhysicalName
            , String fileContentType
            , String fileExtension
            , String title
            , String url
            , String thumbnailFileName
            , String thumbnailFileSize
            , String thumbnailFilePhysicalName
            , String detailThumbnailFileName
            , String detailThumbnailFileSize
            , String detailThumbnailFilePhysicalName
            , Long fileOrder
    ) {
        boolean isFile = ServiceCode.ContentsFileKindCode.FILE.toString().equals(fileKindCode);

        contentsFile.setFileSectionCode(fileSectionCode);
        contentsFile.setFileKindCode(fileKindCode);
        contentsFile.setFileOrder(fileOrder);

        contentsFile.setFileContentType(isFile ? fileContentType : null);
        contentsFile.setFileExtension(isFile ? fileExtension.toUpperCase() : null);

        contentsFile.setFileName(isFile ? fileName : null);
        contentsFile.setFileSize(isFile ? fileSize : null);
        contentsFile.setFilePhysicalName(isFile ? filePhysicalName : null);
        contentsFile.setThumbnailFileName(isFile ? thumbnailFileName : null);
        contentsFile.setThumbnailFileSize(isFile ? thumbnailFileSize : null);
        contentsFile.setThumbnailFilePhysicalName(isFile ? thumbnailFilePhysicalName : null);
        contentsFile.setDetailThumbnailFileName(isFile ? detailThumbnailFileName : null);
        contentsFile.setDetailThumbnailFileSize(isFile ? detailThumbnailFileSize : null);
        contentsFile.setDetailThumbnailFilePhysicalName(isFile ? detailThumbnailFilePhysicalName : null);

        contentsFile.setTitle(!isFile ? title : null);
        contentsFile.setUrl(!isFile ? url : null);

        return contentsFile;
    }

    /**
     * Update.
     *
     * @param contentsFileUpdateDTO the contents file update dto
     * @author [이소정]
     * @implNote 콘텐츠 파일 수정
     * @since 2020. 7. 3. 오후 5:27:06
     */
    public void update(final ContentsFileUpdateDTO contentsFileUpdateDTO) {
        log.info("ContentsFile.update");

        boolean isFile = ServiceCode.ContentsFileKindCode.FILE.toString().equals(contentsFileUpdateDTO.getFileKindCode());

        this.fileSectionCode = contentsFileUpdateDTO.getFileSectionCode();
        this.fileKindCode = contentsFileUpdateDTO.getFileKindCode();
        this.fileOrder = contentsFileUpdateDTO.getFileOrder();

        this.fileContentType = isFile ? contentsFileUpdateDTO.getFileContentType() : null;
        this.fileExtension = isFile ? contentsFileUpdateDTO.getFileExtension().toUpperCase() : null;

        this.fileName = isFile ? contentsFileUpdateDTO.getFileName() : null;
        this.fileSize = isFile ? contentsFileUpdateDTO.getFileSize() : null;
        this.filePhysicalName = isFile ? contentsFileUpdateDTO.getFilePhysicalName() : null;
        this.thumbnailFileName = isFile ? contentsFileUpdateDTO.getThumbnailFileName() : null;
        this.thumbnailFileSize = isFile ? contentsFileUpdateDTO.getThumbnailFileSize() : null;
        this.thumbnailFilePhysicalName = isFile ? contentsFileUpdateDTO.getThumbnailFilePhysicalName() : null;
        this.detailThumbnailFileName = isFile ? contentsFileUpdateDTO.getDetailThumbnailFileName() : null;
        this.detailThumbnailFileSize = isFile ? contentsFileUpdateDTO.getDetailThumbnailFileSize() : null;
        this.detailThumbnailFilePhysicalName = isFile ? contentsFileUpdateDTO.getDetailThumbnailFilePhysicalName() : null;

        this.title = !isFile ? contentsFileUpdateDTO.getTitle() : null;
        this.url = isFile ? null : contentsFileUpdateDTO.getUrl();
    }

    /**
     * Update download count.
     *
     * @param downloadCount the download count
     * @author [이소정]
     * @implNote 콘텐츠 파일 다운로드 수 1 더하기
     * @since 2020. 7. 3. 오후 5:28:11
     */
    public void updateDownloadCount(final Long downloadCount) {
        log.info("ContentsFile.updateDownloadCount");
        this.downloadCount = downloadCount + 1;
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @implNote 콘텐츠 파일 사용여부 수정
     * @since 2020. 7. 6. 오후 12:02:25
     */
    public void updateUseYn(final String useYn) {
        this.useYn = useYn;
    }

}
