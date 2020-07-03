package com.nike.dnp.dto.contents.update;

import com.nike.dnp.dto.contents.save.ContentsSaveDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * The Class Contents foundation update dto.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 3. 오후 3:39:41
 * @Description
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsFoundationUpdateDTO extends ContentsUpdateDTO {

    /**
     * 업로드위치(2depth 메뉴 코드)
     * @author [이소정]
     */
    @ApiModelProperty(name = "uploadCode", value = "업로드위치(2depth 메뉴 코드)", required = true, example = "F_VMS")
    private String uploadCode;
}
