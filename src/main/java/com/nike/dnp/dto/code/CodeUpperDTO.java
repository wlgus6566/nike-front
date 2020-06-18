package com.nike.dnp.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * CodeUpperDTO
 *
 * @author [오지훈]
 * @Description Code(공통코드) Upper DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CodeUpperDTO {

    /**
     * 상위 코드
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperCode", value = "상위 코드", required = true)
    private String upperCode;

}
