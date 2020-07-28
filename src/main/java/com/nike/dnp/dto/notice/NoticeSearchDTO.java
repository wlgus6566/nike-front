package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.SearchDTO;
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
public class NoticeSearchDTO extends SearchDTO {

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @ApiParam(value = "게시물 구분 코드", name = "noticeArticleSectionCode", defaultValue = "", required = false)
    private String noticeArticleSectionCode;

    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    @ApiParam(value = "QNA 게시물 카테고리 코드", name = "noticeArticleCategoryCode", defaultValue = "")
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
     * Sets notice article category code.
     *
     * @param noticeArticleCategoryCode the notice article category code
     * @author [정주희]
     * @CreatedOn 2020. 7. 25. 오후 6:03:48
     * @Description FAQ -> 카테고리 명 대문자 변환
     */
    public void setNoticeArticleCategoryCode(String noticeArticleCategoryCode) {
        this.noticeArticleCategoryCode = noticeArticleCategoryCode.toUpperCase();
    }
}
