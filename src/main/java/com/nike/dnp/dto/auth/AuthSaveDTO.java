package com.nike.dnp.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Auth save dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:21:03
 * @Description Auth(권한) Save DTO 작성
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
    @ApiModelProperty(name = "upperAuthSeq", value = "상위 권한 시퀀스", required = true)
    private Long upperAuthSeq;

    /**
     * 권한명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authName", value = "권한명", required = true)
    private String authName;

    /**
     * 메뉴 역할 시퀀스 배열
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuRoleSeqArray", value = "메뉴 역할 시퀀스 배열", required = true)
    Long[] menuRoleSeqArray;

}
