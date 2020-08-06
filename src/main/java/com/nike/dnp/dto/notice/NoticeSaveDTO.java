package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.BasicDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * The Class Notice save dto.
 *
 * @author [정주희]
 * @since 2020. 7. 19. 오전 1:14:17
 * @implNote
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
    @ApiModelProperty(name = "noticeArticleSectionCode",
            value = "게시물 구분 코드", example = "NOTICE", required = true)
    private String noticeArticleSectionCode;

    /**
     * The Notice yn
     *
     * @author [정주희]
     */
    @NotBlank(message = "customer.noticeYn")
    @ApiModelProperty(name = "noticeYn", value = "[공지사항] 상단 고정 여부", example = "N", required = true)
    private String noticeYn;

    /**
     * The Title
     *
     * @author [정주희]
     */
    @NotBlank(message="customer.title")
    @ApiModelProperty(name = "title", value = "제목", example = "제목입니다.", required = true)
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    @NotBlank(message="customer.contents")
    @ApiModelProperty(name = "contents", value = "내용", example = "내용입니다.", required = true)
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "useYn", value = "게시글 사용 여부", example = "Y", required = true)
    private String useYn;

}
