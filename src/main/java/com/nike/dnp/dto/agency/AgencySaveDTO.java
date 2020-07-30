package com.nike.dnp.dto.agency;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Agency save dto.
 *
 * @author [이소정]
 * @implNote 에이전시 저장 DTO
 * @since 2020. 7. 20. 오후 12:13:03
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class AgencySaveDTO {

    /**
     * The Agency name
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "agencyName", value = "에이젼시 이름", example = "AGENCY1")
    private String agencyName;

    /**
     * The Agency description
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "agencyDescription", value = "에이젼시 설명", example = "NIKE BY YOU 의류/신발 커스텀 물품 관리")
    private String agencyDescription;

    /**
     * The Telephone number
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "telephoneNumber", value = "전화번호", example = "02-123-4567")
    private String telephoneNumber;

    /**
     * The Email
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "email", value = "이메일", example = "example@example.co.kr")
    private String email;

}
