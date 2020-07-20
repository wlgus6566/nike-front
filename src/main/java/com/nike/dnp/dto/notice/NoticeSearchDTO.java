package com.nike.dnp.dto.notice;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.apache.commons.lang.StringUtils;

/**
 * The Class Notice search dto.
 *
 * @author [정주희]
 * @CreatedOn 2020. 7. 13. 오후 7:30:20
 * @Description Customer Center 검색 DTO
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSearchDTO {

    /*public void setNoticeArticleSectionCode(String noticeArticleSectionCode) {
        if(StringUtils.isBlank(noticeArticleSectionCode))
            throw new RuntimeException();

         this.noticeArticleSectionCode = "CD" + noticeArticleSectionCode;
    }*/

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @ApiParam(value = "게시물 구분 코드", name = "noticeArticleSectionCode", defaultValue = "", required = true)
    private String noticeArticleSectionCode;



    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    @ApiParam(value = "게시물 카테고리 코드", name = "noticeArticleCategoryCode", defaultValue = "")
    private String noticeArticleCategoryCode;

    /**
     * The Keyword
     *
     * @author [정주희]
     */
    @ApiParam(value = "검색어", name = "keyword", defaultValue = "", required = false)
    private String keyword = "";

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    @ApiParam(value = "게시글 사용 여부", name = "useYn", defaultValue = "N")
    private String useYn = "N";

    /**
     * The Page
     *
     * @author [정주희]
     */
    @ApiParam(value = "페이지", name = "page", defaultValue = "0")
    private int page;

    /**
     * The Size
     *
     * @author [정주희]
     */
    @ApiParam(value = "사이즈", name = "size", defaultValue = "10")
    private int size = 10;
}
