package com.nike.dnp.dto.auth;

import com.nike.dnp.common.variable.ServiceCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Auth return dto.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:21:03
 * @implNote Auth(권한) Return DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class AuthReturnDTO {

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", required = true)
    private Long authSeq;

    /**
     * 상세_권한_여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "detailAuthYn", value = "상세_권한_여부", required = true, example = "N")
    private String detailAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

    /**
     * 메일_수신_여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "emailReceptionYn", value = "메일_수신_여부", required = true, example = "N")
    private String emailReceptionYn = ServiceCode.YesOrNoEnumCode.N.toString();

}
