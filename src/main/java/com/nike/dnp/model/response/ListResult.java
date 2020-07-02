package com.nike.dnp.model.response;

import lombok.*;

import java.util.List;

/**
 * ListResult
 *
 * @param <T> the type parameter
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:11:39
 * @Description ListResult 작성
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ListResult<T> extends CommonResult {

    /**
     * List<T>
     *
     * @author [오지훈]
     */
    private List<T> list;

}

