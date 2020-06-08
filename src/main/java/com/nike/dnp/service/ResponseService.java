package com.nike.dnp.service;

import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.model.response.SingleResult;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.
    public enum CommonResponse {
        SUCCESS("SUC", "성공"),
        FAIL("ERR", "에러발생");

        String code;
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

    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(final T data) {
        final SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

//    // 단일건 결과, 메세지 를 처리하는 메소드
//    public <T> SingleResult<T> getSingleResult(T data, String msg) {
//        SingleResult<T> result = new SingleResult<>();
//        result.setData(data);
//        /*if (!msg.isEmpty()) {
//            result.setMsg(msg);
//        }*/
//        setSuccessResult(result);
//        return result;
//    }
//
//    // 다중건 결과를 처리하는 메소드
//    public <T> ListResult<T> getListResult(List<T> list) {
//        ListResult<T> result = new ListResult<>();
//        result.setList(list);
//        setSuccessResult(result);
//        return result;
//    }

    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult() {
        final CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리하는 메소드
    public CommonResult getFailResult() {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
        return result;
    }

    // 실패 결과, 메세지를 처리하는 메소드
    public CommonResult getFailResult(final String code, final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

}
