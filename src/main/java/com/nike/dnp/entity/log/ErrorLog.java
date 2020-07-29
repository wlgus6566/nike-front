package com.nike.dnp.entity.log;

import com.nike.dnp.dto.log.ErrorLogSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * ErrorLog Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:10:25
 * @Description ErrorLog(오류 로그) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_ERROR_LOG")
public class ErrorLog extends BaseTimeEntity {

    /**
     * 로그 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ERROR_LOG_SEQ")
    @ApiModelProperty(name = "errorLogSeq", value = "로그 시퀀스", hidden = true)
    private Long errorLogSeq;

    /**
     * URL
     *
     * @author [오지훈]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * 오류 내용
     *
     * @author [오지훈]
     */
    @Column(name = "ERROR_CONTENTS")
    @ApiModelProperty(name = "errorContents", value = "오류 내용", required = true)
    private String errorContents;

    /**
     * Instantiates a new Error log.
     *
     * @param errorLogSaveDTO the error log save dto
     * @author [오지훈]
     * @CreatedOn 2020. 7. 28. 오후 4:26:35
     * @Description
     */
    @Builder
    public ErrorLog (final ErrorLogSaveDTO errorLogSaveDTO) {
        this.url = errorLogSaveDTO.getUrl();
        this.errorContents = errorLogSaveDTO.getErrorContents();
    }

}
