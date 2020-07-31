package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * The Class Contents update dto.
 *
 * @author [이소정]
 * @since 2020. 7. 21. 오후 3:54:34
 * @implNote 컨텐츠 메일 전송 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsMailSendDTO {

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스", example = "4")
    private Long contentsSeq;

    /**
     * 컨텐츠 url
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "컨텐츠 url", example = "/contents/detail/4")
    private String contentsUrl;

}
