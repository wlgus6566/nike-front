package com.nike.dnp.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * UserContentsSaveDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 12:14:01
 * @implNote UserContents(유저 컨텐츠 권한) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserContentsSaveDTO {

    /**
     * 권한 체크 목록
     *
     * @author [오지훈]
     */
    @NotNull(message = "userContents.checks")
    @ApiModelProperty(name = "authChecks", value = "권한 체크 목록")
    private List<AuthCheckDTO> checks = new ArrayList<>();

    /**
     * The Class Auth check.
     *
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 12:23:51
     * @implNote 권한 체크 DTO
     */
    @Getter
    @Setter
    public static class AuthCheckDTO {

        /**
         * The Auth seq
         *
         * @author [오지훈]
         */
        @ApiModelProperty(name = "authSeq", value = "권한 시퀀스")
        private Long authSeq;

        /**
         * The Detail auth yn
         *
         * @author [오지훈]
         */
        @ApiModelProperty(name = "detailAuthYn", value = "상세_권한_여부", example = "N")
        private String detailAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

        /**
         * The Email reception yn
         *
         * @author [오지훈]
         */
        @ApiModelProperty(name = "emailReceptionYn", value = "메일_수신_여부", example = "N")
        private String emailReceptionYn = ServiceCode.YesOrNoEnumCode.N.toString();

    }

}
