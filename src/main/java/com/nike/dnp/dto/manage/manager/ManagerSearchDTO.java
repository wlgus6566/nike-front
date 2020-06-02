package com.nike.dnp.dto.manage.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ManagerSearchDTO
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description Request > 검색용 DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerSearchDTO {

    @ApiModelProperty(value = "검색어")
    private String keyword = "";

    @ApiModelProperty(value = "페이지")
    private int page;

    @ApiModelProperty(value = "사이즈")
    private int size;

}
