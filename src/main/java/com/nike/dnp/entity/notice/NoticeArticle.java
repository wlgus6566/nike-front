package com.nike.dnp.entity.notice;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.dto.notice.CustomerSaveDTO;
import com.nike.dnp.dto.notice.CustomerUpdateDTO;
import com.nike.dnp.dto.notice.NoticeSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.util.ImageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Optional;

/**
 * The Class Notice article.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 6:02:22
 * @implNote
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_NOTICE_ARTICLE")
public class NoticeArticle extends BaseTimeEntity {

    /**
     * The Notice article seq
     *
     * @author [정주희]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ARTICLE_SEQ")
    @ApiModelProperty(name = "noticeArticleSeq", value ="게시물 시퀀스")
    private Long noticeArticleSeq;

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @Column(name = "NOTICE_ARTICLE_SECTION_CODE")
    @ApiModelProperty(name = "noticeArticleSectionCode", value ="게시물 구분 공통 코드", example = "NOTICE")
    private String noticeArticleSectionCode;

    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    @Column(name = "NOTICE_ARTICLE_CATEGORY_CODE")
    @ApiModelProperty(name = "noticeArticleCategoryCode", value ="게시물 카테고리 공통 코드", example = "ASSET")
    private String noticeArticleCategoryCode;

    /**
     * The Notice yn
     *
     * @author [정주희]
     */
    @Column(name = "NOTICE_YN")
    @ApiModelProperty(name = "noticeYn", value ="공지 여부", example = "N")
    private String noticeYn;

    /**
     * The Thumbnail file name
     *
     * @author [정주희]
     */
    @Column(name = "THUMBNAIL_FILE_NAME")
    @ApiModelProperty(name = "thumbnailFileName", value ="썸네일 파일명")
    private String thumbnailFileName;

    /**
     * The Thumbnail file size
     *
     * @author [정주희]
     */
    @Column(name = "THUMBNAIL_FILE_SIZE")
    @ApiModelProperty(name = "thumbnailFileSize", value ="썸네일 파일 사이즈")
    private String thumbnailFileSize;

    /**
     * The Thumbnail file physical name
     *
     * @author [정주희]
     */
    @Column(name = "THUMBNAIL_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value ="썸네일 파일 물리명")
    private String thumbnailFilePhysicalName;

    /**
     * The Title
     *
     * @author [정주희]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value ="제목")
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    @Column(name = "CONTENTS")
    @ApiModelProperty(name = "contents", value ="내용")
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value ="사용 여부", example = "N")
    private String useYn;

    @PrePersist
    public void prePersist() {
        this.useYn = this.useYn == null ? "Y" : this.useYn;
        this.noticeYn = this.noticeYn == null ? "N" : this.noticeYn;
    }

    /**
     * Save notice article.
     *
     * @param customerSaveDTO the customer save dto
     * @return the notice article
     * @author [정주희]
     * @CreatedOn 2020. 7. 30. 오후 10:35:21
     * @Description
     */
    public NoticeArticle save(final CustomerSaveDTO customerSaveDTO) {
        log.info("NoticeArticle.save");
        final NoticeArticle noticeArticle = new NoticeArticle();

        noticeArticle.setNoticeArticleSectionCode(customerSaveDTO.getNoticeArticleSectionCode());
        noticeArticle.setTitle(customerSaveDTO.getTitle());
        noticeArticle.setContents(customerSaveDTO.getContents());
        noticeArticle.setUseYn(customerSaveDTO.getUseYn());
        noticeArticle.setRegisterSeq(customerSaveDTO.getRegisterSeq());
        noticeArticle.setUpdaterSeq(customerSaveDTO.getRegisterSeq());

        if (StringUtils.equals(customerSaveDTO.getNoticeArticleSectionCode(), "NOTICE")) {
            noticeArticle.setNoticeYn(customerSaveDTO.getNoticeYn());
        } else if (StringUtils.equals(customerSaveDTO.getNoticeArticleSectionCode(), "NEWS")) {
            if (!ObjectUtils.isEmpty(customerSaveDTO.getImageBase64())) {
                FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                        ServiceCode.FileFolderEnumCode.QNA.getFolder(), customerSaveDTO.getImageBase64());

                noticeArticle.setThumbnailFileName(fileResultDTO.getFileName());
                noticeArticle.setThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
                noticeArticle.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
            }
        } else if (StringUtils.equals(customerSaveDTO.getNoticeArticleSectionCode(), "QNA")) {
            noticeArticle.setNoticeArticleCategoryCode(customerSaveDTO.getNoticeArticleCategoryCode());
        }

        return noticeArticle;
    }

    public NoticeArticle update(final CustomerUpdateDTO customerUpdateDTO) {
        log.info("NoticeArticle.update");

        // TODO [jjh] 업데이트 수정
        final NoticeArticle noticeArticle = new NoticeArticle();

        noticeArticle.setNoticeArticleSectionCode(customerUpdateDTO.getNoticeArticleSectionCode());
        noticeArticle.setTitle(customerUpdateDTO.getTitle());
        noticeArticle.setContents(customerUpdateDTO.getContents());
        noticeArticle.setUseYn(customerUpdateDTO.getUseYn());

        if (StringUtils.equals(customerUpdateDTO.getNoticeArticleSectionCode(), "NOTICE")) {
            noticeArticle.setNoticeYn(customerUpdateDTO.getNoticeYn());
        } else if (StringUtils.equals(customerUpdateDTO.getNoticeArticleSectionCode(), "NEWS")) {
            if (!ObjectUtils.isEmpty(customerUpdateDTO.getImageBase64())) {
                FileResultDTO fileResultDTO = ImageUtil.fileSaveForBase64(
                        ServiceCode.FileFolderEnumCode.QNA.getFolder(), customerUpdateDTO.getImageBase64());

                noticeArticle.setThumbnailFileName(fileResultDTO.getFileName());
                noticeArticle.setThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
                noticeArticle.setThumbnailFileSize(String.valueOf(fileResultDTO.getFileSize()));
            }
        } else if (StringUtils.equals(customerUpdateDTO.getNoticeArticleSectionCode(), "QNA")) {
            noticeArticle.setNoticeArticleCategoryCode(customerUpdateDTO.getNoticeArticleCategoryCode());
        }

        return noticeArticle;
    }
}
