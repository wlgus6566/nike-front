package com.nike.dnp.dto.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nike.dnp.util.CloudFrontUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The Class Notice list dto.
 *
 * @author [정주희]
 * @since 2020. 7. 13. 오후 7:11:50
 * @implNote Customer Center 목록 반환 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Component
public class CustomerResultDTO {

    /**
     * The Notice article seq
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleSeq", value = "게시글 시퀀스")
    private Long noticeArticleSeq;

    /**
     * The Notice article section code
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleSectionCode", value = "게시물 구분 코드", required = true)
    private String noticeArticleSectionCode;

    /**
     * The Notice article category code
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleCategoryCode", value = "[QNA] 게시물 카테고리 코드 (상위 코드 : NOTICE_CATEGORY_CODE)")
    private String noticeArticleCategoryCode;

    /**
     * The Notice article category value
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeArticleCategoryCode", value = "[QNA] 게시물 카테고리 코드 값", hidden = true)
    private String noticeArticleCategoryValue;

    /**
     * The Notice yn
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "noticeYn", value = "[공지사항] 상단 고정 여부")
    private String noticeYn;

    /**
     * The Thumbnail file name
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "thumbnailFileName", value = "[NEWS] 썸네일 파일 명")
    private String thumbnailFileName;

    /**
     * The Thumbnail file size
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "thumbnailFileSize", value = "[NEWS] 썸네일 파일 사이즈")
    private String thumbnailFileSize;

    /**
     * The Thumbnail file physical name
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "thumbnailFilePhysicalName", value = "[NEWS] 썸네일 파일 물리 명")
    private String thumbnailFilePhysicalName;

    /**
     * The Title
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "title", value = "제목/질문")
    private String title;

    /**
     * The Contents
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "contents", value = "내용")
    private String contents;

    /**
     * The Use yn
     *
     * @author [정주희]
     */
    @ApiModelProperty(name = "useYn", value = "게시글 사용 여부")
    private String useYn;

    /**
     * The nickname
     *
     * @author [오지훈]
     */
    @ApiModelProperty(name = "nickname", value = "최초 작성자")
    private String nickname;

    @ApiModelProperty(name = "registerSeq", value = "최초 작성자 시퀀스")
    private Long registerSeq;

    /**
     * 최종 수정일
     *
     * @author [오지훈]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @ApiModelProperty(name = "updateDt", value = "최종 수정일")
    private LocalDateTime updateDt;

    /*@ApiModelProperty(name = "cdnUrl", value = "cdnUrl", hidden = true)
    private static String cdnUrl;

    @Value("${nike.file.cdnUrl:}")
    public void setCdnUrl(final String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }
*/
    public String getThumbnailFilePhysicalName() {
        return StringUtils.isEmpty(thumbnailFilePhysicalName) ? null : CloudFrontUtil.getCustomSignedUrl(thumbnailFilePhysicalName);
    }
}
