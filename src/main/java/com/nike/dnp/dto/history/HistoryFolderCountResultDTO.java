package com.nike.dnp.dto.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class HistoryFolderCountResultDTO {
    /**
     * 메뉴 구분 코드
     *
     * @author [최미영]
     */

    @ApiModelProperty(name = "fileSectionCode", value = "메뉴 구분 코드 공통코드 (ALL,ASSET,TOOLKIT,FOUNDATION,REPORT)")
    private String sectionCode = "";

    /**
     * The Count
     */
    @ApiModelProperty(name = "count", value = "파일 갯수")
    private Long count;
}
