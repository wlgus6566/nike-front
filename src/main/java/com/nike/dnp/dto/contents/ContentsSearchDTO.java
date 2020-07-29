package com.nike.dnp.dto.contents;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * Contents Search DTO
 *
 * @author [이소정]
 * @since 2020. 6. 19. 오후 5:57:26
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsSearchDTO extends SearchDTO {

    /**
     * 검색어
     *
     * @author [이소정]
     */
    @ApiParam(value = "검색어", name = "keyword" , defaultValue = "NIKE")
    private String keyword = "";

    /**
     * 정렬 타입
     *
     * @author [이소정]
     */
    @ApiParam(value = "정렬 타입", name = "orderType" , defaultValue = "LATEST")
    private String orderType;

    /**
     * 최고 메뉴 코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 코드", hidden = true)
    private String topMenuCode = "";

    /**
     * 2depth 메뉴 코드
     *
     * @author [이소정]
     */
    @ApiParam(value = "2depth 메뉴 코드", name = "menuCode", hidden = true)
    private String menuCode = "";

    /**
     * 노출 여부
     */
    @ApiParam(value = "노출 여부", name = "exposureYn", hidden = true)
    private String exposureYn;

    /**
     * The User auth seq.
     */
    @ApiModelProperty(name = "userAuthSeq", value = "유저 권한 시퀀스", hidden = true)
    private Long userAuthSeq;

    /**
     * Method to String
     *
     * @return String string
     * @author [이소정]
     * @since 2020. 6. 19. 오후 5:57:26
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
