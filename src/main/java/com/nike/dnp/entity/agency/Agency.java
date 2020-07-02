package com.nike.dnp.entity.agency;

import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Agency.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 26. 오후 4:16:34
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_AGENCY")
public class Agency extends BaseTimeEntity {

    /**
     * The Agency seq
     *
     * @author [윤태호]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AGENCY_SEQ")
    @ApiModelProperty(name = "agencySeq", value = "에이전시 시퀀스")
    private long agencySeq;


    /**
     * The Agency name
     *
     * @author [윤태호]
     */
    @Column(name = "AGENCY_NAME")
    @ApiModelProperty(name = "agencyName", value = "에이젼시 이름")
    private String agencyName;

    /**
     * The Agency description
     *
     * @author [윤태호]
     */
    @Column(name = "AGENCY_DESCRIPTION")
    @ApiModelProperty(name = "agencyDescription", value = "에이젼시 설명 ")
    private String agencyDescription;

    /**
     * The Telephone number
     *
     * @author [윤태호]
     */
    @Column(name = "TELEPHONE_NUMBER")
    @ApiModelProperty(name = "telephoneNumber", value = "전화번호")
    private String telephoneNumber;

    /**
     * The Email
     *
     * @author [윤태호]
     */
    @Column(name = "EMAIL")
    @ApiModelProperty(name = "email", value = "이메일")
    private String email;


}
