package com.nike.dnp.entity.auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.menu.MenuRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AuthMenuRole Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:47
 * @Description AuthMenuRole(권한_메뉴_역할) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_AUTH_MENU_ROLE")
public class AuthMenuRole extends BaseTimeEntity implements Serializable {

    /**
     * 권한 메뉴 역할 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_MENU_ROLE_SEQ")
    @ApiModelProperty(name = "authMenuRoleSeq", value = "권한 메뉴 역할 시퀀스", hidden = true)
    private Long authMenuRoleSeq;

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "AUTH_SEQ")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", hidden = true)
    private Long authSeq;

    /**
     * 메뉴 역할 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_ROLE_SEQ")
    @ApiModelProperty(name = "menuRoleSeq", value = "메뉴 역할 시퀀스", hidden = true)
    private Long menuRoleSeq;

    /**
     * 권한
     *
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ", insertable = false, updatable = false)
    @JsonBackReference
    private Auth auth;

    /**
     * 메뉴 역할
     *
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "MENU_ROLE_SEQ", insertable = false, updatable = false)
    //@JsonBackReference
    private MenuRole menuRole;

}
