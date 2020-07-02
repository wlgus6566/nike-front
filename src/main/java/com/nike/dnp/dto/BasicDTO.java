package com.nike.dnp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * BasicDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:38
 * @Description 공통 DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class BasicDTO {

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "사용 여부", hidden = true,example = "Y")
    private String useYn;

    /**
     * 최초 등록자
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "최초 등록자", hidden = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "최종 수정자", hidden = true)
    private Long updaterSeq;

}
