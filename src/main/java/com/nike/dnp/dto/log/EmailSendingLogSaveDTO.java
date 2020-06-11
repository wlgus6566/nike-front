package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * ErrorLogSaveDTO
 *
 * @author [오지훈]
 * @Description ErrorLog(오류 로그) Save DTO 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class EmailSendingLogSaveDTO {

    /**
     * 사용자 시퀀스
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userSeq", value = "사용자 시퀀스", required = true)
    private Long userSeq;

    /**
     * 제목
     * @author [오지훈]
     */
    @ApiModelProperty(name = "title", value = "제목", required = true)
    private String title;

    /**
     * 내용
     * @author [오지훈]
     */
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * Method to String
     * @return String
     */
    public String toString() {
        return "EmailSendingLogSaveDTO{"
                + "userSeq=" + userSeq
                + ", size=" + title
                + ", page=" + contents + '}';
    }

}
