package com.nike.dnp.entity.contents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsSaveDTO;
import com.nike.dnp.dto.contents.ContentsUpdateDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.util.LocalDateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @ApiModelProperty(name = "menuCode", value = "메뉴 공통코드", required = true, example = "SP")
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
    @ApiModelProperty(name = "campaignPeriodSectionCode", value = "캠페인 기간 구분 공통코드(날짜선택:SELECT/365:EVERY)", required = true)
    private String campaignPeriodSectionCode;

    /**
     * 캠페인 시작 일시
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_BEGIN_DT")
    @ApiModelProperty(name = "campaignBeginDt", value = "캠페인 시작 일시")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime campaignBeginDt;

    /**
     * 캠페인 종료 일시
     * @author [이소정]
     */
    @Column(name = "CAMPAIGN_END_DT")
    @ApiModelProperty(name = "campaignEndDt", value = "캠페인 종료 일시")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime campaignEndDt;

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
    @ApiModelProperty(name = "contentsFileList", value = "컨텐츠 파일 목록", required = true)
    private List<ContentsFile> contentsFileList;

    /**
     * Save contents.
     *
     * @param contentsSaveDTO the contents save dto
     * @return the contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 1. 오전 9:59:51
     * @Description 등록
     */
    public Contents save(final ContentsSaveDTO contentsSaveDTO) {
        log.info("Contents.save");
        final Contents saveContents = new Contents();
        saveContentsBasic(contentsSaveDTO, saveContents);
        // 캠페인기간 > 날짜선택 인 경우
        if (ServiceEnumCode.ContentsCampaignPeriodCode.SELECT.toString().equals(contentsSaveDTO.getCampaignPeriodSectionCode())) {
            saveContents.setCampaignBeginDt(LocalDateUtil.strToLocalDateTime(contentsSaveDTO.getCampaignBeginDt()+" 00:00:00","yyyy.MM.dd HH:mm:ss"));
            saveContents.setCampaignEndDt(LocalDateUtil.strToLocalDateTime(contentsSaveDTO.getCampaignEndDt()+" 23:59:59","yyyy.MM.dd HH:mm:ss"));
        } else {
            saveContents.setCampaignBeginDt(null);
            saveContents.setCampaignEndDt(null);
        }
        saveContents.setMemo(contentsSaveDTO.getMemo());

        return saveContents;
    }

    /**
     * Save contents basic.
     *
     * @param contentsSaveDTO the contents save dto
     * @param saveContents    the save contents
     * @author [이소정]
     * @CreatedOn 2020. 7. 1. \오전 9:59:37
     * @Description 기본적인 부분 등록
     */
    public static void saveContentsBasic(final ContentsSaveDTO contentsSaveDTO, final Contents saveContents) {
        log.info("Contents.saveContentsBasic");
        saveContents.setTopMenuCode(contentsSaveDTO.getTopMenuCode());
        saveContents.setMenuCode(contentsSaveDTO.getMenuCode());
        saveContents.setImageFileName(contentsSaveDTO.getImageFileName());
        saveContents.setImageFileSize(contentsSaveDTO.getImageFileSize());
        saveContents.setImageFilePhysicalName(contentsSaveDTO.getImageFilePhysicalName());
        saveContents.setFolderName(contentsSaveDTO.getFolderName());
        saveContents.setFolderContents(contentsSaveDTO.getFolderContents());
        saveContents.setCampaignPeriodSectionCode(contentsSaveDTO.getCampaignPeriodSectionCode());
        saveContents.setExposureYn(contentsSaveDTO.getExposureYn());

        saveContents.setUseYn("Y");
        saveContents.setReadCount(0l);
    }


    /**
     * Update.
     *
     * @param contentsUpdateDTO the contents update dto
     * @author [이소정]
     * @CreatedOn 2020. 7. 3. 오후 4:01:36
     * @Description
     */
    public void update(final ContentsUpdateDTO contentsUpdateDTO) {
        log.info("Contents.update");
        this.menuCode = contentsUpdateDTO.getMenuCode();
        this.imageFileName = contentsUpdateDTO.getImageFileName();
        this.imageFileSize = contentsUpdateDTO.getImageFileSize();
        this.imageFilePhysicalName = contentsUpdateDTO.getImageFilePhysicalName();
        this.folderName = contentsUpdateDTO.getFolderName();
        this.folderContents = contentsUpdateDTO.getFolderContents();
        this.campaignPeriodSectionCode = contentsUpdateDTO.getCampaignPeriodSectionCode();
        this.exposureYn = contentsUpdateDTO.getExposureYn();
    }

    /**
     * Update read count.
     *
     * @param readCount the read count
     * @author [이소정]
     * @CreatedOn 2020. 7. 3. 오후 5:22:59
     * @Description
     */
    public void updateReadCount(final Long readCount) {
        log.info("Contents.updateReadCount");
        this.readCount = readCount + 1;
    }


    /**
     * Delete.
     *
     * @author [이소정]
     * @CreatedOn 2020. 7. 7. 오후 2:06:34
     * @Description
     */
    public void delete() {
        this.useYn = "N";
    }
}
