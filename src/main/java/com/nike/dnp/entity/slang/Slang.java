package com.nike.dnp.entity.slang;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Slang.
 *
 * @author [오지훈]
 * @since 2020. 7. 2. 오전 11:36:36
 * @implNote 금칙어 Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_SLANG")
public class Slang {

    /**
     * 금칙어 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLANG_SEQ")
    @ApiModelProperty(name = "slangSeq", value = "금칙어 시퀀스", hidden = true)
    private Long slangSeq;

    /**
     * 금칙어
     *
     * @author [오지훈]
     */
    @Column(name = "SLANG")
    @ApiModelProperty(name = "slang", value = "금칙어", required = true)
    private String slang;

}
