package com.nike.dnp.entity.contents;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.contents.ContentsFileSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Locale;

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
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드(ASSET/GUIDE/VIDEO)", required = true)
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
     * Gets file physical name.
     *
     * @return the file physical name
     * @author [이소정]
     * @implNote cdnUrl + filePhysicalName
     * @since 2020. 7. 30. 오후 3:49:08
     */
    public String getFilePhysicalName() {
        return ObjectUtils.isEmpty(filePhysicalName) ? filePhysicalName : CloudFrontUtil.getCustomSignedUrl(filePhysicalName);
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
        return ObjectUtils.isEmpty(thumbnailFilePhysicalName) ? thumbnailFilePhysicalName : CloudFrontUtil.getCustomSignedUrl(thumbnailFilePhysicalName);
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
        return ObjectUtils.isEmpty(detailThumbnailFilePhysicalName) ? detailThumbnailFilePhysicalName : CloudFrontUtil.getCustomSignedUrl(detailThumbnailFilePhysicalName);
    }


    /**
     * Save contents file.
     *
     * @param contentsSeq         the contents seq
     * @param contentsFileSaveDTO the contents file save dto
     * @return the contents file
     * @author [이소정]
     * @implNote 콘텐츠 파일 저장
     * @since 2020. 7. 1. 오전 11:24:43
     */
    public ContentsFile save(Long contentsSeq, ContentsFileSaveDTO contentsFileSaveDTO) {
        log.info("ContentsFile.save");
        ContentsFile contentsFile = new ContentsFile();

        contentsFile.setThumbnailFileName(contentsFileSaveDTO.getThumbnailFileName());
        contentsFile.setThumbnailFileSize(contentsFileSaveDTO.getThumbnailFileSize());
        contentsFile.setThumbnailFilePhysicalName(contentsFileSaveDTO.getThumbnailFilePhysicalName());

        contentsFile.setDownloadCount(0l);
        contentsFile.setUseYn("Y");
        contentsFile.setContentsSeq(contentsSeq);

        boolean isFile = ServiceCode.ContentsFileKindCode.FILE.toString().equals(contentsFileSaveDTO.getFileKindCode());

        contentsFile.setFileSectionCode(contentsFileSaveDTO.getFileSectionCode());
        contentsFile.setFileKindCode(contentsFileSaveDTO.getFileKindCode());
        contentsFile.setFileOrder(contentsFileSaveDTO.getFileOrder());

        contentsFile.setFileContentType(isFile ? contentsFileSaveDTO.getFileContentType() : null);
        contentsFile.setFileExtension(isFile ? contentsFileSaveDTO.getFileExtension().toUpperCase(Locale.KOREA) : null);

        contentsFile.setFileName(isFile ? contentsFileSaveDTO.getFileName() : null);
        contentsFile.setFileSize(isFile ? contentsFileSaveDTO.getFileSize() : null);
        contentsFile.setFilePhysicalName(isFile ? contentsFileSaveDTO.getFilePhysicalName() : null);
        contentsFile.setThumbnailFileName(isFile ? contentsFileSaveDTO.getThumbnailFileName() : null);
        contentsFile.setThumbnailFileSize(isFile ? contentsFileSaveDTO.getThumbnailFileSize() : null);
        contentsFile.setThumbnailFilePhysicalName(isFile ? contentsFileSaveDTO.getThumbnailFilePhysicalName() : null);
        contentsFile.setDetailThumbnailFileName(isFile ? contentsFileSaveDTO.getDetailThumbnailFileName() : null);
        contentsFile.setDetailThumbnailFileSize(isFile ? contentsFileSaveDTO.getDetailThumbnailFileSize() : null);
        contentsFile.setDetailThumbnailFilePhysicalName(isFile ? contentsFileSaveDTO.getDetailThumbnailFilePhysicalName() : null);

        contentsFile.setTitle(!isFile ? contentsFileSaveDTO.getTitle() : null);
        contentsFile.setUrl(!isFile ? contentsFileSaveDTO.getUrl() : null);

        return contentsFile;
    }

    /**
     * Update.
     *
     * @param contentsFileSaveDTO the contents file save dto
     * @author [이소정]
     * @implNote 콘텐츠 파일 수정
     * @since 2020. 7. 3. 오후 5:27:06
     */
    public void update(final ContentsFileSaveDTO contentsFileSaveDTO) {
        log.info("ContentsFile.update");

        boolean isFile = ServiceCode.ContentsFileKindCode.FILE.toString().equals(contentsFileSaveDTO.getFileKindCode());

        this.fileSectionCode = contentsFileSaveDTO.getFileSectionCode();
        this.fileKindCode = contentsFileSaveDTO.getFileKindCode();
        this.fileOrder = contentsFileSaveDTO.getFileOrder();

        this.fileContentType = isFile ? contentsFileSaveDTO.getFileContentType() : null;
        this.fileExtension = isFile ? contentsFileSaveDTO.getFileExtension().toUpperCase(Locale.KOREA) : null;

        if (this.checkTempFile(contentsFileSaveDTO.getFilePhysicalName())) {
            this.fileName = isFile ? contentsFileSaveDTO.getFileName() : null;
            this.fileSize = isFile ? contentsFileSaveDTO.getFileSize() : null;
            this.filePhysicalName = isFile ? contentsFileSaveDTO.getFilePhysicalName() : null;
        }

        if (this.checkTempFile(contentsFileSaveDTO.getThumbnailFilePhysicalName())) {
            this.thumbnailFileName = isFile ? contentsFileSaveDTO.getThumbnailFileName() : null;
            this.thumbnailFileSize = isFile ? contentsFileSaveDTO.getThumbnailFileSize() : null;
            this.thumbnailFilePhysicalName = isFile ? contentsFileSaveDTO.getThumbnailFilePhysicalName() : null;
        }

        if (this.checkTempFile(contentsFileSaveDTO.getDetailThumbnailFilePhysicalName())) {
            this.detailThumbnailFileName = isFile ? contentsFileSaveDTO.getDetailThumbnailFileName() : null;
            this.detailThumbnailFileSize = isFile ? contentsFileSaveDTO.getDetailThumbnailFileSize() : null;
            this.detailThumbnailFilePhysicalName = isFile ? contentsFileSaveDTO.getDetailThumbnailFilePhysicalName() : null;
        }

        this.title = !isFile ? contentsFileSaveDTO.getTitle() : null;
        this.url = isFile ? null : contentsFileSaveDTO.getUrl();
    }

    /**
     * Check temp file boolean.
     *
     * @param physicalName the physical name
     * @return the boolean
     * @author [이소정]
     * @implNote temp 파일 여부 확인
     * @since 2020. 8. 13. 오후 6:06:44
     */
    public boolean checkTempFile(final String physicalName) {
        if (!ObjectUtils.isEmpty(physicalName) && physicalName.contains("/temp/")) {
            return true;
        } else {
            return false;
        }
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
