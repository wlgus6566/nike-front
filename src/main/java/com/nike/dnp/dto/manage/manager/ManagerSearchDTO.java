package com.nike.dnp.dto.manage.manager;

import com.nike.dnp.dto.manage.SearchDTO;
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
public class ManagerSearchDTO extends SearchDTO {

    /**
     *
     */
    @ApiModelProperty(value = "검색어")
    private String keyword = "";

}
