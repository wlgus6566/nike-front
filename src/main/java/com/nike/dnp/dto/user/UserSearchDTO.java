package com.nike.dnp.dto.user;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserSearchDTO
 *
 * @author [오지훈]
 * @Description User(유저) Search DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserSearchDTO extends SearchDTO {

    /**
     * 검색어
     * @author [오지훈]
     */
    @ApiModelProperty(value = "검색어", name = "keyword")
    private String keyword = "";

    /**
     * Method to String
     * @return String
     * @author [오지훈]
     */
    @Override
    public String toString() {
        return "UserSearchDTO{"
                + "keyword=" + keyword
                + ", size=" + getSize()
                + ", page=" + getPage() + '}';
    }

}
