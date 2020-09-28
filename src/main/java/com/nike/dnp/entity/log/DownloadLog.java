package com.nike.dnp.entity.log;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * DownLoadLog Entity
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:10:25
 * @implNote DownLoadLog(다운로드 로그) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_DOWNLOAD_LOG")
@Slf4j
public class DownloadLog extends BaseTimeEntity {

    /**
     * 로그 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOWNLOAD_LOG_SEQ")
    @ApiModelProperty(name = "downloadLogSeq", value = "로그 시퀀스", hidden = true)
    private Long downloadLogSeq;

    /**
     * 다운로드 구분
     *
     * @author [오지훈]
     */
    @Column(name = "DOWNLOAD_TYPE")
    @ApiModelProperty(name = "downloadType", value = "다운로드 구분", required = true)
    private String downloadType;

    /**
     * 파일 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "FILE_SEQ")
    @ApiModelProperty(name = "fileSeq", value = "파일 시퀀스", required = true)
    private Long fileSeq;

    /**
     * 파일 시퀀스 배열
     * 같이 다운로드 받은 파일
     *
     * @author [오지훈]
     */
    @Column(name = "FILE_SEQ_ARRAY")
    @ApiModelProperty(name = "fileSeqArray", value = "파일 시퀀스 배열", required = true)
    private String fileSeqArray;

    /**
     * Instantiates a new Download log.
     *
     * @param downloadType the download type
     * @param fileSeq      the file seq
     * @param fileSeqArray the file seq array
     * @author [오지훈]
     * @implNote 생성자 주입
     * @since 2020. 9. 28. 오전 10:36:00
     */
    @Builder
    public DownloadLog (
            final String downloadType
            , final Long fileSeq
            , final String fileSeqArray
    ) {
        log.info("DownloadLog.DownloadLog");
        this.downloadType = downloadType;
        this.fileSeq = fileSeq;
        this.fileSeqArray = fileSeqArray;
    }
}
