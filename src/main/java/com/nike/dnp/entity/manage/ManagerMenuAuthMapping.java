package com.nike.dnp.entity.manage;

import com.nike.dnp.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * ManagerMenuAuthMapping Entity
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description ManagerMenuAuthMapping Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER_MENU_AUTH_MAPPING")
public class ManagerMenuAuthMapping extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAPPING_SEQ")
    private Long mappingSeq;

    @ManyToOne
    @JoinColumn(name = "MENU_SEQ")
    private ManagerMenu managerMenu;

    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ")
    private ManagerAuth managerAuth;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "REGISTER_SEQ")
    private Long registerSeq;

    @Column(name = "UPDATER_SEQ")
    private Long updaterSeq;

    @Builder
    public ManagerMenuAuthMapping(
            ManagerMenu managerMenu
            , ManagerAuth managerAuth
            , String useYn
            , Long registerSeq
    ) {
        this.managerMenu = managerMenu;
        this.managerAuth = managerAuth;
    }

}
