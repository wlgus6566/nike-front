package com.nike.dnp.dto.manage.menu;

import lombok.Getter;
import lombok.Setter;

/**
 * ManagerMenuAuthMappingDTO
 *
 * @since 2020.05.28
 * @author [오지훈]
 * @Description Request > ManagerMenuAuthMappingDTO 작성
 * @history [오지훈] [2020.05.28] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerMenuAuthMappingSaveDTO {

    private Long menuSeq;
    private Long authSeq;
    private Long registerSeq;
    private String useYn;

}
