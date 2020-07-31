package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;


/**
 * The Class Contents update dto.
 *
 * @author [이소정]
 * @implNote 컨텐츠 메일 전송 DTO
 * @since 2020. 7. 21. 오후 3:54:34
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsMailSendDTO {

    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @NotNull(message = "contentsMailSend.contentsSeq")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스", example = "4")
    private Long contentsSeq;

    /**
     * 컨텐츠 url
     *
     * @author [이소정]
     */
    @NotNull(message = "contentsMailSend.contentsUrl")
    @ApiModelProperty(name = "contentsUrl", value = "컨텐츠 url", example = "/contents/detail/4")
    private String contentsUrl;

}
