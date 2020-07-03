package com.nike.dnp.entity.contents;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Contents Entity
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 19. 오후 5:57:35
 * @Description Contents Entity 작성
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CONTENTS")
public class Contents extends BaseTimeEntity {

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @Column(name = "TOP_MENU_CODE")
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드")
    private String topMenuCode;

    /**
     * 메뉴 공통코드
     * @author [이소정]
     */
    @Column(name = "MENU_CODE")
    @ApiModelProperty(name = "menuCode", value = "메뉴 공통코드", required = true, example = "A_SP")
    private String menuCode;

    /**
     * 이미지 파일명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_NAME")
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일명", required = true)
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_SIZE")
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", required = true)
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @Column(name = "IMAGE_FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", required = true)
    private String imageFilePhysicalName;

    /**
     * 폴더명
     * @author [이소정]
     */
    @Column(name = "FOLDER_NAME")
    @ApiModelProperty(name = "folderName", value = "폴더명", required = true)
    private String folderName;

    /**
     * 폴더 내용
     * @author [이소정]
     */
    @Column(name = "FOLDER_CONTENTS")
    @ApiModelProperty(name = "folderContents", value = "폴더 내용", required = true)
    private String folderContents;

    /**
     * 캠페인 기간 구분 공통코드
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_PERIOD_SECTION_CODE")
    @ApiModelProperty(name = "campaignPeriodSectionCode", value = "캠페인 기간 구분 공통코드", required = true)
    private String campaignPeriodSectionCode;

    /**
     * 캠페인 시작 일시
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_BEGIN_DT")
    @ApiModelProperty(name = "campaignBeginDt", value = "캠페인 시작 일시")
    private String campaignBeginDt;

    /**
     * 캠페인 종료 일시
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_END_DT")
    @ApiModelProperty(name = "campaignEndDt", value = "캠페인 종료 일시")
    private String campaignEndDt;

    /**
     * 메모
     * @author [이소정]
     */
    @Column(name = "MEMO")
    @ApiModelProperty(name = "memo", value = "메모")
    private String memo;

    /**
     * 조회수
     * @author [이소정]
     */
    @Column(name = "READ_COUNT")
    @ApiModelProperty(name = "readCount", value = "조회수")
    private Long readCount;

    /**
     * 노출 여부
     * @author [이소정]
     */
    @Column(name = "EXPOSURE_YN")
    @ApiModelProperty(name = "exposureYn", value = "노출 여부", example = "Y")
    private String exposureYn;

    /**
     * 사용 여부
     * @author [이소정]
     */
    @Column(name = "USE_YN")
    @ApiModelProperty(name = "useYn", value = "사용 여부", example = "Y/N")
    private String useYn;

    /**
     * The Contents files
     * @author [이소정]
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "contents")
    @ApiModelProperty(name = "contentsFiles", value = "콘텐츠 파일 목록", required = true)
    private List<ContentsFile> contentsFiles;

    /**
     * Save contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 1. 오전 9:59:51
     * @Description 등록
     */
    @Transactional
    public Contents save(ContentsSaveDTO contentsSaveDTO) {
        log.info("Contents.save");
        Contents saveContents = new Contents();
        saveContentsBasic(contentsSaveDTO, saveContents);
        // 캠페인기간 > 날짜선택 인 경우
        if (ServiceEnumCode.ContentsCampaignPeriodCode.EVERY.toString().equals(contentsSaveDTO.getCampaignPeriodSectionCode())) {
            saveContents.setCampaignBeginDt(contentsSaveDTO.getCampaignBeginDt());
            saveContents.setCampaignEndDt(contentsSaveDTO.getCampaignEndDt());
        }
        saveContents.setMemo(contentsSaveDTO.getMemo());
        // TODO[lsj] 권한설정 추가 하기

        return saveContents;
    }

    /**
     * Save contents basic.
     *
     * @param contentsSaveDTO the contents save dto
     * @param saveContents    the save contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 1. 오전 9:59:37
     * @Description 기본적인 부분 등록
     */
    public static void saveContentsBasic(ContentsSaveDTO contentsSaveDTO, Contents saveContents) {
        log.info("Contents.saveContentsBasic");
        saveContents.setTopMenuCode(contentsSaveDTO.getTopMenuCode());
        saveContents.setMenuCode(contentsSaveDTO.getMenuCode());
        saveContents.setImageFileName(contentsSaveDTO.getImageFileName());
        saveContents.setImageFileSize(contentsSaveDTO.getImageFileSize());
        saveContents.setImageFilePhysicalName(contentsSaveDTO.getImageFilePhysicalName());
        saveContents.setFolderName(contentsSaveDTO.getFolderName());
        saveContents.setFolderContents(contentsSaveDTO.getFolderContents());
        saveContents.setCampaignPeriodSectionCode(contentsSaveDTO.getCampaignPeriodSectionCode());

        saveContents.setExposureYn("Y");
        saveContents.setUseYn("Y");
        saveContents.setReadCount(0l);
    }

}
