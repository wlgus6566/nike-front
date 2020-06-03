package com.nike.dnp.advice;

import com.nike.dnp.exception.ErrorEnumCode;
import com.nike.dnp.exception.NoRequiredValueException;
import com.nike.dnp.exception.TargetNotFoundException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Global Exception Handler
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description Global Exception Handler 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */

@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    private final ResponseService responseService;

    public ExceptionAdvice(ResponseService responseService) {
        this.responseService = responseService;
    }

    /**
     * 조회한 값이 없을 경우 (목록 제외, ex_상세 페이지 등)
     *
     * @param request
     * @param e
     * @return 상태값 : 200, 실페 메세지
     */
    @ExceptionHandler(TargetNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult targetNotFoundException(HttpServletRequest request, TargetNotFoundException e) {
        logger.error("==================ERROR===================");
        logger.error("Exception targetNotFoundException: "+ e.getMessage());

        logger.error(e.getLocalizedMessage());
        logger.error(ExceptionUtils.getStackTrace(e));
        return responseService.getFailResult(e.getCode(), e.getMessage());
    }
//
    /**
     * 필수 값이 없을 경우
     *
     * @param request
     * @param e
     * @return 상태값 : 200, 실페 메세지
     */
    @ExceptionHandler(NoRequiredValueException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult noRequiredValueException(HttpServletRequest request, NoRequiredValueException e) {
        logger.error("==================ERROR===================");
        logger.error("Exception nullPointException: "+ e.getMessage());

        logger.error(e.getLocalizedMessage());
        logger.error(ExceptionUtils.getStackTrace(e));
        return responseService.getFailResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleExcption(HttpServletRequest request, Exception e) {
        logger.error("==================Global ERROR===================");
        logger.error(e.getLocalizedMessage());
        logger.error(ExceptionUtils.getStackTrace(e));

        return responseService.getFailResult();
    }



}
