package com.nike.dnp.dto.auth;

import com.nike.dnp.common.variable.ServiceEnumCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Auth return dto.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:21:03
 * @Description Auth(권한) Return DTO 작성
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
     * 상위 권한 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperAuthSeq", value = "상위 권한 시퀀스", required = true)
    private Long upperAuthSeq;

    /**
     * 권한명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authName", value = "권한 명", required = true)
    private String authName;

    /**
     * 상위 권한 명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperAuthName", value = "상위 권한 명", required = true)
    private String upperAuthName;

    /**
     * 상세_권한_여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "detailAuthYn", value = "상세_권한_여부", required = true, example = "N")
    private String DETAIL_AUTH_YN = ServiceEnumCode.YesOrNoEnumCode.N.toString();

    /**
     * 메일_수신_여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "emailReceptionYn", value = "메일_수신_여부", required = true, example = "N")
    private String EMAIL_RECEPTION_YN = ServiceEnumCode.YesOrNoEnumCode.N.toString();

}
