package com.nike.dnp.entity.manage;

import com.nike.dnp.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * ManagerMenu Entity
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description ManagerMenu Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER_MENU")
public class ManagerMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_SEQ")
    private Long menuSeq;

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "MENU_PATH")
    private String menuPath;

    @Column(name = "MENU_CODE")
    private String menuCode;

    @Column(name = "UPPER_MENU_CODE")
    private String upperMenuCode;

    @Column(name = "CREATION_AUTH_YN")
    private String creationAuthYn;

    @Column(name = "UPDATE_AUTH_YN")
    private String updateAuthYn;

    @Column(name = "DELETE_AUTH_YN")
    private String deleteAuthYn;

    @Column(name = "READING_AUTH_YN")
    private String readingAuthYn;

    @Column(name = "DOWNLOAD_AUTH_YN")
    private String downloadAuthYn;

    @Column(name = "UPLOAD_AUTH_YN")
    private String uploadAuthYn;

    @Column(name = "PRINTING_AUTH_YN")
    private String printingAuthYn;

    @Column(name = "EXCEL_AUTH_YN")
    private String excelAuthYn;

    @Column(name = "MENU_ORDER")
    private Long menuOrder;

    @Column(name = "LOW_MENU_YN")
    private String lowMenuYn;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "REGISTER_SEQ")
    private Long registerSeq;

    @Column(name = "UPDATER_SEQ")
    private Long updaterSeq;

    @Builder
    public ManagerMenu(
            String upperMenuCode
            , String menuCode
            , String menuName
            , String menuPath
            , String creationAuthYn
            , String updateAuthYn
            , String deleteAuthYn
            , String readingAuthYn
            , String downloadAuthYn
            , String uploadAuthYn
            , String printingAuthYn
            , String excelAuthYn
            , Long menuOrder
            , String useYn
            , Long registerSeq
    ) {
        this.upperMenuCode = upperMenuCode;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.creationAuthYn = creationAuthYn;
        this.updateAuthYn = updateAuthYn;
        this.deleteAuthYn = deleteAuthYn;
        this.readingAuthYn = readingAuthYn;
        this.downloadAuthYn = downloadAuthYn;
        this.uploadAuthYn = uploadAuthYn;
        this.printingAuthYn = printingAuthYn;
        this.excelAuthYn = excelAuthYn;
        this.menuOrder = menuOrder;
        this.useYn = useYn;
        this.registerSeq = registerSeq;
        this.updaterSeq = registerSeq;
    }

    public void update(
            String menuName
            , String menuPath
            , String creationAuthYn
            , String updateAuthYn
            , String deleteAuthYn
            , String readingAuthYn
            , String downloadAuthYn
            , String uploadAuthYn
            , String printingAuthYn
            , String excelAuthYn
            , Long menuOrder
            , String useYn
            , Long updaterSeq
    ) {
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.creationAuthYn = creationAuthYn;
        this.updateAuthYn = updateAuthYn;
        this.deleteAuthYn = deleteAuthYn;
        this.readingAuthYn = readingAuthYn;
        this.downloadAuthYn = downloadAuthYn;
        this.uploadAuthYn = uploadAuthYn;
        this.printingAuthYn = printingAuthYn;
        this.excelAuthYn = excelAuthYn;
        this.menuOrder = menuOrder;
        this.useYn = useYn;
        this.updaterSeq = updaterSeq;
    }

}
