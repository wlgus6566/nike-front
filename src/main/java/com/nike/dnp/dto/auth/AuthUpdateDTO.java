package com.nike.dnp.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Auth update dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:21:01
 * @Description Auth(권한) Update DTO 작성
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
    @ApiModelProperty(name = "authName", value = "권한명", required = true)
    private String authName;

}
