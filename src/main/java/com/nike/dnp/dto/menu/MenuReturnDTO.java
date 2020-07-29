package com.nike.dnp.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * MenuReturnDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:52:14
 * @implNote Menu(메뉴) Return DTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuReturnDTO {

    /**
     * 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuSeq", value = "메뉴 시퀀스", hidden = true)
    private Long menuSeq;

    /**
     * 메뉴 코드
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuCode", value = "메뉴 코드", required = true)
    private String menuCode;

    /**
     * 상위 메뉴 시퀀스
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperMenuSeq", value = "상위 메뉴 시퀀스", hidden = true)
    private Long upperMenuSeq;

    /**
     * 메뉴명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuName", value = "메뉴명", required = true)
    private String menuName;

    /**
     * 상위 메뉴 명
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "upperMenuName", value = "상위 메뉴 명", hidden = true)
    private String upperMenuName;

    /**
     * 메뉴 경로 URL
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuPathUrl", value = "메뉴 경로 URL", required = true)
    private String menuPathUrl;

//    /**
//     * 생성 권한 여부
//     *
//     * @author [오지훈]
//     */
//    @ApiModelProperty(name = "creationAuthYn", value = "생성 권한 여부", required = true)
//    private String creationAuthYn;
//
//    /**
//     * 삭제 권한 여부
//     *
//     * @author [오지훈]
//     */
//    @ApiModelProperty(name = "deleteAuthYn", value = "삭제 권한 여부", required = true)
//    private String deleteAuthYn;
//
//    /**
//     * 다운로드 권한 여부
//     *
//     * @author [오지훈]
//     */
//    @ApiModelProperty(name = "downloadAuthYn", value = "다운로드 권한 여부", required = true)
//    private String downloadAuthYn;
//
//    /**
//     * 목록 권한 여부
//     *
//     * @author [오지훈]
//     */
//    @ApiModelProperty(name = "listAuthYn", value = "목록 권한 여부", required = true)
//    private String listAuthYn;
//
//    /**
//     * 상세 권한 여부
//     *
//     * @author [오지훈]
//     */
//    @ApiModelProperty(name = "detailAuthYn", value = "상세 권한 여부", required = true)
//    private String detailAuthYn;
//
//    /**
//     * 리포트 권한 여부
//     *
//     * @author [오지훈]
//     */
//    @ApiModelProperty(name = "reportAuthYn", value = "리포트 권한 여부", required = true)
//    private String reportAuthYn;

}
