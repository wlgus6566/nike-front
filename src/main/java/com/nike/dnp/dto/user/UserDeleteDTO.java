package com.nike.dnp.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * UserDeleteDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 12:14:01
 * @Description User(유저) 유저시퀀스(배열) 삭제 DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UserDeleteDTO {

    /**
     * 유저 시퀀스 배열
     *
     * @author [오지훈]
     */
    @ApiModelProperty(value = "유저 시퀀스 배열", name = "userSeqArray", required = true)
    private Long[] userSeqArray;


}
