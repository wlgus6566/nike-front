package com.nike.dnp.entity.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.dto.banner.BannerSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Banner Entity
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:09:56
 * @Description Banner(메인 비주얼) Entity 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_BANNER")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@DynamicUpdate
@DynamicInsert
public class Banner extends BaseTimeEntity implements Serializable {

    /**
     * 배너 시퀀스
     *
     * @author [오지훈]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANNER_SEQ")
    @ApiModelProperty(name = "bannerSeq", value = "배너 시퀀스", hidden = true)
    private Long bannerSeq;

    /**
     * 제목
     *
     * @author [오지훈]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "제목", required = true)
    private String title;

    /**
     * 내용
     *
     * @author [오지훈]
     */
    @Column(name = "CONTENTS")
    @ApiModelProperty(name = "contents", value = "내용", required = true)
    private String contents;

    /**
     * 이미지_파일_명
     *
     * @author [오지훈]
     */
    @Column(name = "IMAGE_FILE_NAME")
    @ApiModelProperty(name = "imageFileName", value = "이미지_파일_명", required = true)
    private String imageFileName;

    /**
     * 이미지_파일_사이즈
     *
     * @author [오지훈]
     */
    @Column(name = "IMAGE_FILE_SIZE")
    @ApiModelProperty(name = "imageFileSize", value = "이미지_파일_사이즈", required = true)
    private String imageFileSize;

    /**
     * 이미지_파일_물리_명
     *
     * @author [오지훈]
     */
    @Column(name = "IMAGE_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지_파일_물리_명", required = true)
    private String imageFilePhysicalName;

    /**
     * 모바일_이미지_파일_명
     *
     * @author [오지훈]
     */
    @Column(name = "MOBILE_IMAGE_FILE_NAME")
    @ApiModelProperty(name = "mobileImageFileName", value = "모바일_이미지_파일_명", required = true)
    private String mobileImageFileName;

    /**
     * 모바일_이미지_파일_사이즈
     *
     * @author [오지훈]
     */
    @Column(name = "MOBILE_IMAGE_FILE_SIZE")
    @ApiModelProperty(name = "mobileImageFileSize", value = "모바일_이미지_파일_사이즈", required = true)
    private String mobileImageFileSize;

    /**
     * 모바일_이미지_파일_물리_명
     *
     * @author [오지훈]
     */
    @Column(name = "MOBILE_IMAGE_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "mobileImageFilePhysicalName", value = "모바일_이미지_파일_물리_명", required = true)
    private String mobileImageFilePhysicalName;

    /**
     * 링크_URL_타입_공통코드
     *
     * @author [오지훈]
     */
    @Column(name = "LINK_URL_TYPE_CODE")
    @ApiModelProperty(name = "linkUrlTypeCode", value = "링크_URL_타입_공통코드")
    private String linkUrlTypeCode;

    /**
     * 링크_URL
     *
     * @author [오지훈]
     */
    @Column(name = "LINK_URL")
    @ApiModelProperty(name = "linkUrl", value = "링크_URL", required = true)
    private String linkUrl;

    public Banner saveOrUpdate (BannerSaveDTO bannerSaveDTO) {
        this.title = bannerSaveDTO.getTitle();
        this.contents = bannerSaveDTO.getContents();
        this.imageFileName = bannerSaveDTO.getImageFileName();
        this.imageFileSize = bannerSaveDTO.getImageFileSize();
        this.imageFilePhysicalName = bannerSaveDTO.getImageFilePhysicalName();
        this.mobileImageFileName = bannerSaveDTO.getMobileImageFileName();
        this.mobileImageFileSize = bannerSaveDTO.getMobileImageFileSize();
        this.mobileImageFilePhysicalName = bannerSaveDTO.getMobileImageFilePhysicalName();
        this.linkUrlTypeCode = bannerSaveDTO.getLinkUrlTypeCode();
        this.linkUrl = bannerSaveDTO.getLinkUrl();
        return this;
    }

    /*public void update (BannerUpdateDTO bannerUpdateDTO) {
        this.title = bannerUpdateDTO.getTitle();
        this.contents = bannerUpdateDTO.getContents();
        this.imageFileName = bannerUpdateDTO.getImageFileName();
        this.imageFileSize = bannerUpdateDTO.getImageFileSize();
        this.imageFilePhysicalName = bannerUpdateDTO.getImageFilePhysicalName();
        this.mobileImageFileName = bannerUpdateDTO.getMobileImageFileName();
        this.mobileImageFileSize = bannerUpdateDTO.getMobileImageFileSize();
        this.mobileImageFilePhysicalName = bannerUpdateDTO.getMobileImageFilePhysicalName();
        this.linkUrlTypeCode = bannerUpdateDTO.getLinkUrlTypeCode();
        this.linkUrl = bannerUpdateDTO.getLinkUrl();
    }*/
}
