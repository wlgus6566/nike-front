package com.nike.dnp.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * The Class Auth return dto.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:21:03
 * @implNote Auth(권한) Return DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
     * 권한 명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authName", value = "권한 명", required = true)
    private String authName;

    /**
     * 권한 역
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "roleType", value = "권한 역", required = true)
    private String roleType;

    /**
     * 권한 depth
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authDepth", value = "권한 depth", required = true)
    private Long authDepth;

    /**
     * 하위 권한
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "subAuths", value = "하위 권한", required = true)
    private List<AuthReturnDTO> subAuths;

    /**
     * 상세_권한_여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "detailAuthYn", value = "상세_권한_여부", required = true, example = "N")
    private String detailAuthYn = ServiceCode.YesOrNoEnumCode.N.name();

    /**
     * 메일_수신_여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "emailReceptionYn", value = "메일_수신_여부", required = true, example = "N")
    private String emailReceptionYn = ServiceCode.YesOrNoEnumCode.N.name();

    /**
     * 컨텐츠 권한 목록 노출 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "viewYn", value = "컨텐츠 권한 목록 노출 여부", required = true, example = "N")
    private String viewYn = ServiceCode.YesOrNoEnumCode.N.name();

    /**
     * 컨텐츠 권한 목록 체크박스 노출 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "checkBoxYn", value = "컨텐츠 권한 목록 체크박스 노출 여부", required = true, example = "N")
    private String checkBoxYn = ServiceCode.YesOrNoEnumCode.N.name();

    /**
     * 권한 사용 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true, example = "N")
    private String useYn;

}
