package com.nike.dnp.entity.user;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserContents Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:29
 * @Description UserContents(컨텐츠 권한) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_CONTENTS")
public class UserContents extends BaseTimeEntity implements Serializable {

    /**
     * 컨텐츠 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_AUTH_SEQ")
    @ApiModelProperty(name = "contentsAuthSeq", value = "컨텐츠 권한 시퀀스", hidden = true)
    private Long contentsAuthSeq;

    /**
     * 권한 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "AUTH_SEQ")
    @ApiModelProperty(name = "authSeq", value = "권한 시퀀스", hidden = true)
    private Long authSeq;

    /**
     * 컨텐츠 시퀀스
     *
     * @author [오지훈]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsAuthSeq", value = "컨텐츠 시퀀스", hidden = true)
    private Long contentsSeq;

    /**
     * 상세_권한_여부
     *
     * @author [오지훈]
     */
    @Column(name = "DETAIL_AUTH_YN")
    @ApiModelProperty(name = "detailAuthYn", value = "상세_권한_여부")
    private String detailAuthYn;

    /**
     * 메일_수신_여부
     *
     * @author [오지훈]
     */
    @Column(name = "EMAIL_RECEPTION_YN")
    @ApiModelProperty(name = "emailReceptionYn", value = "메일_수신_여부")
    private String emailReceptionYn;

    /**
     * Save user contents.
     *
     * @param contentSeq       the content seq
     * @param authSeq          the auth seq
     * @param detailAuthYn     the detail auth yn
     * @param emailReceptionYn the email reception yn
     * @return the user contents
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 12:08:11
     * @Description 저장
     */
    public UserContents save (
            final Long contentSeq
            , final Long authSeq
            , final String detailAuthYn
            , final String emailReceptionYn
    ) {
        this.authSeq = authSeq;
        this.contentsSeq = contentSeq;
        this.detailAuthYn = detailAuthYn;
        this.emailReceptionYn = emailReceptionYn;
        return this;
    }

}
