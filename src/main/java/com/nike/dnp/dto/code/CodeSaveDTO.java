package com.nike.dnp.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CodeSaveDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:08:43
 * @Description Code(공통코드) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CodeSaveDTO {

    /**
     * 코드
     *
     * @author [오지훈]
     */
    @NotBlank(message = "code.code")
    @ApiModelProperty(name = "code", value = "코드")
    private String code;

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

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @NotBlank(message = "code.useYn")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

}
