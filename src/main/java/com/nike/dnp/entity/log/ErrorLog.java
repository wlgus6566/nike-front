package com.nike.dnp.entity.log;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * ErrorLog Entity
 *
 * @author [오지훈]
 * @Description ErrorLog(오류 로그) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
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
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ERROR_LOG_SEQ")
    @ApiModelProperty(name = "errorLogSeq", value = "로그 시퀀스", hidden = true)
    private Long errorLogSeq;

    /**
     * URL
     * @author [오지훈]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "URL", required = true)
    private String url;

    /**
     * 오류 내용
     * @author [오지훈]
     */
    @Column(name = "ERROR_CONTENTS")
    @ApiModelProperty(name = "errorContents", value = "오류 내용", required = true)
    private String errorContents;

    /**
     * 최초 작성자
     * @author [오지훈]
     */
    @Column(name = "REGISTER_SEQ")
    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스", hidden = true, required = true)
    private Long registerSeq;

    /**
     * 최종 수정자
     * @author [오지훈]
     */
    @Column(name = "UPDATER_SEQ")
    @ApiModelProperty(name = "updaterSeq", value = "최종 수정자 시퀀스", hidden = true, required = true)
    private Long updaterSeq;

}
