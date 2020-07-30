package com.nike.dnp.entity.contents;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * The Class Contents basket.
 *
 * @author [이소정]
 * @implNote 콘텐츠 장바구니 entity
 * @since 2020. 7. 14. 오후 5:22:10
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CONTENTS_BASKET")
public class ContentsBasket extends BaseTimeEntity {

    /**
     * 컨텐츠 장바구니 시퀀스
     *
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_BASKET_SEQ")
    @ApiModelProperty(name = "contentsBasketSeq", value ="컨텐츠 장바구니 시퀀스", example = "example")
    private Long contentsBasketSeq;

    /**
     * 유저 시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "USER_SEQ")
    @ApiModelProperty(name = "userSeq", value ="유저 시퀀스", example = "example")
    private Long userSeq;

    /**
     * 컨텐츠 파일 시퀀스
     *
     * @author [이소정]
     */
    @Column(name = "CONTENTS_FILE_SEQ")
    @ApiModelProperty(name = "contentsFileSeq", value ="컨텐츠 파일 시퀀스", example = "10")
    private Long contentsFileSeq;

    /**
     * Save contents basket.
     *
     * @param contentsFileSeq the contents file seq
     * @param authUserDTO     the auth user dto
     * @return the contents basket
     * @author [이소정]
     * @implNote 콘텐츠 장바구니 저장
     * @since 2020. 7. 15. 오후 12:02:37
     */
    @Transactional
    public ContentsBasket save(final Long contentsFileSeq, final AuthUserDTO authUserDTO) {
        final ContentsBasket saveContentsBasket = new ContentsBasket();
        saveContentsBasket.setUserSeq(authUserDTO.getUserSeq());
        saveContentsBasket.setContentsFileSeq(contentsFileSeq);
        return saveContentsBasket;
    }


}
