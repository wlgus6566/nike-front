package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

/**
 * The type Contents user email dto.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsUserEmailDTO {

    /**
     * The User id.
     */
    @Column(name = "USER_ID")
    @ApiModelProperty(name = "userId", value = "유저 ID", required = true, example = "test@nike.co.kr")
    private String userId;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", required = true)
    private String imageFilePhysicalName;

}
