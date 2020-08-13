package com.nike.dnp.entity.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu Entity
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:52:14
 * @implNote Menu(메뉴) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MENU")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Menu extends BaseTimeEntity implements Serializable {

    /**
     * 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_SEQ")
    @ApiModelProperty(name = "menuSeq", value = "메뉴 시퀀스", hidden = true)
    private Long menuSeq;

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_CODE")
    @ApiModelProperty(name = "menuCode", value = "메뉴 코드", required = true)
    private String menuCode;

    /**
     * 상위 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "UPPER_MENU_SEQ")
    @ApiModelProperty(name = "upperMenuSeq", value = "상위 메뉴 시퀀스", hidden = true)
    private Long upperMenuSeq;

    /**
     * 메뉴명
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_NAME")
    @ApiModelProperty(name = "menuName", value = "메뉴명", required = true)
    private String menuName;

    /**
     * 메뉴 경로 URL
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_PATH_URL")
    @ApiModelProperty(name = "menuPathUrl", value = "메뉴 경로 URL", required = true)
    private String menuPathUrl;

    /**
     * 생성 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "CREATION_AUTH_YN")
    @ApiModelProperty(name = "creationAuthYn", value = "생성 권한 여부", required = true)
    private String creationAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 삭제 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "DELETE_AUTH_YN")
    @ApiModelProperty(name = "deleteAuthYn", value = "삭제 권한 여부", required = true)
    private String deleteAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 다운로드 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "DOWNLOAD_AUTH_YN")
    @ApiModelProperty(name = "downloadAuthYn", value = "다운로드 권한 여부", required = true)
    private String downloadAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 목록 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "LIST_AUTH_YN")
    @ApiModelProperty(name = "listAuthYn", value = "목록 권한 여부", required = true)
    private String listAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 상세 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "DETAIL_AUTH_YN")
    @ApiModelProperty(name = "detailAuthYn", value = "상세 권한 여부", required = true)
    private String detailAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 리포트 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "REPORT_AUTH_YN")
    @ApiModelProperty(name = "reportAuthYn", value = "리포트 권한 여부", required = true)
    private String reportAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 메뉴 순서
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_ORDER")
    @ApiModelProperty(name = "menuOrder", value = "메뉴 순서", required = true)
    private Long menuOrder;

    /**
     * 메뉴 depth
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_DEPTH")
    @ApiModelProperty(name = "menuDepth", value = "메뉴 depth", required = true)
    private Long menuDepth;

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

    /**
     * PC 노출 여부
     *
     * @author [오지훈]
     */
    @Column(name = "PC_YN")
    @ApiModelProperty(name = "pcYn", value = "PC 노출 여부", required = true)
    private String pcYn;

    /**
     * MOBILE 노출 여부
     *
     * @author [오지훈]
     */
    @Column(name = "MOBILE_YN")
    @ApiModelProperty(name = "mobileYn", value = "MOBILE 노출 여부", required = true)
    private String mobileYn;

    /**
     * 권한 관리 여부
     *
     * @author [오지훈]
     */
    @Column(name = "MANAGEMENT_YN")
    @ApiModelProperty(name = "managementYn", value = "권한 관리 여부", required = true)
    private String managementYn;

    /**
     * 하위 메뉴 목록
     *
     * @author [오지훈]
     */
    @OneToMany
    @JoinColumn(name = "UPPER_MENU_SEQ",
            referencedColumnName = "MENU_SEQ",
            insertable = false, updatable = false)
    //@JsonBackReference
    @ApiModelProperty(name = "subMenus", value = "하위 메뉴")
    private List<Menu> subMenus;

    /*@ManyToOne
    @JoinColumn(name = "UPPER_MENU_SEQ",
            referencedColumnName = "MENU_SEQ",
            insertable = false, updatable = false)
    @JsonManagedReference
    private Menu upperMenu;*/

    /**
     * The Menu roles
     *
     * @author [오지훈]
     */
    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    @JsonManagedReference
    @ApiModelProperty(name = "menuRoles", value = "메뉴 역할")
    private List<MenuRole> menuRoles;

    /**
     * The Skill codes
     *
     * @author [오지훈]
     */
    @Transient
    @ApiModelProperty(name = "skillCodes", value = "스킬 코드")
    //private HashMap<Integer, Object> skillCodes;
    private List<SkillCode> skillCodes;

    /**
     * Gets skill codes.
     *
     * @return the skill codes
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 4:16:43
     * @implNote
     */
    public List<SkillCode> getSkillCodes() {
        final List<SkillCode> skillCodes = new ArrayList<>();
        for (final ServiceCode.MenuSkillEnumCode enumCode : ServiceCode.MenuSkillEnumCode.values()) {
            Long menuRoleSeq = 0L;
            for (final MenuRole menuRole : this.menuRoles) {
                if (menuRole.getMenuSkillCode().equals(enumCode.toString())) {
                    menuRoleSeq = menuRole.getMenuRoleSeq();
                }
            }

            skillCodes.add(SkillCode.builder()
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
