package com.nike.dnp.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * BasicDTO
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 공통 DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class BasicDTO {

    /**
     *
     */
    @ApiModelProperty(name = "사용여부", hidden = true)
    private String useYn;

    /**
     *
     */
    @ApiModelProperty(name = "최초등록자", hidden = true)
    private Long registerSeq;

    /**
     *
     */
    @ApiModelProperty(name = "최종수정자", hidden = true)
    private Long updaterSeq;

}
