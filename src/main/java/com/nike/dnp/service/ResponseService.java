package com.nike.dnp.service;

import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import org.springframework.stereotype.Service;

/**
 * ResponseService
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 공통 response 서비스 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Service
public class ResponseService {

    /**
     * The enum Common response.
     * enum으로 api 요청 결과에 대한 code, message를 정의합니다.
     */
    public enum CommonResponse {
        SUCCESS("SUC", "성공"),
        FAIL("ERR", "에러발생");

        /**
         *
         */
        String code;

        /**
         *
         */
        String msg;

        CommonResponse(final String code, final String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * Gets single result.
     * 단일건 결과를 처리하는 메소드
     *
     * @param data the data
     * @return the single result
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
     */
    public CommonResult getFailResult() {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
        return result;
    }

    /**
     * Gets fail result.
     * 실패 결과, 메세지를 처리하는 메소드
     *
     * @param code the code
     * @param msg  the msg
     * @return the fail result
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
     */
    public CommonResult getFailResult(final String msg) {
        return getFailResult(CommonResponse.FAIL.getCode(),msg);
    }

    /**
     * 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
     */
    private static void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

}
