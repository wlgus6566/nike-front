package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The Class Contents list dto.
 *
 * @author [이소정]
 * @implNote 컨텐츠 결과 DTO
 * @since 2020. 7. 13. 오후 2:52:03
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ContentsResultDTO extends BaseTimeEntity {

    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 최고 메뉴 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드")
    private String topMenuCode;

    /**
     * 2depth 메뉴 코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드")
    private String menuCode;

    /**
     * 이미지 파일 물리명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", example = "8080/cdn/contnets/")
    private String imageFilePhysicalName;

    /**
     * 폴더명
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderName", value = "폴더명", example = "SP20 NSW NIKE DIRECT AM90")
    private String folderName;

    /**
     * 폴더 내용
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderContents", value = "폴더 내용", required = true, example = "SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료")
    private String folderContents;

    /**
     * 캠페인 기간 구분 공통코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignPeriodSectionCode", value = "캠페인 기간 구분 공통코드(날짜선택:SELECT/365:EVERY)", required = true, example = "EVERY")
    private String campaignPeriodSectionCode;

    /**
     * 캠페인 시작 일시
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignBeginDt", value = "캠페인 시작 일시", example = "2020.06.01 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime campaignBeginDt;

    /**
     * 캠페인 종료 일시
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignEndDt", value = "캠페인 종료 일시", example = "2020.09.01 23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime campaignEndDt;

    /**
     * 메모
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "memo", value = "메모")
    private String memo;

    /**
     * 조회수
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "readCount", value = "조회수")
    private Long readCount;

    /**
     * 상세 권한 여부
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "detailAuthYn", value = "상세 권한 여부(N : 권한없음)", example = "Y")
    private String detailAuthYn = "N";

    /**
     * 노출 여부
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "exposureYn", value = "노출 여부(N : 진행중)", example = "Y")
    private String exposureYn;

//    /**
//     * The Checks
//     *
//     * @author [이소정]
//     */
//    @ApiModelProperty(name = "checks", value = "컨텐츠 권한 목록")
//    private List<AuthReturnDTO> checks;

    /**
     * The Recipients count
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "recipientsCount", value = "메일 수신자 갯수")
    private int recipientsCount = 0;

    /**
     * 유저 ID
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "userId", value = "유저 ID", required = true, example = "test@nike.co.kr")
    private String userId;

    /**
     * Gets image file physical name.
     *
     * @return the image file physical name
     * @author [이소정]
     * @implNote cndUrl + imageFilePhysicalName
     * @since 2020. 7. 30. 오후 3:44:43
     */
    public String getImageFilePhysicalName() {
        return CloudFrontUtil.getCustomSignedUrl(imageFilePhysicalName);
    }



}
