package com.nike.dnp.service;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.SuccessCode;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * ResponseService
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:00:51
 * @implNote 공통 response 서비스 작성
 */
@Service
@RequiredArgsConstructor
public class ResponseService {

    /**
     * Gets single result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the single result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 6:00:51
     * @implNote 단일건 결과를 처리하는 메소드
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
     * @since 2020. 6. 30. 오후 4:59:21
     * @implNote 메시지 포함
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
     * @since 2020. 6. 24. 오후 6:00:51
     * @implNote 성공 결과만 처리하는 메소드
     */
    public CommonResult getSuccessResult() {
        final CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    /**
     * Gets success result.
     *
     * @param code the code
     * @param msg  the msg
     * @return the success result
     * @author [오지훈]
     * @since 2020. 7. 27. 오후 4:41:38
     * @implNote 성공 코드와 메시지만 리턴하는 메소드
     */
    public CommonResult getSuccessResult(final String code, final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(true);
        result.setExistMsg(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * Sets success result.
     *
     * @param result the result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 6:00:51
     * @implNote 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
     */
    private static void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        if(result.getCode().isEmpty()) {
            result.setCode(SuccessCode.ConfigureSuccess.SUCCESS.name());
        }
        if(result.getMsg().isEmpty()) {
            result.setMsg(MessageUtil.getMessage(SuccessCode.ConfigureSuccess.SUCCESS.name()));
        }
    }

    /**
     * Gets fail result.
     *
     * @return the fail result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 6:00:51
     * @implNote 실패 결과만 처리하는 메소드
     */
    public CommonResult getFailResult() {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setExistMsg(true);
        result.setCode(FailCode.ExceptionError.ERROR.name());
        result.setMsg(MessageUtil.getMessage(FailCode.ExceptionError.ERROR.name()));
        return result;
    }

    /**
     * Gets fail result.
     *
     * @param code the code
     * @param msg  the msg
     * @return the fail result
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 6:00:51
     * @implNote 실패 결과, 메세지를 처리하는 메소드
     */
    public CommonResult getFailResult(final String code, final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setExistMsg(true);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * Gets fail result.
     *
     * @param code    the code
     * @param msg     the msg
     * @param payload the payload
     * @return the fail result
     * @author [오지훈]
     * @implNote [method 설명]
     * @since 2020. 7. 30. 오후 3:56:43
     */
    public CommonResult getFailResult(final String code, final String msg, final Set<HashMap<String, Object>> payload) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setExistMsg(true);
        result.setCode(code);
        result.setMsg(msg);
        result.setPayload(payload);
        return result;
    }

}
