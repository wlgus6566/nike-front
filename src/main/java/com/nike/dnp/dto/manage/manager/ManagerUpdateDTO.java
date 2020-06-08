package com.nike.dnp.dto.manage.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ManagerDTO
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description Request > ManagerDTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerUpdateDTO {

    /**
     *
     */
    private Long authSeq;

    /**
     *
     */
    private String managerName;

    /**
     *
     */
    private String password;

    /**
     *
     */
    @ApiModelProperty(hidden = true)
    private Long updaterSeq;

}
