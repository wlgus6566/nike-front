package com.nike.dnp.dto.user;

import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * UserSearchDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오후 12:11:18
 * @Description User(유저) Search DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserSearchDTO extends SearchDTO {

    private String test = "asdasd";

    /**
     * The Keyword
     *
     * @author [오지훈]
     */
    @ApiParam(value = "검색어", name = "keyword")
    private String keyword = "";

    /**
     * The Status
     *
     * @author [오지훈]
     */
    @ApiParam(value = "유저 상태", name = "status")
    private String status = "";

    /**
     * The Sort
     *
     * @author [오지훈]
     */
    @ApiParam(value = "정렬", name = "sort", defaultValue = "userSeq")
    private String sort = "userSeq";

    /**
     * The Auth seq
     *
     * @author [오지훈]
     */
    @ApiParam(value = "권한 시퀀스", name = "authSeq")
    private Long authSeq = 0L;

    /**
     * The Begin dt
     *
     * @author [오지훈]
     */
    @ApiParam(value = "시작일", name = "beginDt", defaultValue = "2020.06.01")
    private String beginDt;

    /**
     * The End dt
     *
     * @author [오지훈]
     */
    @ApiParam(value = "종료일", name = "endDt", defaultValue = "2020.12.31")
    private String endDt;

    /**
     * Method to String
     *
     * @return String string
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오후 12:11:18
     * @Description
     */
    @Override
    public String toString() {
        return "UserSearchDTO{" +
                "keyword='" + keyword + '\'' +
                ", sort='" + sort + '\'' +
                ", authSeq=" + authSeq +
                ", beginDt='" + beginDt + '\'' +
                ", endDt='" + endDt + '\'' +
                ", size='" + getSize() + '\'' +
                ", page='" + getPage() + '\'' +
                '}';
    }
}
