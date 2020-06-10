package com.nike.dnp.advice;

import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global Exception Handler
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description Global Exception Handler 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * ResponseService
     */
    private final transient ResponseService responseService;

    /**
     * Instantiates a new Exception advice.
     *
     * @param responseService the response service
     */
    public ExceptionAdvice(final ResponseService responseService) {
        this.responseService = responseService;
    }

    /**
     * status 200 Exception
     *
     * @param exception the exception
     * @return 상태값 : 200, 코드, 메세지
     */
    @ExceptionHandler(CodeMessageHandleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult codeMessageHandleException(final CodeMessageHandleException exception) {
        log.debug("==================ERROR===================");
        log.debug("Exception Status200Exception", exception);
        return responseService.getFailResult(exception.getCode(), exception.getMessage());
    }

    /**
     * 정의 된 오류 외의 excpetion
     *
     * @param exception the e
     * @return the common result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult globalHandelException(final Exception exception) {
        log.debug("==================Global ERROR===================");
        log.debug("Exception", exception);
        return responseService.getFailResult();
    }

}
