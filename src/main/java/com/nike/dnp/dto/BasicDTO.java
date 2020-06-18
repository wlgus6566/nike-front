package com.nike.dnp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * BasicDTO
 *
 * @author [오지훈]
 * @Description 공통 DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class BasicDTO {

    /**
     * 사용 여부
     * @author [오지훈]
     */
    @ApiModelProperty(name = "사용 여부", hidden = true)
    private String useYn;

    /**
     * 최초 등록자
     * @author [오지훈]
     */
    @ApiModelProperty(name = "최초 등록자", hidden = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     * @author [오지훈]
     */
    @ApiModelProperty(name = "최종 수정자", hidden = true)
    private Long updaterSeq;

}
