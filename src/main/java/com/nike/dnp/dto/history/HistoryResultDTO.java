package com.nike.dnp.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * The Class History result dto.
 *
 * @author [이소정]
 * @since 2020. 7. 23. 오전 11:27:50
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class HistoryResultDTO {

    /**
     * The History seq
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "historySeq", value ="히스토리 시퀀스", example = "1")
    private Long historySeq;

    /**
     * The Type cd
     *
     * @author [이소정]
     */
    @Column(name = "TYPE_CD")
    @ApiModelProperty(name = "typeCd", value ="타입 코드(ALL/ASSET/TOOLKIT/FOUNDATION/REPORT_MANAGE)", example = "ALL")
    private String typeCd;

    /**
     * The Folder seq.
     */
    @Column(name = "FOLDER_SEQ")
    @ApiModelProperty(name = "folderSeq", value ="폴더 시퀀스(콘텐츠, 리포트 시퀀스)", example = "4")
    private Long folderSeq;

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @Column(name = "TOP_MENU_CODE")
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드", example = "ASSET")
    private String topMenuCode;

    /**
     * 2depth 메뉴 코드
     */
    @Column(name = "MENU_CODE")
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드", example = "SP")
    private String menuCode;

    /**
     * 이미지 파일명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_NAME")
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일명", example = "main_img.jpg")
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_SIZE")
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈",  example = "500")
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", example = "8080/cdn/contnets/")
    private String imageFilePhysicalName;

    /**
     * 폴더명
     * @author [이소정]
     */
    @Column(name = "FOLDER_NAME")
    @ApiModelProperty(name = "folderName", value = "폴더명", example = "SP20 NSW NIKE DIRECT AM90")
    private String folderName;

    /**
     * 폴더 내용
     * @author [이소정]
     */
    @Column(name = "FOLDER_CONTENTS")
    @ApiModelProperty(name = "folderContents", value = "폴더 내용", required = true, example = "SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료")
    private String folderContents;

    /**
     * 캠페인 기간 구분 공통코드
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_PERIOD_SECTION_CODE")
    @ApiModelProperty(name = "campaignPeriodSectionCode", value = "캠페인 기간 구분 공통코드(날짜선택:SELECT/365:EVERY)", required = true, example = "EVERY")
    private String campaignPeriodSectionCode;

    /**
     * 캠페인 시작 일시
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_BEGIN_DT")
    @ApiModelProperty(name = "campaignBeginDt", value = "캠페인 시작 일시", example = "2020.06.01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime campaignBeginDt;

    /**
     * 캠페인 종료 일시
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_END_DT")
    @ApiModelProperty(name = "campaignEndDt", value = "캠페인 종료 일시", example = "2020.09.01 23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime campaignEndDt;

    /**
     * 조회수
     * @author [이소정]
     */
    @Column(name = "READ_COUNT")
    @ApiModelProperty(name = "readCount", value = "조회수")
    private Long readCount;

    /**
     * 최종 수정일
     *
     * @author [오지훈]
     */
    @Column(name = "UPDATE_DT")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @UpdateTimestamp
    @ApiModelProperty(name = "updateDt", value = "최종 수정일", hidden = true)
    private LocalDateTime updateDt;
}
