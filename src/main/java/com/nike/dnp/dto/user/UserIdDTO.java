package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * UserIdDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 12:14:01
 * @Description User(유저) ID로 검색 DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserIdDTO {

    /**
     * The User id
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "유저 ID", name = "userId")
    private String userId;


}
