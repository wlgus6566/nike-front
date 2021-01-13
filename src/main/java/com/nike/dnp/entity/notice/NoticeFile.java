package com.nike.dnp.entity.notice;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.notice.CustomerFileSaveDTO;
import com.nike.dnp.dto.report.ReportFileSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;


/**
 * The Class Notice file.
 *
 * @author [이소정]
 * @since 2020. 12. 16. 오후 6:30:09
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "TB_NOTICE_FILE")
public class NoticeFile extends BaseTimeEntity {

    /**
     * 게시물_파일_시퀀스
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_FILE_SEQ")
    @ApiModelProperty(name = "noticeFileSeq" , value = "게시물_파일_시퀀스")
    private Long noticeFileSeq;


    /**
     * 게시물_시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "NOTICE_ARTICLE_SEQ")
    @ApiModelProperty(name = "noticeArticleSeq", value ="게시물 시퀀스")
    private Long noticeArticleSeq;

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
     * 사용 여부
     *
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * 파일 종류 공통코드
     *
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드(FILE/VIDEO)")
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
     * url
     *
     * @author [이소정]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "url")
    private String url;

    /**
     * 주문 상품 정보
     *
     * @author [이소정]
     */
    @ManyToOne
    @JoinColumn(name="NOTICE_ARTICLE_SEQ",insertable = false,updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "게시물", value = "게시물")
    private NoticeArticle noticeArticle;


    /**
     * Save notice file notice file.
     *
     * @param customerFileSaveDTO the customer file save dto
     * @return the notice file
     * @author [이소정]
     * @implNote 게시물 파일 저장
     * @since 2020. 12. 16. 오후 6:57:15
     */
    public NoticeFile saveNoticeFile(final CustomerFileSaveDTO customerFileSaveDTO) {
        NoticeFile noticeFile = new NoticeFile();
        noticeFile.setNoticeArticleSeq(customerFileSaveDTO.getNoticeArticleSeq());

        boolean isFile = ServiceCode.NoticeFileKindCode.FILE.toString().equals(customerFileSaveDTO.getFileKindCode()) ? true : false;
        
        noticeFile.setFileKindCode(customerFileSaveDTO.getFileKindCode());

        noticeFile.setFileContentType(isFile ? customerFileSaveDTO.getFileContentType() : null);
        noticeFile.setFileExtension(isFile ? customerFileSaveDTO.getFileExtension() : null);
        noticeFile.setFileName(isFile ? customerFileSaveDTO.getFileName(): null);
        noticeFile.setFileSize(isFile ? customerFileSaveDTO.getFileSize(): null);
        noticeFile.setFilePhysicalName(isFile ? customerFileSaveDTO.getFilePhysicalName(): null);
        noticeFile.setDetailThumbnailFileName(isFile ? customerFileSaveDTO.getDetailThumbnailFileName() : null);
        noticeFile.setDetailThumbnailFileSize(isFile ? customerFileSaveDTO.getDetailThumbnailFileSize() : null);
        noticeFile.setDetailThumbnailFilePhysicalName(isFile ? customerFileSaveDTO.getDetailThumbnailFilePhysicalName() : null);

        noticeFile.setTitle(isFile ? null : customerFileSaveDTO.getTitle());
        noticeFile.setUrl(isFile ? null : customerFileSaveDTO.getUrl());

        noticeFile.setUseYn(customerFileSaveDTO.getUseYn());
        return noticeFile;
    }

    /**
     * Update.
     *
     * @param customerFileSaveDTO the customer file save dto
     * @author [이소정]
     * @implNote 게시판 파일 수정
     * @since 2021. 1. 7. 오후 9:47:41
     */
    public void update(final CustomerFileSaveDTO customerFileSaveDTO, final String cdnUrl) {
        boolean isFile = ServiceCode.NoticeFileKindCode.FILE.toString().equals(customerFileSaveDTO.getFileKindCode()) ? true : false;
        this.fileKindCode = customerFileSaveDTO.getFileKindCode();
        
        this.fileContentType = isFile ? customerFileSaveDTO.getFileContentType() : null;
        this.fileExtension = isFile ? customerFileSaveDTO.getFileExtension() : null;
        this.fileName = isFile ? customerFileSaveDTO.getFileName(): null;
        this.fileSize = isFile ? customerFileSaveDTO.getFileSize(): null;
        this.filePhysicalName = isFile ? customerFileSaveDTO.getFilePhysicalName().replace(cdnUrl, ""): null;
        this.detailThumbnailFileName = isFile ? customerFileSaveDTO.getDetailThumbnailFileName() : null;
        this.detailThumbnailFileSize = isFile ? customerFileSaveDTO.getDetailThumbnailFileSize() : null;
        this.detailThumbnailFilePhysicalName = isFile && customerFileSaveDTO.getDetailThumbnailFilePhysicalName() != null ? customerFileSaveDTO.getDetailThumbnailFilePhysicalName().replace(cdnUrl, "") : null;
        
        this.title = isFile ? null : customerFileSaveDTO.getTitle();
        this.url = isFile ? null : customerFileSaveDTO.getUrl();
    }

    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @implNote [method 설명]
     * @since 2020. 12. 22. 오후 7:45:16
     */
    public void updateUseYn(final String useYn) {
        log.info("NoticeFile.updateUseYn");
        this.useYn = useYn;
    }

}
