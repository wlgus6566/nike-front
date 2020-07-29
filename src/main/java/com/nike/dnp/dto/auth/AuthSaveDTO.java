package com.nike.dnp.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The Class Auth save dto.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:21:03
 * @implNote Auth(권한) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class AuthSaveDTO {

    /**
     * 상위 권한 시퀀스
     *
     * @author [오지훈]
     */
    @NotNull(message = "auth.upperAuthSeq")
    @ApiModelProperty(name = "upperAuthSeq", value = "상위 권한 시퀀스")
    private Long upperAuthSeq;

    /**
     * 권한명
     *
     * @author [오지훈]
     */
    @NotBlank(message = "auth.authName")
    @ApiModelProperty(name = "authName", value = "권한명")
    private String authName;

    /**
     * 권한 Depth
     *
     * @author [오지훈]
     */
    @NotNull(message = "auth.authDepth")
    @ApiModelProperty(name = "authDepth", value = "권한 Depth")
    private Long authDepth;

    /**
     * 메뉴 역할 시퀀스 배열
     *
     * @author [오지훈]
     */
    @NotNull(message = "auth.menuRoleSeqArray")
    @ApiModelProperty(name = "menuRoleSeqArray", value = "메뉴 역할 시퀀스 배열", required = true)
    private Long[] menuRoleSeqArray;

}
