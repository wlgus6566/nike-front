package com.nike.dnp.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Menu role resource return dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 13. 오전 11:35:12
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuRoleResourceReturnDTO {

    /**
     * 메뉴 역할 리소스 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuRoleResourceSeq", value = "메뉴 역할 리소스 시퀀스")
    private Long menuRoleResourceSeq;

    /**
     * 메뉴 역할 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuRoleSeq", value = "메뉴 역할 시퀀스")
    private Long menuRoleSeq;

    /**
     * 리소스 URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "resourceUrl", value = "리소스 URL")
    private String resourceUrl;

    /**
     * 리소스 메소드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "resourceMethod", value = "리소스 메소드")
    private String resourceMethod;

}
