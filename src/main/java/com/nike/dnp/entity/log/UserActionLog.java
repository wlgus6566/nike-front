package com.nike.dnp.entity.log;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * UserActionLog Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:10:30
 * @Description UserActionLog(유저_활동_로그) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_ACTION_LOG")
public class UserActionLog extends BaseTimeEntity {

    /**
     * 로그 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_LOG_SEQ")
    @ApiModelProperty(name = "actionLogSeq", value = "로그 시퀀스", hidden = true)
    private Long actionLogSeq;

    /**
     * 사용자 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * URL
     *
     * @author [오지훈]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * 메소드 타입
     *
     * @author [오지훈]
     */
    @Column(name = "METHOD_TYPE_NAME")
    @ApiModelProperty(name = "methodTypeName", value = "메소드 타입", required = true)
    private String methodTypeName;

    /**
     * 호출 메소드 명
     *
     * @author [오지훈]
     */
    @Column(name = "METHOD_SIGNATURE")
    @ApiModelProperty(name = "methodSignature", value = "호출 메소드 명", required = true)
    private String methodSignature;

    /**
     * Parameter
     *
     * @author [오지훈]
     */
    @Column(name = "PARAMETER")
    @ApiModelProperty(name = "parameter", value = "Parameter", required = true)
    private String parameter;

}
