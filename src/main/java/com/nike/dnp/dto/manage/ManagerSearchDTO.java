package com.nike.dnp.dto.manage;

import lombok.Getter;
import lombok.Setter;

/**
 * ManagerSearchDTO
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description Request > 검색용 DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerSearchDTO {

    private Long searchManagerSeq;

    private String searchManagerName;

    private String searchManagerId;

}
