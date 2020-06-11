package com.nike.dnp.entity.example;

import com.nike.dnp.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * ManagerAuth Entity
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description ManagerAuth Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER_AUTH")
public class ManagerAuth extends BaseTimeEntity {

    /**
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_SEQ")
    private Long authSeq;

    /**
     * @author [오지훈]
     */
    @Column(name = "AUTH_NAME")
    private String authName;

    /**
     * @author [오지훈]
     */
    @Column(name = "ROLE_TYPE")
    private String roleType;

    /**
     * @author [오지훈]
     */
    @Column(name = "USE_YN")
    private String useYn;

    /**
     * Instantiates a new Manager auth.
     *
     * @param authName the auth name
     * @param roleType the role type
     * @param useYn    the use yn
     */
    @Builder
    public ManagerAuth(
            final String authName
            , final String roleType
            , final String useYn
    ) {
        super();
        this.authName = authName;
        this.roleType = roleType;
        this.useYn = useYn;
    }

    /**
     * Update.
     *
     * @param authName the auth name
     * @param roleType the role type
     * @param useYn    the use yn
     */
    public void update(
            final String authName
            , final String roleType
            , final String useYn
    ) {
        this.authName = authName;
        this.roleType = roleType;
        this.useYn = useYn;
    }
}
