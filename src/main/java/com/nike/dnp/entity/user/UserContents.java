package com.nike.dnp.entity.user;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.contents.Contents;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * UserContents Entity
 *
 * @author [오지훈]
 * @Description UserContents(컨텐츠 권한) Entity 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
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
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_AUTH_SEQ")
    @ApiModelProperty(name = "contentsAuthSeq", value = "컨텐츠 권한 시퀀스", hidden = true)
    private Long contentsAuthSeq;

    /**
     * 권한
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "AUTH_SEQ")
    private Auth auth;

    /**
     * 컨텐츠
     * @author [오지훈]
     */
    @ManyToOne
    @JoinColumn(name = "CONTENTS_SEQ")
    private Contents contents;

}
