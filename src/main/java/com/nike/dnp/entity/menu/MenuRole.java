package com.nike.dnp.entity.menu;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * MenuRole Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:10:44
 * @Description MenuRole(메뉴 역할) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MENU_ROLE")
public class MenuRole extends BaseTimeEntity implements Serializable {

    /**
     * 메뉴 역할 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ROLE_SEQ")
    @ApiModelProperty(name = "menuRoleSeq", value = "메뉴 역할 시퀀스", hidden = true)
    private Long menuRoleSeq;

    /**
     * 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_SEQ")
    @ApiModelProperty(name = "menuSeq", value = "메뉴 시퀀스", hidden = true)
    private Long menuSeq;

    /**
     * 메뉴 기능 코드
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_SKILL_CODE")
    @ApiModelProperty(name = "menuSkillCode", value = "메뉴 기능 코드")
    private String menuSkillCode;

    /**
     * 메뉴 기능 명
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_SKILL_NAME")
    @ApiModelProperty(name = "menuSkillName", value = "메뉴 기능 명")
    private String menuSkillName;

    /**
     * 메뉴 맵핑
     *
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "MENU_SEQ", insertable = false, updatable = false)
    @JsonBackReference
    @ApiModelProperty(name = "menu", value = "메뉴")
    private Menu menu;

    /**
     * 메뉴 역할 리소스 맵핑
     *
     * @author [오지훈]
     */
    /*@OneToMany(mappedBy = "menuRole")
    @JsonManagedReference
    private List<MenuRoleResource> menuRoleResources;*/

    /**
     * 권한 메뉴 역할 맵핑
     *
     * @author [오지훈]
     */
    /*@OneToMany(mappedBy = "menuRole")
    @JsonManagedReference
    private List<AuthMenuRole> authMenuRoles;*/

}
