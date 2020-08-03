package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.BasicDTO;
import lombok.*;

import java.util.Locale;

/**
 * The Class Notice save dto.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 19. 오전 1:14:17
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CustomerSaveDTO extends BasicDTO {

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    private String noticeArticleSectionCode;

    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    private String noticeArticleCategoryCode;


    /**
     * The Notice article seq
     *
     * @author [정주희]
     */
    private Long noticeArticleSeq;

    /**
     * The Notice yn
     *
     * @author [정주희]
     */
    private String noticeYn;

    /**
     * The Thumbnail file name
     *
     * @author [정주희]
     */
    private String thumbnailFileName;

    /**
     * The Thumbnail file size
     *
     * @author [정주희]
     */
    private String thumbnailFileSize;

    /**
     * The Thumbnail file physical name
     *
     * @author [정주희]
     */
    private String thumbnailFilePhysicalName;

    /**
     * The Title
     *
     * @author [정주희]
     */
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    private String useYn;

    /**
     * The Image base 64
     *
     * @author [정주희]
     */
    private String imageBase64;

    public void setNoticeArticleCategoryCode(String noticeArticleCategoryCode) {
        this.noticeArticleCategoryCode = noticeArticleCategoryCode.toUpperCase(Locale.KOREA);
    }
}
