package com.nike.dnp.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * SkillCodeDTO
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:52:14
 * @implNote SkillCodeDTO 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SkillCodeDTO {

    /**
     * The Menu seq
     *
     * @author [오지훈]
     */
    private Long menuSeq;
    /**
     * The Code
     *
     * @author [오지훈]
     */
    private String code;
    /**
     * The Field
     *
     * @author [오지훈]
     */
    private String field;
    /**
     * The Message
     *
     * @author [오지훈]
     */
    private String message;
    /**
     * The Menu role seq
     *
     * @author [오지훈]
     */
    private Long menuRoleSeq;

    /**
     * Instantiates a new Skill code.
     *
     * @param menuSeq     the menu seq
     * @param code        the code
     * @param field       the field
     * @param message     the message
     * @param menuRoleSeq the menu role seq
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 3:51:09
     * @implNote
     */
    @Builder
    public SkillCodeDTO (
            final Long menuSeq
            , final String code
            , final String field
            , final String message
            , final Long menuRoleSeq
    ) {
        this.menuSeq = menuSeq;
        this.code = code;
        this.field = field;
        this.message = message;
        this.menuRoleSeq = menuRoleSeq;
    }

}
