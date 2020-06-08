package com.nike.dnp.dto.manage.menu;

import com.nike.dnp.dto.manage.BasicDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * ManagerMenuDTO
 *
 * @since 2020.05.28
 * @author [오지훈]
 * @Description Request > ManagerMenuDTO 작성
 * @history [오지훈] [2020.05.28] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerMenuSaveDTO extends BasicDTO {

    private String menuName;
    private String menuPath;
    private String upperMenuCode;
    private String menuCode;
    private String creationAuthYn;
    private String listAuthYn;
    private String detailAuthYn;
    private String updateAuthYn;
    private String deleteAuthYn;
    private String downloadAuthYn;
    private String uploadAuthYn;
    private String printingAuthYn;
    private String excelAuthYn;
    private String lowMenuYn;
    private Long menuOrder;

}
