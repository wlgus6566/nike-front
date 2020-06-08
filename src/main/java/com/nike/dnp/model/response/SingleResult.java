package com.nike.dnp.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * SingleResult
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description SingleResult 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 *
 */

@Getter
@Setter
public class SingleResult<T> extends CommonResult {

    /**
     * @author [오지훈]
     */
    private T data;

}
