package com.nike.dnp.entity.menu;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * MenuRoleResource Entity
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:10:46
 * @implNote MenuRoleResource(메뉴 역할 리소스) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MENU_ROLE_RESOURCE")
public class MenuRoleResource extends BaseTimeEntity implements Serializable {

    /**
     * 메뉴 역할 리소스 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ROLE_RESOURCE_SEQ")
    @ApiModelProperty(name = "menuRoleResourceSeq", value = "메뉴 역할 리소스 시퀀스", hidden = true)
    private Long menuRoleResourceSeq;

    /**
     * 메뉴 역할 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "MENU_ROLE_SEQ")
    @ApiModelProperty(name = "menuRoleSeq", value = "메뉴 역할 시퀀스", hidden = true)
    private Long menuRoleSeq;

    /**
     * 리소스 URL
     *
     * @author [오지훈]
     */
    @Column(name = "RESOURCE_URL")
    @ApiModelProperty(name = "resourceUrl", value = "리소스 URL")
    private String resourceUrl;

    /**
     * 리소스 메소드
     *
     * @author [오지훈]
     */
    @Column(name = "RESOURCE_METHOD")
    @ApiModelProperty(name = "resourceMethod", value = "리소스 메소드")
    private String resourceMethod;

    /**
     * 메뉴 역할
     *
     * @author [오지훈]
     */
    /*@ManyToOne
    @JoinColumn(name = "MENU_ROLE_SEQ", insertable = false, updatable = false)
    //@JsonBackReference
    private MenuRole menuRole;*/

}
