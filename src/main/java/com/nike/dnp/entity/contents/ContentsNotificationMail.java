package com.nike.dnp.entity.contents;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * The Class Contents notification mail.
 * 컨텐츠 알림 메일
 *
 * @author [이소정]
 * @since 2020. 12. 22. 오후 9:01:08
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "TB_CONTENTS_NOTIFICATION_MAIL")
public class ContentsNotificationMail extends BaseTimeEntity {

    /**
     * 컨텐츠 알림 메일 시퀀스
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_NOTIFICATION_MAIL_SEQ")
    @ApiModelProperty(name = "contentsNotificationMailSeq", value = "컨텐츠 알림 메일 시퀀스")
    private Long contentsNotificationMailSeq;

    /**
     * 컨텐츠 시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 컨텐츠 URL
     *
     * @author [이소정]
     */
    @Column(name = "CONTENTS_URL")
    @ApiModelProperty(name = "contentsUrl", value = "컨텐츠 URL", required = true, example = "/asset/su/596")
    private String contentsUrl;

    /**
     * 메일 코멘트
     *
     * @author [이소정]
     */
    @Column(name = "MAIL_COMMENT")
    @ApiModelProperty(name = "mailComment", value = "메일 코멘트",example = "SP21 시즌에서는 이 자료를 활용해주세요.")
    private String mailComment;

}
