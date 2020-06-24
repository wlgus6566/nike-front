package com.nike.dnp.model.response;

import lombok.*;

/**
 * SingleResult
 *
 * @param <T> the type parameter
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:45
 * @Description SingleResult 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SingleResult<T> extends CommonResult {

    /**
     * T
     *
     * @author [오지훈]
     */
    private T data;

}
