package com.nike.dnp.entity.auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AuthMenuRole Entity
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:09:47
 * @implNote AuthMenuRole(권한_메뉴_역할) Entity 작성
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
    @ApiModelProperty(name = "auth", value = "권한")
    private Auth auth;

    /*@ManyToOne
    @JoinColumn(name = "MENU_ROLE_SEQ", insertable = false, updatable = false)
    //@JsonBackReference
    private MenuRole menuRole;*/

    /**
     * Instantiates a new Auth menu role.
     *
     * @param authSeq     the auth seq
     * @param menuRoleSeq the menu role seq
     * @author [오지훈]
     * @since 2020. 7. 13. 오후 3:33:32
     * @implNote 등록
     */
    @Builder
    public AuthMenuRole (
            final Long authSeq
            , final Long menuRoleSeq
    ) {
        super();
        this.authSeq = authSeq;
        this.menuRoleSeq = menuRoleSeq;
    }
}
