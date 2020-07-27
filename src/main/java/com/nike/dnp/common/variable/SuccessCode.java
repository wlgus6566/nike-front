package com.nike.dnp.common.variable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * The Class Success enum code.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 1. 오후 5:01:10
 * @Description
 */
@Component
@AllArgsConstructor
public class SuccessCode {

    /**
     * The enum Login success enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ConfigureSuccess {
        SUCCESS
        , SEND_EMAIL_CERT_CODE
        , SEND_EMAIL
        , CHANGE_PASSWORD
        , TERMS_AGREEMENT
        , NOT_DUPLICATE
    }

}
