package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;

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
public class QnaUpdateDTO extends BasicDTO {

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleSectionCode",
            value = "게시물 구분 코드", example = "QNA", required = true)
    private String noticeArticleSectionCode;

    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleCategoryCode",
            value = "[QNA] 게시물 카테고리 코드", example = "ASSET", required = true)
    private String noticeArticleCategoryCode;


    /**
     * The Notice article seq
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleSeq", value = "게시글 시퀀스", example = "48", required = true)
    private Long noticeArticleSeq;

    /**
     * The Title
     *
     * @author [정주희]
     */
    @NotBlank(message = "customer.question")
    @ApiModelProperty(name = "title", value = "질문", example = "질문입니다.", required = true)
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    @NotBlank(message = "customer.contents")
    @ApiModelProperty(name = "contents", value = "내용", example = "내용입니다.", required = true)
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "useYn", value = "게시글 사용 여부", example = "Y", required = true)
    private String useYn;

    /**
     * Sets notice article category code.
     *
     * @param noticeArticleCategoryCode the notice article category code
     * @author [정주희]
     * @CreatedOn 2020. 7. 29. 오후 4:43:45
     * @Description
     */
    public void setNoticeArticleCategoryCode(String noticeArticleCategoryCode) {
        this.noticeArticleCategoryCode = noticeArticleCategoryCode.toUpperCase();
    }
}
