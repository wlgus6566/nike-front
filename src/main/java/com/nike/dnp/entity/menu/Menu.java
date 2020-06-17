package com.nike.dnp.entity.menu;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Menu Entity
 *
 * @author [오지훈]
 * @Description Menu(메뉴) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MENU")
public class Menu extends BaseTimeEntity implements Serializable {

    /**
     * 메뉴 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_SEQ")
    @ApiModelProperty(name = "menuSeq", value = "메뉴 시퀀스", hidden = true)
    private Long menuSeq;

    /**
     * 상위 메뉴 시퀀스
     * @author [오지훈]
     */
    @Column(name = "UPPER_MENU_SEQ")
    @ApiModelProperty(name = "upperMenuSeq", value = "상위 메뉴 시퀀스", hidden = true)
    private Long upperMenuSeq;

    /**
     * 메뉴명
     * @author [오지훈]
     */
    @Column(name = "MENU_NAME")
    @ApiModelProperty(name = "menuName", value = "메뉴명", required = true)
    private String menuName;

    /**
     * 역할 타입
     * @author [오지훈]
     */
    @Column(name = "MENU_PATH")
    @ApiModelProperty(name = "roleType", value = "역할 타입", required = true)
    private String roleType;

    /**
     * 메뉴 여부
     * @author [오지훈]
     */
    @Column(name = "MENU_YN")
    @ApiModelProperty(name = "menuYn", value = "메뉴 여부", required = true)
    private String menuYn;

    /**
     * 생성 권한 여부
     * @author [오지훈]
     */
    @Column(name = "CREATION_AUTH_YN")
    @ApiModelProperty(name = "creationAuthYn", value = "생성 권한 여부", required = true)
    private String creationAuthYn;

    /**
     * 삭제 권한 여부
     * @author [오지훈]
     */
    @Column(name = "DELETE_AUTH_YN")
    @ApiModelProperty(name = "deleteAuthYn", value = "삭제 권한 여부", required = true)
    private String deleteAuthYn;

    /**
     * 다운로드 권한 여부
     * @author [오지훈]
     */
    @Column(name = "DOWNLOAD_AUTH_YN")
    @ApiModelProperty(name = "downloadAuthYn", value = "다운로드 권한 여부", required = true)
    private String downloadAuthYn;

    /**
     * 엑셀 권한 여부
     * @author [오지훈]
     */
    @Column(name = "EXCEL_AUTH_YN")
    @ApiModelProperty(name = "excelAuthYn", value = "엑셀 권한 여부", required = true)
    private String excelAuthYn;

    /**
     * 메뉴 순서
     * @author [오지훈]
     */
    @Column(name = "MENU_ORDER")
    @ApiModelProperty(name = "menuOrder", value = "메뉴 순서", required = true)
    private Long menuOrder;

    /**
     * 사용 여부
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

    /**
     * 하위 메뉴 목록
     * @author [오지훈]
     */
    @OneToMany
    @JoinColumn(name = "UPPER_MENU_SEQ",
            referencedColumnName = "MENU_SEQ",
            insertable = false, updatable = false)
    private List<Menu> subMenus;

}
