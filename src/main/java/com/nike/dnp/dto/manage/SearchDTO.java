package com.nike.dnp.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * SearchDTO
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description Request > 검색용 공통 DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
public class SearchDTO {

    /**
     *
     */
    @ApiModelProperty(value = "페이지")
    private int page;

    /**
     *
     */
    @ApiModelProperty(value = "사이즈")
    private int size = 20;

}
