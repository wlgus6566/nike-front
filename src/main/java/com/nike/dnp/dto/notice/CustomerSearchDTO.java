package com.nike.dnp.dto.notice;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class CustomerSearchDTO extends SearchDTO {

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
    @ApiParam(value = "[QNA] 게시물 카테고리 코드 (상위 코드 : NOTICE_CATEGORY_CODE)", name = "noticeArticleCategoryCode", defaultValue = "")
    private String noticeArticleCategoryCode;

    /**
     * The Keyword
     *
     * @author [정주희]
     */
    @ApiParam(value = "검색어", name = "keyword", defaultValue = "", required = false)
    private String keyword = "";

}
