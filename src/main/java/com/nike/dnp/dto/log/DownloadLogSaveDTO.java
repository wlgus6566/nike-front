package com.nike.dnp.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DownloadLogSaveDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:09:06
 * @implNote Download Log(다운로드 로그) Save DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class DownloadLogSaveDTO {

    /**
     * 시퀀스 배열
     *
     * @author [오지훈]
     */
    @NotNull(message = "download.seq")
    @ApiModelProperty(name = "seqArray", value = "시퀀스 배열", required = true)
    private Long[] seqArray;

    /**
     * 다운로드 구분
     *
     * @author [오지훈]
     */
    @NotBlank(message = "download.type")
    @ApiModelProperty(name = "downloadType", value = "다운로드 구분", required = true, allowableValues = "CONTENTS,REPORT")
    private String downloadType;

    /**
     * The DEVICE
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "device", value = "디바이스 정보", example = "PC", hidden = true)
    private String device;

}
