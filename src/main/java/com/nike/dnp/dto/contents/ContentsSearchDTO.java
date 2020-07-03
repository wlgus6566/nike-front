package com.nike.dnp.dto.contents;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.Column;

/**
 * Contents Search DTO
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 19. 오후 5:57:26
 * @Description Contents Search DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsSearchDTO extends SearchDTO {

    /**
     * 검색어
     *
     * @author [이소정]
     */
    @ApiModelProperty(value = "검색어", name = "keyword")
    private String keyword = "";

    /**
     * 정렬 타입
     *
     * @author [이소정]
     */
    @ApiModelProperty(value = "정렬 타입", name = "keyword", example = "LATEST")
    private String orderType;

    /**
     * 최고 메뉴 코드
     *
     * @author [이소정]
     */
    @Column(name = "TOP_MENU_CODE")
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 코드", hidden = true)
    private String topMenuCode = "";

    /**
     * 2depth 메뉴 코드
     *
     * @author [이소정]
     */
    @Column(name = "MENU_CODE")
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드")
    private String menuCode = "";

    /**
     * Method to String
     *
     * @return String string
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:57:26
     * @Description
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
