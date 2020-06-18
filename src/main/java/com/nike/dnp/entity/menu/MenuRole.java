package com.nike.dnp.entity.menu;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * MenuRole Entity
 *
 * @author [오지훈]
 * @Description MenuRole(메뉴 역할) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
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
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ROLE_SEQ")
    @ApiModelProperty(name = "menuRoleSeq", value = "메뉴 역할 시퀀스", hidden = true)
    private Long menuRoleSeq;

    /**
     * 메뉴 시퀀스
     * @author [오지훈]
     */
    @Column(name = "MENU_SEQ")
    @ApiModelProperty(name = "menuSeq", value = "메뉴 시퀀스", hidden = true)
    private Long menuSeq;

    /**
     * 메뉴 기능 명
     * @author [오지훈]
     */
    @Column(name = "MENU_SKILL_NAME")
    @ApiModelProperty(name = "menuSkillName", value = "메뉴 기능 명")
    private String menuSkillName;

    /**
     * 메뉴
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "MENU_SEQ")
    private Menu menu;

}
