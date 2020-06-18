package com.nike.dnp.service;

import com.nike.dnp.common.variable.CommonResponse;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ResponseService
 *
 * @author [오지훈]
 * @Description 공통 response 서비스 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Service
@RequiredArgsConstructor
public class ResponseService {

    /**
     * CommonResponse FAIL
     * @author [오지훈]
     */
    private static final CommonResponse FAIL = CommonResponse.FAIL;

    /**
     * CommonResponse SUCCESS
     * @author [오지훈]
     */
    private static final CommonResponse SUCCESS = CommonResponse.SUCCESS;

    /**
     * Gets single result.
     * 단일건 결과를 처리하는 메소드
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the single result
     * @author [오지훈]
     */
    public <T> SingleResult<T> getSingleResult(final T data) {
        final SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    /**
     * Gets success result.
     * 성공 결과만 처리하는 메소드
     *
     * @return the success result
     * @author [오지훈]
     */
    public CommonResult getSuccessResult() {
        final CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    /**
     * Gets fail result.
     * 실패 결과만 처리하는 메소드
     *
     * @return the fail result
     * @author [오지훈]
     */
    public CommonResult getFailResult() {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(FAIL.getCode());
        result.setMsg(FAIL.getMsg());
        return result;
    }

    /**
     * Gets fail result.
     * 실패 결과, 메세지를 처리하는 메소드
     *
     * @param code the code
     * @param msg  the msg
     * @return the fail result
     * @author [오지훈]
     */
    public CommonResult getFailResult(final String code, final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * Gets fail result.
     *
     * @param msg the msg
     * @return the fail result
     * @author [오지훈]
     */
    public CommonResult getFailResult(final String msg) {
        return getFailResult(FAIL.getCode(),msg);
    }

    /**
     * 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
     *
     * @author [오지훈]
     */
    private static void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        result.setCode(SUCCESS.getCode());
        result.setMsg(SUCCESS.getMsg());
    }

}
