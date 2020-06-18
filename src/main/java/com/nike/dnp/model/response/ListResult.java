package com.nike.dnp.model.response;

import lombok.*;

import java.util.List;

/**
 * ListResult
 *
 * @param <T> the type parameter
 * @author [오지훈]
 * @Description ListResult 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ListResult<T> extends CommonResult {

    /**
     * List<T>
     * @author [오지훈]
     */
    private List<T> list;

}

