package com.nike.dnp.entity.log;

import com.nike.dnp.dto.log.EmailSendingLogSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * EmailSendingLog Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:10:18
 * @Description EmailSendingLog(메일_발송_로그) Entity 작성
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
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMAIL_SENDING_LOG_SEQ")
    @ApiModelProperty(name = "emailSendingLogSeq", value = "로그 시퀀스", hidden = true)
    private Long emailSendingLogSeq;

    /**
     * 수신자 이메일
     *
     * @author [오지훈]
     */
    @Column(name = "EMAIL")
    @ApiModelProperty(name = "email", value = "수신자 이메일", required = true)
    private String email;

    /**
     * 제목
     *
     * @author [오지훈]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "제목", required = true)
    private String title;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @Column(name = "CONTENTS")
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * Instantiates a new Email sending log.
     *
     * @param emailSendingLogSaveDTO the email sending log save dto
     * @author [오지훈]
     * @CreatedOn 2020. 7. 28. 오후 4:06:35
     * @Description
     */
    @Builder
    public EmailSendingLog (final EmailSendingLogSaveDTO emailSendingLogSaveDTO) {
        this.email = emailSendingLogSaveDTO.getEmail();
        this.title = emailSendingLogSaveDTO.getTitle();
        this.contents = emailSendingLogSaveDTO.getContents();
    }
}
