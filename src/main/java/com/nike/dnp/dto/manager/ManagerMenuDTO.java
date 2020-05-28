package com.nike.dnp.dto.manager;

import com.nike.dnp.entity.manager.ManagerMenu;
import lombok.Builder;
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
public class ManagerMenuDTO extends ManagerMenu {

    @Builder
    public ManagerMenuDTO() {

    }

}
