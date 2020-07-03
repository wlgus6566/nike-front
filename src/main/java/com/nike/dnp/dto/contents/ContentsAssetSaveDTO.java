package com.nike.dnp.dto.contents;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * The Class Contents save dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 3:25:23
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsAssetSaveDTO extends ContentsSaveDTO {

    /**
     * 업로드위치(2depth 메뉴 코드)
     * @author [이소정]
     */
    @ApiModelProperty(name = "uploadCode", value = "업로드위치(2depth 메뉴 코드)", required = true, example = "A_SP")
    private String uploadCode;
}
