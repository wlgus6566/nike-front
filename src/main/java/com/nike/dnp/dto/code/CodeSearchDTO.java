package com.nike.dnp.dto.code;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * CodeSearchDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:08:47
 * @implNote Code(공통코드) Search DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CodeSearchDTO extends SearchDTO {

    /**
     * 검색어
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "keyword", value = "검색어")
    private String keyword = "";

    /**
     * Method to String
     *
     * @return String string
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 6:08:48
     * @implNote
     */
    @Override
    public String toString() {
        return "CodeSearchDTO{"
                + "keyword=" + keyword
                + ", size=" + getSize()
                + ", page=" + getPage() + '}';
    }
}
