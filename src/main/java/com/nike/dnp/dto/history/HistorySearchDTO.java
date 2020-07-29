package com.nike.dnp.dto.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class History search dto.
 *
 * @author [이소정]
 * @since 2020. 7. 23. 오전 11:24:45
 * @implNote
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class HistorySearchDTO extends SearchDTO {

    /**
     * The Type cd
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "typeCd", value ="타입 코드(ALL/ASSET/TOOLKIT/FOUNDATION/REPORT_MANAGE)", example = "ALL")
    private String typeCd = "";

    /**
     * 최초 등록자
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "최초 등록자", hidden = true)
    private Long registerSeq;
}
