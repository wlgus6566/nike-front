package com.nike.dnp.entity.log;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * EmailSendingLog Entity
 *
 * @author [오지훈]
 * @Description EmailSendingLog(메일_발송_로그) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_EMAIL_SENDING_LOG")
public class EmailSendingLog extends BaseTimeEntity {

    /**
     * 로그 시퀀스
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMAIL_SENDING_LOG_SEQ")
    @ApiModelProperty(name = "emailSendingLogSeq", value = "로그 시퀀스", hidden = true)
    private Long emailSendingLogSeq;

    /**
     * 사용자 시퀀스
     * @author [오지훈]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * 제목
     * @author [오지훈]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "제목", required = true)
    private String title;

    /**
     * 내용
     * @author [오지훈]
     */
    @Column(name = "CONTENTS")
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

}
