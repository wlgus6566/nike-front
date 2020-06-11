package com.nike.dnp.dto.example.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ManagerSaveDTO {

    /**
     *
     */
    private Long authSeq;

    /**
     *
     */
    private String managerId;

    /**
     *
     */
    private String managerName;

    /**
     *
     */
    private String password;

    /**
     *
     */
    @ApiModelProperty(hidden = true)
    private Long registerSeq;

    /**
     * Method to String
     * @return String
     */
    public String toString() {
        return "ManagerSaveDTO{"
                + "authSeq=" + authSeq
                + ", managerId=" + managerId
                + ", managerName=" + managerName
                + ", password=" + password + '}';
    }
}
