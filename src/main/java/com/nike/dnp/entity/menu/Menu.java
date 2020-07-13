package com.nike.dnp.entity.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Menu Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 19. 오후 4:52:14
 * @Description Menu(메뉴) Entity 작성
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
    private String creationAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 삭제 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "DELETE_AUTH_YN")
    @ApiModelProperty(name = "deleteAuthYn", value = "삭제 권한 여부", required = true)
    private String deleteAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 다운로드 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "DOWNLOAD_AUTH_YN")
    @ApiModelProperty(name = "downloadAuthYn", value = "다운로드 권한 여부", required = true)
    private String downloadAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 목록 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "LIST_AUTH_YN")
    @ApiModelProperty(name = "listAuthYn", value = "목록 권한 여부", required = true)
    private String listAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 상세 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "DETAIL_AUTH_YN")
    @ApiModelProperty(name = "detailAuthYn", value = "상세 권한 여부", required = true)
    private String detailAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 리포트 권한 여부
     *
     * @author [오지훈]
     */
    @Column(name = "REPORT_AUTH_YN")
    @ApiModelProperty(name = "reportAuthYn", value = "리포트 권한 여부", required = true)
    private String reportAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 메뉴 순서
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_ORDER")
    @ApiModelProperty(name = "menuOrder", value = "메뉴 순서", required = true)
    private Long menuOrder;

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

    /**
     * The Skill codes
     *
     * @author [오지훈]
     */
    @Transient
    @ApiModelProperty(name = "skillCodes", value = "스킬 코드")
    private HashMap<Integer, Object> skillCodes;

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
    private List<MenuRole> menuRoles;

    /**
     * Gets skill codes.
     *
     * @return the skill codes
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 4:16:43
     * @Description
     */
    public HashMap<Integer, Object> getSkillCodes() {
        HashMap<Integer, Object> map = new HashMap<>();
        for (ServiceEnumCode.MenuSkillEnumCode enumCode : ServiceEnumCode.MenuSkillEnumCode.values()) {
            HashMap<String, Object> sub = new HashMap<>();
            sub.put("menuSeq", this.menuSeq);
            sub.put("code", enumCode.toString());
            sub.put("field", enumCode.getField());
            sub.put("message", enumCode.getMessage());
            sub.put("menuRoleSeq", 0);
            for (MenuRole menuRole : this.menuRoles) {
                if (menuRole.getMenuSkillCode().equals(enumCode.toString())) {
                    sub.put("menuRoleSeq", menuRole.getMenuRoleSeq());
                }
            }
            map.put(enumCode.getSort(), sub);
        }
        return map;
    }
}
