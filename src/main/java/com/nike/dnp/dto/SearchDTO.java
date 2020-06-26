package com.nike.dnp.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

/**
 * SearchDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:42
 * @Description 공통 Search DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SearchDTO {

    /**
     * The Page
     *
     * @author [오지훈]
     * @defaultValue 0
     */
    @ApiParam(value = "페이지", name = "page",defaultValue = "0")
    private Integer page = 0;

    /**
     * The Size
     *
     * @author [오지훈]
     * @defaultValue 20
     */
    @ApiParam(value = "사이즈", name = "size" , defaultValue = "20")
    private int size = 20;

}
