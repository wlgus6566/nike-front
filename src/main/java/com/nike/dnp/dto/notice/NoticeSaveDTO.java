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
public class NoticeSaveDTO extends BasicDTO {

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @ApiParam(name = "noticeArticleSectionCode", value = "게시물 구분 코드", defaultValue = "", required = true)
    private String noticeArticleSectionCode;

    /**
     * The Notice article seq
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleSeq", value = "게시글 시퀀스")
    private Long noticeArticleSeq;
    /**
     * The Notice yn
     *
     * @author [정주희]
     */
    @NotBlank(message = "customer.noticeYn")
    @ApiModelProperty(name = "noticeYn", value = "[공지사항] 상단 고정 여부")
    private String noticeYn;


    /**
     * The Title
     *
     * @author [정주희]
     */
    @NotBlank(message="customer.title")
    @ApiModelProperty(name = "title", value = "제목")
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    @NotBlank(message="customer.contents")
    @ApiModelProperty(name = "contents", value = "내용")
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    private String useYn;

}
