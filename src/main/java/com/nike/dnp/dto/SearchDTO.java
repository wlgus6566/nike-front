package com.nike.dnp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * SearchDTO
 *
 * @author [오지훈]
 * @Description 공통 Search DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SearchDTO {

    /**
     * @defaultValue 0
     * @author [오지훈]
     */
    @ApiModelProperty(value = "페이지", name = "page")
    private Integer page = 0;

    /**
     * @defaultValue 20
     * @author [오지훈]
     */
    @ApiModelProperty(value = "사이즈", name = "size")
    private int size = 20;

}
