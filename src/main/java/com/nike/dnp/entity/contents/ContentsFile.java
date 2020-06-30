package com.nike.dnp.entity.contents;

import com.nike.dnp.entity.BaseTimeEntity;
import com.nike.dnp.entity.menu.MenuRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * The Class Contents file Entity.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 3:56:22
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_CONTENTS_FILE")
//@Embeddable // 연관관계 있는거 자동으로 삭제 해줌
//사요하는 곳에서 @Embeddable  private ContentsFile contentFile; 로 사용하면 됨
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
    @ApiModelProperty(name = "fileSectionCode", value = "파일 구분 공통코드", required = true, example = "")
    private String fileSectionCode;

    /**
     * 파일 종류 공통코드
     * @author [이소정]
     */
    @Column(name = "FILE_KIND_CODE")
    @ApiModelProperty(name = "fileKindCode", value = "파일 종류 공통코드", required = true, example = "")
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
    private int downloadCount;


    /**
     * The Contents
     * @author [이소정]
     */
    @ManyToOne
    @JoinColumn(name = "CONTENTS_SEQ", insertable = false, updatable = false)
    private Contents contents;

//    /**
//     * Update.
//     *
//     * @param managerName the manager name
//     * @param password    the password
//     * @param managerAuth the manager auth
//     */
//    public void update(
//            final String managerName
//            , final String password
//            , final ManagerAuth managerAuth
//    ) {
//        this.managerName = managerName;
//        this.password = password;
//        this.managerAuth = managerAuth;
//    }


}
