package com.nike.dnp.common.variable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The Class Error enum code.
 *
 * @author [이소정]
 * @since 2020. 7. 1. 오후 5:01:17
 * @implNote
 */
@Component
@AllArgsConstructor
public class FailCode {

    /**
     * The enum Exception error.
     *
     * @implNote 공통 오류 처리
     */
    @RequiredArgsConstructor
    @Getter
    public enum ExceptionError {
        ERROR, NOT_FOUND, INVALID
    }

    /**
     * The enum Configure error.
     *
     * @implNote Validation 이외의 오류 처리
     */
    @RequiredArgsConstructor
    @Getter
    public enum ConfigureError {
        NO_AUTH
        , FAIL_DELETE
        , NULL_PASSWORD
        , CHECK_ID_PASSWORD
        , OVERTIME_PASSWORD
        , EXPIRED_PERIOD
        , INVALID_PASSWORD
        , DUPLICATE_ID_PASSWORD
        , USED_PASSWORD
        , NOT_MATCH_PASSWORD
        , EXPIRED_CERT_CODE
        , NOT_MATCH_CERT_CODE
        , IS_SLANG
        , IS_DORMANT
        , NOT_SESSION
        , USED_ID
        , NOT_VALID_EMAIL
        , RETRY_CONFIRM_EMAIL
        , DUPLICATE_GOODS
        , INVALID_ORDER
        , INVALID_FILE
        , SELECT_CAMPAIGN_BEGIN_DT
        , SELECT_CAMPAIGN_END_DT
        , NULL_TITLE
        , NULL_URL
        , SELECT_FILE
        , EXCEED_MAX_NOTICE
        , NOT_FOUND_PRODUCT
        , MINIMUM_ORDER_QUANTITY
        , MAX_INSERT_CALENDAR
    }

}
