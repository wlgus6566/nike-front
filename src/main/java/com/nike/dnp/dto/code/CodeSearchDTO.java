package com.nike.dnp.dto.code;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * CodeSearchDTO
 *
 * @author [오지훈]
 * @Description Code(공통코드) Search DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CodeSearchDTO extends SearchDTO {

    /**
     * 검색어
     * @author [오지훈]
     */
    @ApiModelProperty(name = "keyword", value = "검색어")
    private String keyword = "";

    /**
     * Method to String
     * @return String
     */
    @Override
    public String toString() {
        return "CodeSearchDTO{"
                + "keyword=" + keyword
                + ", size=" + getSize()
                + ", page=" + getPage() + '}';
    }
}
