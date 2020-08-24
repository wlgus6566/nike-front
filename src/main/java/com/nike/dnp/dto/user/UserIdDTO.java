package com.nike.dnp.dto.user;

import com.nike.dnp.common.validation.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;


/**
 * UserIdDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 12:14:01
 * @implNote User(유저) ID로 검색 DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserIdDTO {

    /**
     * The User id
     *
     * @author [오지훈]
     */
    @NotBlank(message = "user.userId", groups = {ValidationGroups.Group1.class})
    @ApiParam(value = "유저 ID", name = "userId", required = true, defaultValue = "test@nike.co.kr")
    private String userId;

    /**
     * 접속 platform
     *
     * @author [오지훈]
     */
    @NotBlank(message = "userCert.platform", groups = {ValidationGroups.Group2.class})
    @ApiModelProperty(value = "접속 platform", name = "platform", required = true, example = "PC", allowableValues = "PC,MOBILE")
    private String platform;

}
