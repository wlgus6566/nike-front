package com.nike.dnp.dto.manage.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ManagerMenuDTO
 *
 * @since 2020.05.28
 * @author [오지훈]
 * @Description Request > ManagerMenuDTO 작성
 * @history [오지훈] [2020.05.28] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerAuthDTO {

    /**
     *
     */
    private Long authSeq;

    /**
     *
     */
    private String authName;

    /**
     *
     */
    private String roleType;

    /**
     *
     */
    private String useYn;

    /**
     *
     */
    @ApiModelProperty(hidden = true)
    private Long registerSeq;

    /**
     *
     */
    @ApiModelProperty(hidden = true)
    private Long updaterSeq;

}
