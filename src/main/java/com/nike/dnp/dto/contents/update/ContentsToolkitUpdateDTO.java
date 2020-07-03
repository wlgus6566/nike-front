package com.nike.dnp.dto.contents.update;

import com.nike.dnp.dto.contents.save.ContentsSaveDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * The Class Contents toolkit update dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 3. 오후 2:51:29
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsToolkitUpdateDTO extends ContentsUpdateDTO {

    /**
     * 업로드위치(2depth 메뉴 코드)
     * @author [이소정]
     */
    @ApiModelProperty(name = "uploadCode", value = "업로드위치(2depth 메뉴 코드)", required = true, example = "T_VMS")
    private String uploadCode;
}
