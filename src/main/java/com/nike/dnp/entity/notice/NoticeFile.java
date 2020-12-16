package com.nike.dnp.entity.notice;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.dto.notice.NoticeFileSaveDTO;
import com.nike.dnp.dto.order.OrderProductFileSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.order.OrderProductMapping;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * The Class Notice file.
 *
 * @author [이소정]
 * @since 2020. 12. 16. 오후 6:30:09
 */
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
     * 사용 여부
     *
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * 주문 상품 정보
     *
     * @author [윤태호]
     */
    @ManyToOne
    @JoinColumn(name="NOTICE_ARTICLE_SEQ",insertable = false,updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "게시물", value = "게시물")
    private NoticeArticle noticeArticle;


    /**
     * Save notice file notice file.
     *
     * @param noticeFileSaveDTO the notice file save dto
     * @return the notice file
     * @author [이소정]
     * @implNote [method 설명]
     * @since 2020. 12. 16. 오후 6:57:15
     */
    public NoticeFile saveNoticeFile(final NoticeFileSaveDTO noticeFileSaveDTO) {
        NoticeFile noticeFile = new NoticeFile();
        noticeFile.setNoticeArticleSeq(noticeFileSaveDTO.getNoticeArticleSeq());
        noticeFile.setFileContentType(noticeFileSaveDTO.getFileContentType());
        noticeFile.setFileExtension(noticeFileSaveDTO.getFileExtension());
        noticeFile.setFileName(noticeFileSaveDTO.getFileName());
        noticeFile.setFileSize(noticeFileSaveDTO.getFileSize());
        noticeFile.setUseYn(noticeFileSaveDTO.getUseYn());
        return noticeFile;
    }

}
