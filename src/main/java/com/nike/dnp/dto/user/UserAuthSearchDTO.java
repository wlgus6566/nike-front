package com.nike.dnp.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * UserContentsSearchDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 22. 오후 12:14:01
 * @implNote UserContents(유저 컨텐츠 권한) Search DTO 작성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserAuthSearchDTO {

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @NotBlank(message = "userContents.menuCode")
    @ApiModelProperty(name = "menuCode", value = "메뉴 코드", example = "ASSET_SP")
    private String menuCode;

    /**
     * 스킬 코드
     *
     * @author [오지훈]
     */
    @NotBlank(message = "userContents.skillCode")
    @ApiModelProperty(name = "skillCode", value = "스킬 코드", example = "VIEW")
    private String skillCode;

    /**
     * 콘텐츠 일련번호
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsSeq", value = "콘텐츠 일련번호", example = "5", hidden = true)
    private Long contentsSeq = 0l;

    /**
     * 등록 여부
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "createYn", value = "등록여부", example = "Y", hidden = true)
    private String createYn = "N";

    /**
     * The Menu code list
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "createYn", value = "메뉴 코드 목록", hidden = true)
    private List<String> menuCodeList;

}
