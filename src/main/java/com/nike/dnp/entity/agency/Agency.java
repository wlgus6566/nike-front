package com.nike.dnp.entity.agency;

import com.nike.dnp.dto.agency.AgencySaveDTO;
import com.nike.dnp.dto.agency.AgencyUpdateDTO;
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

    /**
     * 사용 여부
     *
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부")
    private String useYn;

    /**
     * Save agency.
     *
     * @param agencySaveDTO the agency save dto
     * @return the agency
     * @author [이소정]
     * @CreatedOn 2020. 7. 20. 오후 12:15:31
     * @Description
     */
    public Agency save(final AgencySaveDTO agencySaveDTO) {
        final Agency agency = new Agency();
        agency.setAgencyName(agencySaveDTO.getAgencyName());
        agency.setAgencyDescription(agencySaveDTO.getAgencyDescription());
        agency.setTelephoneNumber(agencySaveDTO.getTelephoneNumber());
        agency.setEmail(agencySaveDTO.getEmail());
        agency.setUseYn("Y");
        return agency;
    }

    /**
     * Update.
     *
     * @param agencyUpdateDTO the agency update dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 20. 오후 12:19:53
     * @Description
     */
    public void update(final AgencyUpdateDTO agencyUpdateDTO) {
        this.agencyName = agencyUpdateDTO.getAgencyName();
        this.agencyDescription = agencyUpdateDTO.getAgencyDescription();
        this.telephoneNumber = agencyUpdateDTO.getTelephoneNumber();
        this.email = agencyUpdateDTO.getEmail();
    }


    /**
     * Update use yn.
     *
     * @param useYn the use yn
     * @author [이소정]
     * @CreatedOn 2020. 7. 20. 오후 2:20:00
     * @Description
     */
    public void updateUseYn(final String useYn ) {
        this.useYn = useYn;
    }

}
