package com.nike.dnp.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CodeUpdateDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:08:56
 * @Description Code(공통코드) Update DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CodeUpdateDTO {

    /**
     * 상위 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperCode", value = "상위 코드")
    private String upperCode;

    /**
     * 코드 명
     *
     * @author [오지훈]
     */
    @NotBlank(message = "code.codeName")
    @ApiModelProperty(name = "codeName", value = "코드 명")
    private String codeName;

    /**
     * 코드 설명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "codeDescription", value = "코드 설명")
    private String codeDescription;

    /**
     * 코드 순서
     *
     * @author [오지훈]
     */
    @NotNull(message = "code.codeOrder")
    @ApiModelProperty(name = "codeOrder", value = "코드 순서")
    private Long codeOrder;

}
