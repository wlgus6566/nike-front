package com.nike.dnp.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;


/**
 * UserContentsSearchDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 12:14:01
 * @Description UserContents(유저 컨텐츠 권한) Search DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserContentsSearchDTO {

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @NotBlank(message = "userContents.menuCode")
    @ApiModelProperty(name = "menuCode", value = "메뉴 코드", example = "ASSET_SP")
    private String menuCode;

    /**
     * 스킬 코드
     *
     * @author [오지훈]
     */
    @NotBlank(message = "userContents.skillCode")
    @ApiModelProperty(name = "skillCode", value = "스킬 코드", example = "VIEW")
    private String skillCode;

}
