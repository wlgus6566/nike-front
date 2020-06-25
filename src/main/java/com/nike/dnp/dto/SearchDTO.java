package com.nike.dnp.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "페이지", name = "page")
    private Integer page = 0;

    /**
     * The Size
     *
     * @author [오지훈]
     * @defaultValue 20
     */
    @ApiModelProperty(value = "사이즈", name = "size")
    private int size = 20;

}
