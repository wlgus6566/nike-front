package com.nike.dnp.entity.contents;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsFileSaveDTO;
import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.exception.CodeMessageHandleException;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * The Class Contents file Entity.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 3:56:22
 * @Description
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CONTENTS_FILE")
public class ContentsFile extends BaseTimeEntity {

    /**
     * 컨텐츠 파일 시퀀스
     * @author [이소정]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTS_FILE_SEQ")
    @ApiModelProperty(name = "contentsFileSeq", value = "컨텐츠 파일 시퀀스")

    private Long contentsFileSeq;

    /**
     * 컨텐츠 시퀀스
     * @author [이소정]
     */
    @Column(name = "CONTENTS_SEQ")
    @ApiModelProperty(name = "contentsSeq", value = "컨텐츠 시퀀스")
    private Long contentsSeq;

    /**
     * 파일 구분 공통코드
     * @author [이소정]
     */
    @Column(name = "FILE_SECTION_CODE")
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드", required = true)
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드", required = true)
    private String fileKindCode;

    /**
     * 타이틀
     * @author [이소정]
     */
    @Column(name = "TITLE")
    @ApiModelProperty(name = "title", value = "타이틀")
    private String title;

    /**e
     * url
     * @author [이소정]
     */
    @Column(name = "URL")
    @ApiModelProperty(name = "url", value = "url")
    private String url;

    /**
     * 파일 명
     * @author [이소정]
     */
    @Column(name = "FILE_NAME")
    @ApiModelProperty(name = "fileName", value = "파일 명")
    private String fileName;

    /**
     * 파일 사이즈
     * @author [이소정]
     */
    @Column(name = "FILE_SIZE")
    @ApiModelProperty(name = "fileSize", value = "파일 사이즈")
    private Long fileSize;

    /**
     * 파일 물리 명
     * @author [이소정]
     */
    @Column(name = "FILE_PHYSICAL_NAME")
    @ApiModelProperty(name = "filePhysicalName", value = "파일 물리 명")
    private String filePhysicalName;

    /**
     * 다운로드 수
     * @author [이소정]
     */
    @Column(name = "DOWNLOAD_COUNT")
    @ApiModelProperty(name = "downloadCount", value = "다운로드 수")
    private long downloadCount;


    /**
     * The Contents
     * @author [이소정]
     */
    @ManyToOne
    @JoinColumn(name = "CONTENTS_SEQ", insertable = false, updatable = false)
    @ApiModelProperty(name = "contents", value = "The Contents", hidden = true)
    private Contents contents;

    /**
     * Save contents file.
     *
     * @param savedContents       the saved contents
     * @param contentsFileSaveDTO the contents file save dto
     * @return the contents file
     * @author [이소정]
     * @CreatedOn 2020. 7. 1. 오전 11:24:43
     * @Description
     */
    @Transactional
    public ContentsFile save(Contents savedContents, ContentsFileSaveDTO contentsFileSaveDTO) {
        log.info("ContentsFile.save");
        ContentsFile contentsFile = new ContentsFile();

        contentsFile.setDownloadCount(0l);
        contentsFile.setContentsSeq(savedContents.getContentsSeq());
        contentsFile.setFileSectionCode(contentsFileSaveDTO.getFileSectionCode());
        contentsFile.setFileKindCode(contentsFileSaveDTO.getFileKindCode());
        String fileKindCode = contentsFileSaveDTO.getFileKindCode();

        if (ServiceEnumCode.ContentsFileKindCode.FILE.equals(fileKindCode)) {
            this.checkStringValidation(contentsFileSaveDTO.getFileName(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_NAME.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_NAME.getMessage());

            contentsFile.setFileName(contentsFileSaveDTO.getFileName());
            contentsFile.setFileSize(contentsFileSaveDTO.getFileSize());
            contentsFile.setFilePhysicalName(contentsFileSaveDTO.getFilePhysicalName());
        } else {
            this.checkStringValidation(contentsFileSaveDTO.getTitle(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_TITLE.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_TITLE.getMessage());
            this.checkStringValidation(contentsFileSaveDTO.getUrl(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_URL.toString(), ErrorEnumCode.ContentsError.NOT_EXIST_FILE_URL.getMessage());

            contentsFile.setTitle(contentsFileSaveDTO.getTitle());
            contentsFile.setUrl(contentsFileSaveDTO.getUrl());
        }

        return contentsFile;
    }

    /**
     * Check string validation boolean.
     *
     * @param value        the value
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @return the boolean
     * @author [이소정]
     * @CreatedOn 2020. 6. 26. 오후 5:30:51
     * @Description
     */
    public Boolean checkStringValidation(String value, String errorCode, String errorMessage) {
        if (value.isEmpty() || value.trim().isEmpty()) {
            throw new CodeMessageHandleException(errorCode, errorMessage);
        }
        return true;
    }

}
