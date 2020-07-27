package com.nike.dnp.dto.notice;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * The Class Notice list dto.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 7:11:50
 * @Description Customer Center 목록 반환 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NoticeArticleListDTO {

    /**
     * The Notice article seq
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleSeq", value = "게시글 시퀀스")
    private Long noticeArticleSeq;

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @ApiParam(name = "noticeArticleSectionCode", value = "게시물 구분 코드", defaultValue = "", required = true)
    private String noticeArticleSectionCode;



    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    @ApiParam(name = "noticeArticleCategoryCode", value = "[QNA] 게시물 카테고리 코드", defaultValue = "")
    private String noticeArticleCategoryCode;

    /**
     * The Notice yn
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeYn", value = "[공지사항] 공지 여부")
    private String noticeYn;

    /**
     * The Thumbnail file name
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "thumbnailFileName", value = "[NEWS] 썸네일 파일 명")
    private String thumbnailFileName;

    /**
     * The Thumbnail file size
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "thumbnailFileSize", value = "[NEWS] 썸네일 파일 사이즈")
    private String thumbnailFileSize;

    /**
     * The Thumbnail file physical name
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "[NEWS] 썸네일 파일 물리 명")
    private String thumbnailFilePhysicalName;

    /**
     * The Title
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "title", value = "제목")
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "contents", value = "내용")
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;
}
