package com.nike.dnp.service;

import com.nike.dnp.common.variable.CommonResponse;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * ResponseService
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:00:51
 * @Description 공통 response 서비스 작성
 */
@Service
@RequiredArgsConstructor
public class ResponseService {

    /**
     * CommonResponse FAIL
     *
     * @author [오지훈]
     */
    private static final CommonResponse FAIL = CommonResponse.FAIL;

    /**
     * CommonResponse SUCCESS
     *
     * @author [오지훈]
     */
    private static final CommonResponse SUCCESS = CommonResponse.SUCCESS;

    /**
     * Gets single result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:00:51
     * @Description 단일건 결과를 처리하는 메소드
     */
    public <T> SingleResult<T> getSingleResult(final T data) {
        final SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    /**
     * Gets single result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @param msg  the msg
     * @return the single result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 30. 오후 4:59:21
     * @Description 메시지 포함
     */
    public <T> SingleResult<T> getSingleResult(final T data
            , final String code, final String msg, final Boolean existMsg) {
        final SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        result.setExistMsg(existMsg);
        result.setCode(code);
        result.setMsg(msg);
        setSuccessResult(result);
        return result;
    }

    /**
     * Gets success result.
     *
     * @return the success result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:00:51
     * @Description 성공 결과만 처리하는 메소드
     */
    public CommonResult getSuccessResult() {
        final CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    /**
     * Sets success result.
     *
     * @param result the result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:00:51
     * @Description 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
     */
    private static void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        if(result.getCode().isEmpty()) result.setCode(SUCCESS.getCode());
        if(result.getMsg().isEmpty()) result.setMsg(SUCCESS.getMsg());
    }

    /**
     * Gets fail result.
     *
     * @return the fail result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:00:51
     * @Description 실패 결과만 처리하는 메소드
     */
    public CommonResult getFailResult() {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setExistMsg(true);
        result.setCode(FAIL.getCode());
        result.setMsg(FAIL.getMsg());
        return result;
    }

    /**
     * Gets fail result.
     *
     * @param code the code
     * @param msg  the msg
     * @return the fail result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:00:51
     * @Description 실패 결과, 메세지를 처리하는 메소드
     */
    public CommonResult getFailResult(final String code, final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setExistMsg(true);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public CommonResult getFailResult(final String code, final String msg, final HashMap<String, Object> payload) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setExistMsg(true);
        result.setCode(code);
        result.setMsg(msg);
        result.setPayload(payload);
        return result;
    }

    /**
     * Gets fail result.
     *
     * @param msg the msg
     * @return the fail result
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:00:51
     * @Description
     */
    public CommonResult getFailResult(final String msg) {
        return getFailResult(FAIL.getCode(), msg);
    }

}
