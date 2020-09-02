package com.nike.dnp.common.variable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * The Class Service enum code.
 *
 * @author [오지훈]
 * @since 2020. 7. 3. 오전 11:34:38
 * @implNote
 */
@Component
@AllArgsConstructor
public class ServiceCode {

    /**
     * Y or N
     */
    public enum YesOrNoEnumCode {
        Y,N
    }

    /**
     * 정렬 순서
     *
     * @author [이소정]
     */
    @RequiredArgsConstructor
    @Getter
    public enum SearchEnumCode {
        LATEST("updateDt") //최신순
        , START_DATE("campaignBeginDt"); //시작일순

        /**
         * 코드값
         *
         * @author [이소정]
         */
        private final String value;
    }

    /**
     * The enum Contents file search code.
     *
     * @author [이소정]
     */
    @RequiredArgsConstructor
    @Getter
    public enum ContentsFileSearchCode {
        ORDER("fileOrder") // 정렬번호 순
        , FILE_NAME("fileName"); // 파일명

        /**
         * 코드값
         *
         * @author [이소정]
         */
        private final String value;
    }

    /**
     * 컨텐츠 > 캠페인기간 enumCode
     */
    public enum ContentsCampaignPeriodCode {
        SELECT  //날짜선택
        , EVERY  //365
    }

    /**
     * 컨텐츠 > 파일 > 파일구분
     */
    public enum ContentsFileSectionCode {
        ASSET
        , GUIDE
        , VIDEO
    }

    /**
     * 컨텐츠 > 파일 > 파일종류
     */
    public enum ContentsFileKindCode {
        FILE  // 파일
        , VIDEO  // 동영상(URL)
        , VR  // VR
    }

    /**
     * The enum Contents top menu code.
     */
    public enum ContentsTopMenuCode {
        ASSET
        , TOOLKIT
        , FOUNDATION
    }

    /**
     * The enum Contents menu code.
     */
    public enum ContentsMenuCode {
        ALL
    }

    /**
     * Asset 메뉴 코드
     */
    public enum AssetMenuCode {
        SP // Asset > SP
        , SU // Asset > SU
        , FA // Asset > FA
        , HO // Asset > HO
    }

    /**
     * Toolkit 메뉴 코드
     */
    public enum ToolkitMenuCode {
        VMS // TOOLKIT > VMS
        , EKIN // TOOLKIT > EKIN
        , SOCIAL // TOOLKIT > SOCIAL
        , RB // TOOLKIT > RB
    }

    /**
     * Foundation 메뉴 코드
     */
    public enum FoundationMenuCode {
        VMS // FOUNDATION > VMS
        , EKIN // FOUNDATION > EKIN
        , DIGITAL // FOUNDATION > DIGITAL
        , RB // FOUNDATION > RB
    }

    /**
     * The enum User status enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum UserStatusEnumCode {

        NORMAL("정상"),
        DORMANT("휴면"),
        DELETE("탈퇴");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Email type.
     */
    @RequiredArgsConstructor
    @Getter
    public enum EmailTypeEnumCode {
        USER_CREATE("[NIKE SPACE] 계정 생성 안내"),
        PASSWORD_SETTING("[NIKE SPACE] 비밀번호 설정"),
        PASSWORD_GUIDE("[NIKE SPACE] 비밀번호 변경 안내"),
        CERT_CODE_SEND("[NIKE SPACE] E-MAIL 인증 안내"),
        DORMANT_PREV("[NIKE SPACE] 휴면계정 전환 사전 안내"),
        DORMANT_ACTIVE("[NIKE SPACE] 휴면계정 처리 안내"),
        DORMANT_CHANGE("[NIKE SPACE] 휴면계정 해제 안내"),
        CONTENTS_UPDATE("[NIKE SPACE] 컨텐츠 업데이트 알림"),
        ORDER("[NIKE SPACE] 주문서");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Return type enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ReturnTypeEnumCode {
        CREATE("등록되었습니다.")
        ,UPDATE("수정되었습니다.")
        ,DELETE("삭제되었습니다.")
        ,CANCEL("취소되었습니다.")
        ;
        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Product category 2 enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ProductCategory2EnumCode {
        SUBSIDIARY("부자재"),
        NIKE_BY_YOU("NIKE BY YOU"),
        CUSTOM23("CUSTOM23"),
        MNQ("MNQ");

        /**
         * 코드값
         *
         * @author [윤태호]
         */
        private final String message;
    }

    /**
     * The enum Product category 3 enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ProductCategory3EnumCode {
        SUBSIDIARY21("운영 비품"),
        SUBSIDIARY22("스태프 비품"),
        SUBSIDIARY23("운영 사이니지"),
        SUBSIDIARY24("세일 사이니지"),
        SUBSIDIARY25("오픈 패키지"),
        SUBSIDIARY26("나이키 골프"),
        NIKE_BY_YOU27("신발 커스텀 (단품)"),
        NIKE_BY_YOU28("신발 커스텀 (패키지)"),
        NIKE_BY_YOU29("의류 커스텀 (단품)"),
        NIKE_BY_YOU30("의류 커스텀 (패키지)"),
        NIKE_BY_YOU31("OTHERS"),
        CUSTOM2332("신발 커스텀 (단품)"),
        CUSTOM2333("신발 커스텀 (패키지)"),
        CUSTOM2334("의류 커스텀 (단품)"),
        CUSTOM2335("의류 커스텀 (패키지)"),
        CUSTOM2336("OTHERS"),
        MNQ37("남성"),
        MNQ38("여성"),
        MNQ39("유아동"),
        MNQ40("수리/보수");

        /**
         * 코드값
         *
         * @author [윤태호]
         */
        private final String message;
    }

    /**
     * The enum Menu skill enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum MenuSkillEnumCode {
        LIST("목록", "listAuthYn", 0)
        ,CREATE("등록/수정", "creationAuthYn", 1)
        ,DELETE("삭제", "deleteAuthYn", 2)
        ,VIEW("상세보기", "detailAuthYn", 3)
        ,DOWNLOAD("다운로드/주문", "downloadAuthYn", 4)
        ,REPORT("시공보고서", "reportAuthYn", 5)
        ;
        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
        /**
         * The Field
         *
         * @author [오지훈]
         */
        private final String field;
        /**
         * The Sort
         *
         * @author [오지훈]
         */
        private final int sort;
    }

    /**
     * The enum File folder enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum FileFolderEnumCode {
        PRODUCT("product"),
        CONTENTS("contents"),
        REPORT("report"),
        TEMP("temp"),
        NEWS("NEWS"),
        BANNER("banner")
        ;

        /**
         * 코드값
         *
         * @author [윤태호]
         */
        private final String folder;

    }

    /**
     * The enum History tab enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum HistoryTabEnumCode {
        ALL
        , ASSET
        , TOOLKIT
        , FOUNDATION
        , REPORT_MANAGE
    }

    /**
     * The enum Menu code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum MenuCode {
        REPORT_UPLOAD
        , REPORT_MANAGE
    }


    /**
     * The enum File extension enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum FileExtensionEnumCode {
        AI(".ai")
        , JPG(".jpg")
        , JPEG(".jpeg")
        , GIF(".gif")
        , TIF(".tif")
        , PSD(".psd")
        , BMP(".bmp")
        , PNG(".png")
        , PDF(".pdf")
        , PPT(".ppt")
        , PPTX(".pptx")
        , DOC(".doc")
        , DOCX(".docx")
        , XLS(".xls")
        , XLSX(".xlsx")
        , KEY(".key")
        , MP4(".mp4")
        , AVI(".avi")
        , MOV(".mov")
        , TTF(".TTF")
        , OTF(".OTF")
        , INDD(".INDD");

        /**
         * 확장자값
         *
         * @author [이소정]
         */
        private final String fileExtension;
    }

    /**
     * The enum Alarm action enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum AlarmActionEnumCode {
        NEW
        , UPDATE
        , FEEDBACK
    }

    /**
     * The enum Faq enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum QNAEnumCode {
        ASSET("ASSET/TOOLKIT/FOUNDATION"),
        SUBSIDIARY("부자재주문"),
        REPORT("시공보고서"),
        INFO("INFORMATION"),
        USED("사이트이용"),
        ETC("기타");

        /**
         * The Message
         *
         * @author [정주희]
         */
        private final String message;
    }

    /**
     * The enum Notice article section enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum NoticeArticleSectionEnumCode {
        NOTICE
        , NEWS
        , QNA
    }
}
