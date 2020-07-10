package com.nike.dnp.common.variable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The Class Error enum code.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 1. 오후 5:01:17
 * @Description
 */
@Component
public class ErrorEnumCode {

    @RequiredArgsConstructor
    @Getter
    public enum ExceptionError {
        ERROR("서버에서 오류가 발생하였습니다.");

        /**
         * The Value
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Auth success enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum AuthError {
        NO_AUTH("접근 권한이 없습니다."),
        FAIL_DELETE("해당 권한 그룹에 속한 계정이 있어 삭제가 불가능합니다. 권한 변경 후 삭제해 주세요");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }


    /**
     * The enum Login error.
     *
     * @author [이소정]
     */
    @RequiredArgsConstructor
    @Getter
    public enum LoginError {
        NOT_JOIN("가입여부를 확인해주세요.\n등록된 정보가 없습니다."),
        CHECK_EMAIL("이메일을 정확히 입력해주세요."),
        DUPLICATE_EMAIL("이미 사용중인 이메일 입니다."),
        NULL_PASSWORD("비밀번호를 입력해주세요."),
        WRONG_PASSWORD("비밀번호가 틀렸습니다."),
        CHECK_ID_PASSWORD("아이디 또는 비밀번호를 확인 후 다시 입력해 주세요"),
        OVERTIME_PASSWORD("비밀번호를 변경하신지 90일이 지났습니다. 비밀번호를 변경해 주세요"),
        EXPIRED_PERIOD("유효기간이 만료된 url 입니다"),
        INVALID_PASSWORD("(8~16자/대소문자/숫자/특수문자 포함)의 비밀번호를 입력해 주세요"),
        DUPLICATE_ID_PASSWORD("비밀번호는 ID와 중복됩니다. 다시 입력해 주세요."),
        USED_PASSWORD("전에 사용하지 않은 비밀번호로 변경해 주세요"),
        NOT_MATCH_PASSWORD("입력하신 비밀번호가 일치하지 않습니다."),
        EXPIRED_CERT_CODE("인증코드 유효기간이 만료되었습니다. 다시 인증해 주세요"),
        NOT_MATCH_CERT_CODE("인증코드를 확인해 주세요.(인증코드는 대소문자를 구별합니다.)"),
        IS_SLANG("간단한 패턴이나 공통 사전 단어를 포함한 비밀번호를 설정할 수 없습니다. 확인 후 다시 설정해 주세요."),
        IS_DORMANT("휴면회원 입니다.")
        ;

        /**
         * 에러 메시지
         *
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Contents error.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ContentsError {
        NOT_EXIST_FILE_NAME("파일명이 존재하지 않습니다."),
        NOT_EXIST_FILE_TITLE("파일타이틀이 존재하지 않습니다."),
        NOT_EXIST_FILE_URL("파일URL이 존재하지 않습니다."),
        NOT_SELECT_DATE("시작/끝날짜를 둘 다 지정해주시기 바랍니다."),
        START_DATE_BIGGER("시작날짜가 종료일자보다 큽니다.");

        /**
         * 에러 메시지
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Manage error.
     *
     * @author [이소정]
     */
    @RequiredArgsConstructor
    @Getter
    public enum UserError {
        NOT_FOUND("해당 회원의 상세정보가 존재하지 않습니다.")
        ,USE_ID("이미 사용중인 ID입니다.")
        ,NOT_VALID_EMAIL("올바른 Email 주소를 입력해 주세요.")
        ,RETRY_CONFIRM_EMAIL("E-MAIL을 다시 확인 후 입력해 주세요.")
        ;

        /**
         * 에러 메시지
         *
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Manager error enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ManagerError {
        NO_SELECT_ACCOUNT("하나 이상의 계정을 선택해 주세요"),
        START_DATE_BIGGER("시작날짜가 종료일자보다 큽니다");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Data error.
     */
    @RequiredArgsConstructor
    @Getter
    public enum DataError {

        /**
         * Not found data error
         *
         * @author [오지훈]
         */
        NOT_FOUND("데이터가 존재하지 않습니다.");

        /**
         * 메시지
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Order error enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum OrderError {
        NO_PRODUCT_SELECT("하나 이상의 상품을 선택해 주세요."),
        ONLY_NUMBER("숫자만 입력 가능합니다"),
        CHECK_MINIMUM_QUANTITY("최소주문수량보다 적게 주문이 불가합니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Report error enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ReportError {
        MAXIMUM_NUMBER_EXCEEDED("이미지는 최대 10개까지 등록 가능합니다. 다시 등록해 주세요."),
        DUPLICATE_FILE("이미 담긴 파일 입니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Mypage error enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum MypageError {
        START_DATE_BIGGER("시작날짜가 종료일자보다 큽니다"),
        NOT_CHANGE_PERIOD("비밀번호 변경 가능한 기간이 아닙니다"),
        MAXIMUM_NUMBER_EXCEEDED("상단 고정은 최대 3개까지만 설정가능합니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum Wish list error.
     */
    @RequiredArgsConstructor
    @Getter
    public enum WishListError {
        DUPLICATE_GOODS("이미 담긴 상품 입니다."),
        NOT_FOUND_WISHLIST("해당 상품은 위시리스트에 없습니다.");

        /**
         * The Value
         *
         * @author [윤태호]
         */
        private final String message;
    }

    @RequiredArgsConstructor
    @Getter
    public enum BasketError {
        NOT_FOUND_BASKET("해당 상품은 장바구니에 없습니다.");

        /**
         * The Value
         *
         * @author [윤태호]
         */
        private final String message;
    }

    @RequiredArgsConstructor
    @Getter
    public enum FileError {
        NOT_EXIST_FILE("파일이 존재하지 않습니다."),
        NOT_IMAGE_FILE("이미지 파일이 아닙니다."),
        FILE_READ_ERROR("파일 읽기 중 오류 발생"),
        FILE_WRITE_ERROR("파일 쓰기 중 오류 발생"),
        FILE_COPY_ERROR("파일 저장 중 오류 발생");

        /**
         * The Value
         *
         * @author [윤태호]
         */
        private final String message;
    }

}
