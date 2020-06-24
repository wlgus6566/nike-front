package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;

/**
 * EnumCode
 * <p>
 * 이 클래스는 각 서비스별 발생할 수 있는 코드 정의입니다.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 5:26:07
 * @Description 서비스 별 EnumCode
 */
@Component
public class EnumCode {

    /**
     * 정렬 순서
     *
     * @author [이소정]
     */
    public enum SearchEnumCode {

        /**
         * Latest search enum code
         *
         * @author [오지훈]
         */
        LATEST //최신순

        /**
         * Start date search enum code
         *
         * @author [오지훈]
         */
        ,START_DATE //시작일순
    }
}
