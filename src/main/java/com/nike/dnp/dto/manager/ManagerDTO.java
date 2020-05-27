package com.nike.dnp.dto.manager;

import com.nike.dnp.entity.manager.Manager;
import lombok.Getter;
import lombok.Setter;

/**
 * ManagerDTO
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description Request > ManagerDTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@Setter
public class ManagerDTO extends Manager {

    private String managerId;
    private String password;
    private String managerName;
    private Long authSeq;


}
