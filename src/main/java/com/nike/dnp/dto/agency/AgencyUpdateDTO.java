package com.nike.dnp.dto.agency;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Agency update dto.
 *
 * @author [이소정]
 * @implNote 에이전시 수정 DTO
 * @since 2020. 7. 20. 오후 12:16:57
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class AgencyUpdateDTO {

    /**
     * The Agency seq
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스", example = "3")
    private long agencySeq;

    /**
     * The Agency name
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "agencyName", value = "에이젼시 이름", example = "AGENCY100")
    private String agencyName;

    /**
     * The Agency description
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "agencyDescription", value = "에이젼시 설명", example = "NIKE 의류/신발 커스텀 물품 관리")
    private String agencyDescription;

    /**
     * The Telephone number
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "telephoneNumber", value = "전화번호", example = "02-789-4561")
    private String telephoneNumber;

    /**
     * The Email
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "email", value = "이메일", example = "update@update.co.kr")
    private String email;
}
