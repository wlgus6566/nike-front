package com.nike.dnp.entity.notice;

import com.nike.dnp.dto.notice.NoticeUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * The Class Notice article.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 6:02:22
 * @Description
 */
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
     * Delete.
     *
     * @param noticeUpdateDTO the notice update dto
     * @author [정주희]
     * @CreatedOn 2020. 7. 21. 오후 5:35:02
     * @Description
     */
    public void delete(NoticeUpdateDTO noticeUpdateDTO) {
        setUseYn(noticeUpdateDTO.getUseYn());
    }

}
