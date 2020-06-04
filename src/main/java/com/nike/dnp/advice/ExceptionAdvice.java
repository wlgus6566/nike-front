package com.nike.dnp.advice;

import com.nike.dnp.exception.CodeMessageHandleException;
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
     * status 200 Exception
     *
     * @param request
     * @param e
     * @return 상태값 : 200, 코드, 메세지
     */
    @ExceptionHandler(CodeMessageHandleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult codeMessageHandleException(HttpServletRequest request, CodeMessageHandleException e) {
        logger.error("==================ERROR===================");
        logger.error("Exception Status200Exception: "+ e.getMessage());

        logger.error(e.getLocalizedMessage());
        logger.error(ExceptionUtils.getStackTrace(e));
        return responseService.getFailResult(e.getCode(), e.getMessage());
    }

    /**
     * 정의 된 오류 외의 excpetion
     *
     * @param request the request
     * @param e       the e
     * @return the common result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult globalHandelException(HttpServletRequest request, Exception e) {
        logger.error("==================Global ERROR===================");
        logger.error(e.getLocalizedMessage());
        logger.error(ExceptionUtils.getStackTrace(e));

        return responseService.getFailResult();
    }



}