package com.nike.dnp.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The Class Auth update dto.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:21:01
 * @implNote Auth(권한) Update DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class AuthUpdateDTO {

    /**
     * 권한명
     *
     * @author [오지훈]
     */
    @NotBlank(message = "auth.authName")
    @ApiModelProperty(name = "authName", value = "권한명")
    private String authName;

    /**
     * 메뉴 역할 시퀀스 배열
     *
     * @author [오지훈]
     */
    @NotNull(message = "auth.menuRoleSeqArray")
    @ApiModelProperty(name = "menuRoleSeqArray", value = "메뉴 역할 시퀀스 배열")
    private Long[] menuRoleSeqArray;

    // 이번엔 안하는걸로~
    /*
    @ApiModelProperty(name = "insertMenuRoleSeqArray", value = "등록 메뉴 역할 시퀀스 배열")
    Long[] insertMenuRoleSeqArray;

    @ApiModelProperty(name = "deleteMenuRoleSeqArray", value = "삭제 메뉴 역할 시퀀스 배열")
    Long[] deleteMenuRoleSeqArray;
    */
}
