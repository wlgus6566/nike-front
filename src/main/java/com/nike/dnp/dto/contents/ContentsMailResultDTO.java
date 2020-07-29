package com.nike.dnp.dto.contents;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Contents list dto.
 *
 * @author [이소정]
 * @since 2020. 7. 13. 오후 2:52:03
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ContentsMailResultDTO {

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드")
    private String topMenuCode;

    /**
     * 2depth 메뉴 코드
     */
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드")
    private String menuCode;

}
