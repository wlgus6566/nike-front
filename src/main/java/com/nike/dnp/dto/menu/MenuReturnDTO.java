package com.nike.dnp.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.entity.menu.MenuRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuReturnDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:52:14
 * @implNote Menu(메뉴) Return DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuReturnDTO {

    /**
     * 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuSeq", value = "메뉴 시퀀스", hidden = true)
    private Long menuSeq;

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuCode", value = "메뉴 코드", required = true)
    private String menuCode;

    /**
     * 상위 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperMenuSeq", value = "상위 메뉴 시퀀스", hidden = true)
    private Long upperMenuSeq;

    /**
     * 메뉴명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuName", value = "메뉴명", required = true)
    private String menuName;

    /**
     * 상위 메뉴 명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperMenuName", value = "상위 메뉴 명", hidden = true)
    private String upperMenuName;

    /**
     * 메뉴 경로 URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuPathUrl", value = "메뉴 경로 URL", required = true)
    private String menuPathUrl;

    /**
     * PC 노출 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "pcYn", value = "PC 노출 여부", required = true)
    private String pcYn;

    /**
     * MOBILE 노출 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "mobileYn", value = "MOBILE 노출 여부", required = true)
    private String mobileYn;

    /**
     * 부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "managementYn", value = "managementYn", required = true)
    private String managementYn;

    /**
     * 하위 메뉴 목록
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menu", value = "하위 메뉴 목록")
    private List<MenuReturnDTO> menus;



    /**
     * The Menu roles
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuRoles", value = "메뉴 역할")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<MenuRole> menuRoles = new ArrayList<>();

    /**
     * The Skill codes
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "skillCodes", value = "스킬 코드")
    private List<MenuReturnDTO.SkillCode> skillCodes = new ArrayList<>();

    /**
     * Gets skill codes.
     *
     * @return the skill codes
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 4:16:43
     * @implNote
     */
    public List<MenuReturnDTO.SkillCode> getSkillCodes() {
        final List<MenuReturnDTO.SkillCode> skillCodes = new ArrayList<>();

        if (this.menuRoles.isEmpty()) {
            return new ArrayList<>();
        }

        for (final ServiceCode.MenuSkillEnumCode enumCode : ServiceCode.MenuSkillEnumCode.values()) {
            Long menuRoleSeq = 0L;
            for (final MenuRole menuRole : this.menuRoles) {
                if (menuRole.getMenuSkillCode().equals(enumCode.toString())) {
                    menuRoleSeq = menuRole.getMenuRoleSeq();
                }
            }

            skillCodes.add(MenuReturnDTO.SkillCode.builder()
                    .menuSeq(this.menuSeq)
                    .code(enumCode.toString())
                    .field(enumCode.getField())
                    .message(enumCode.getMessage())
                    .menuRoleSeq(menuRoleSeq)
                    .build());
        }
        return skillCodes;
    }

    /**
     * The Class Skill code.
     *
     * @author [오지훈]
     * @since 2020. 7. 14. 오후 12:02:07
     * @implNote
     */
    @Getter
    @Setter
    public static class SkillCode {
        /**
         * The Menu seq
         *
         * @author [오지훈]
         */
        private Long menuSeq;
        /**
         * The Code
         *
         * @author [오지훈]
         */
        private String code;
        /**
         * The Field
         *
         * @author [오지훈]
         */
        private String field;
        /**
         * The Message
         *
         * @author [오지훈]
         */
        private String message;
        /**
         * The Menu role seq
         *
         * @author [오지훈]
         */
        private Long menuRoleSeq;

        /**
         * Instantiates a new Skill code.
         *
         * @param menuSeq     the menu seq
         * @param code        the code
         * @param field       the field
         * @param message     the message
         * @param menuRoleSeq the menu role seq
         * @author [오지훈]
         * @since 2020. 7. 21. 오후 3:51:09
         * @implNote
         */
        @Builder
        public SkillCode (
                final Long menuSeq
                , final String code
                , final String field
                , final String message
                , final Long menuRoleSeq
        ) {
            this.menuSeq = menuSeq;
            this.code = code;
            this.field = field;
            this.message = message;
            this.menuRoleSeq = menuRoleSeq;
        }
    }

}
