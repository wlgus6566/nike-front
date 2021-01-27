package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;


/**
 * The Class Contents file count result dto.
 *
 * @author [이소정]
 * @since 2021. 1. 27. 오후 2:40:00
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ContentsFileCountResultDTO {

    /**
     * 파일 구분 코드
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드 (ATTRACT,ENGAGE,RN,TR,NSW,FB,BB,JD,KIDS,OTHERS,DIGITAL,GUIDE,VIDEO,VR)")
    private String sectionCode = "";

    /**
     * The Count
     */
    @ApiModelProperty(name = "count", value = "파일 갯수수")
    private Long count;

}
