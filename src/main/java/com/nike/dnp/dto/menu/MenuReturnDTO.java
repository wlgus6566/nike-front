package com.nike.dnp.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.entity.menu.MenuRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;
import java.util.List;

/**
 * MenuReturnDTO
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 19. 오후 4:52:14
 * @Description Menu(메뉴) Return DTO 작성
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

    /**
     * 생성 권한 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "creationAuthYn", value = "생성 권한 여부", required = true)
    private String creationAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 삭제 권한 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "deleteAuthYn", value = "삭제 권한 여부", required = true)
    private String deleteAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 다운로드 권한 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "downloadAuthYn", value = "다운로드 권한 여부", required = true)
    private String downloadAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 목록 권한 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "listAuthYn", value = "목록 권한 여부", required = true)
    private String listAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 상세 권한 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "detailAuthYn", value = "상세 권한 여부", required = true)
    private String detailAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 리포트 권한 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "reportAuthYn", value = "리포트 권한 여부", required = true)
    private String reportAuthYn = ServiceEnumCode.yesOrNoEnumCode.N.toString();

    /**
     * 메뉴 순서
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "menuOrder", value = "메뉴 순서", required = true)
    private Long menuOrder;

    /**
     * 사용 여부
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "useYn", value = "사용 여부", required = true)
    private String useYn;

    /**
     * 스킬 코드 목록
     *
     * @author [오지훈]
     */
    private HashMap<Integer, Object> SkillCodes;

    /**
     * 하위 메뉴 목록
     *
     * @author [오지훈]
     */
    private List<Menu> subMenus;

    private List<MenuRole> menuRoles;

    /**
     * 스킬 코드 목록 셋팅
     *
     * @return the skill codes
     * @author [오지훈]
     * @CreatedOn 2020. 7. 8. 오후 6:13:03
     * @Description
     */
    public HashMap<Integer, Object> getSkillCodes() {
        HashMap<Integer, Object> map = new HashMap<>();
        for (ServiceEnumCode.MenuSkillEnumCode enumCode : ServiceEnumCode.MenuSkillEnumCode.values()) {
            HashMap<String, String> sub = new HashMap<>();
            sub.put("code", enumCode.toString());
            sub.put("field", enumCode.getField());
            sub.put("message", enumCode.getMessage());
            map.put(enumCode.getSort(), sub);
        }

        return map;
    }

}
