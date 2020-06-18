package com.nike.dnp.model.response;

import lombok.*;

/**
 * SingleResult
 *
 * @param <T> the type parameter
 * @author [오지훈]
 * @Description SingleResult 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SingleResult<T> extends CommonResult {

    /**
     * T
     * @author [오지훈]
     */
    private T data;

}
