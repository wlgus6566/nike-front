package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.Locale;


/**
 * The Class Notice update dto.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 20. 오후 10:02:38
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CustomerUpdateDTO extends BasicDTO {

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
     * The Image base 64
     *
     * @author [정주희]
     */
    private String imageBase64;

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
     * The Notice file save list
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileList", value = "게시글 파일목록")
    private List<CustomerFileSaveDTO> fileList;

    public void setNoticeArticleCategoryCode(String noticeArticleCategoryCode) {
        this.noticeArticleCategoryCode = noticeArticleCategoryCode.toUpperCase(Locale.KOREA);
    }
}
