package com.nike.dnp.dto.contents;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

/**
 * Contents Search DTO
 *
 * @author [이소정]
 * @Description Contents Search DTO 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsSearchDTO extends SearchDTO {

    /**
     * 검색어
     * @author [이소정]
     */
    @ApiModelProperty(value = "검색어", name = "keyword")
    private String keyword = "";

    /**
     * 정렬 타입
     * @author [이소정]
     */
    @ApiModelProperty(value = "정렬 타입", name = "keyword", example = "LATEST/START_DATE")
    private String orderType;

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @Column(name = "TOP_MENU_CODE")
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드")
    private String topMenuCode;

    /**
     * 메뉴 공통코드
     * @author [이소정]
     */
    @Column(name = "MENU_CODE")
    @ApiModelProperty(name = "menuCode", value = "메뉴 공통코드")
    private String menuCode;

    /**
     * Method to String
     * @return String
     */
    @Override
    public String toString() {
        return "ContentsSearchDTO{"
                + "keyword=" + keyword
                + ", orderType=" + orderType
                + ", size=" + getSize()
                + ", page=" + getPage() + '}';
    }

}
