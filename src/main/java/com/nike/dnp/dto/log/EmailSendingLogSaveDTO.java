package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * ErrorLogSaveDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:03
 * @Description ErrorLog(오류 로그) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class EmailSendingLogSaveDTO {

    /**
     * 사용자 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "email", value = "수신 이메일", required = true)
    private String email;

    /**
     * 제목
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "title", value = "제목", required = true)
    private String title;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * Instantiates a new Email sending log save dto.
     *
     * @param email    the email
     * @param title    the title
     * @param contents the contents
     * @author [오지훈]
     * @CreatedOn 2020. 7. 28. 오후 3:56:42
     * @Description Builder 생성
     */
    @Builder
    public EmailSendingLogSaveDTO (
            final String email
            , final String title
            , final String contents
    ) {
        this.email = email;
        this.title = title;
        this.contents = contents;
    }

}
