package com.nike.dnp.dto.example;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SearchDTO {

    /**
     * @author [오지훈]
     * @default 0
     */
    @ApiModelProperty(value = "페이지", name = "page")
    private int page;

    /**
     * @author [오지훈]
     * @default 20
     */
    @ApiModelProperty(value = "사이즈", name = "size")
    private int size = 20;

}
