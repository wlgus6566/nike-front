package com.nike.dnp.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ListResult
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ListResult 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 *
 */

@Getter
@Setter
public class ListResult<T> extends CommonResult {

    /**
     * @author [오지훈]
     */
    private List<T> list;

}

