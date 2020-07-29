package com.nike.dnp.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.log.UserLoginLog;
import com.nike.dnp.entity.user.PasswordHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * UserReturnDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 12:14:01
 * @implNote User(유저) Return DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReturnDTO {

    /**
     * 유저 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "유저 시퀀스")
    private Long userSeq;

    /**
     * 유저 ID
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userId", value = "유저 ID")
    private String userId;

    /**
     * 닉네임
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "nickname", value = "닉네임")
    private String nickname;

    /**
     * 유저 IP
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "loginIp", value = "유저 IP")
    private String loginIp;

    /**
     * 로그인 일시
     *
     * @author [오지훈]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "loginDt", value = "로그인 일시")
    private LocalDateTime loginDt;

    /**
     * 유저 상태 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userStatusCode", value = "유저 상태 코드")
    private String userStatusCode;

    /**
     * 유저 상태 코드명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userStatusCodeName", value = "유저 상태 코드명")
    private String userStatusCodeName;

    /**
     * 약관 동의 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "termsAgreeYn", value = "약관 동의 여부")
    private String termsAgreeYn;

    /**
     * 브라우저 헤더
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "loginBrowserHeader", value = "브라우저 헤더")
    private String loginBrowserHeader;

    /**
     * 비밀번호 최종 수정 일시
     *
     * @author [오지훈]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "passwordLastUpdateDt", value = "비밀번호 최종 수정 일시")
    private LocalDateTime passwordLastUpdateDt;

    /**
     * 비밀번호 변경 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "passwordChangeYn", value = "비밀번호 변경 여부")
    private String passwordChangeYn;

    /**
     * 유저권한 맵핑
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "auth", value = "유저 권한")
    private Auth auth;

    /**
     * 권한명(그룹명)
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "authName", value = "권한명(그룹명)")
    private String authName;

    /**
     * 패스워드 기록 맵핑
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "histories", value = "비밀번호 변경 이력")
    private List<PasswordHistory> histories = new ArrayList<>();

    /**
     * 로그인 기록 맵핑
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "loginLogs", value = "로그인 이력")
    private List<UserLoginLog> loginLogs = new ArrayList<>();

    /**
     * Gets user status code name.
     *
     * @return the user status code name
     * @author [오지훈]
     * @since 2020. 7. 6. 오후 2:31:09
     * @implNote 유저 권한명
     */
    public String getUserStatusCodeName() {
        String result = "";
        for (final ServiceCode.UserStatusEnumCode enumCode : ServiceCode.UserStatusEnumCode.values()) {
            if (enumCode.toString().equals(this.userStatusCode)) {
                result = enumCode.getMessage();
                break;
            }
        }
        return result;
    }
}
