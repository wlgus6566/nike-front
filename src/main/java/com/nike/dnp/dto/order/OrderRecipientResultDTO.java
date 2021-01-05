package com.nike.dnp.dto.order;

import com.nike.dnp.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.List;

/**
 * The Class Order recipient result dto.
 *
 * @author [이소정]
 * @since 2021. 1. 5. 오후 4:08:15
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderRecipientResultDTO {

    /**
     * The Depth check yn
     */
    @ApiModelProperty(name = "depthCheckYn", value ="3depth 여부", example = "Y/N")
    private String depthCheckYn;

    /**
     * The Recipient list
     */
    @ApiParam(value = "계정 목록", name = "userList")
    private List<UserDTO> userList;

}
