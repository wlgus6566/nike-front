package com.nike.dnp.advice;

import com.nike.dnp.dto.log.ErrorLogSaveDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.ErrorLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Global Exception Handler
 *
 * @author [이소정]
 * @Description Global Exception Handler 작성
 * @history [이소정] [2020.06.03] [최초 작성]
 * @since 2020.06.03
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    /**
     * ResponseService
     * @author [이소정]
     */
    private final ResponseService responseService;

    /**
     * ErrorLogService
     *
     * @author [오지훈]
     */
    private final ErrorLogService errorLogService;

    /**
     * status 200 Exception
     *
     * @param exception the exception
     * @return 상태값 : 200, 코드, 메세지
     * @author [이소정]
     */
    @ExceptionHandler(CodeMessageHandleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult codeMessageHandleException(final CodeMessageHandleException exception) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.debug("==================ERROR===================");
        log.debug("Exception Status200Exception", exception);
        log.debug("========================= ErrorLog Start =========================");
        final ErrorLogSaveDTO errorLog = new ErrorLogSaveDTO();
        errorLog.setUrl(request.getRequestURI());
        errorLog.setErrorContents(exception.getMessage());
        errorLogService.save(errorLog);
        log.debug("========================= End End =========================");

        return responseService.getFailResult(exception.getCode(), exception.getMessage());
    }

    /**
     * 정의 된 오류 외의 excpetion
     *
     * @param exception the e
     * @return the common result
     * @author [이소정]
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult globalHandelException(final Exception exception) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.debug("==================Global ERROR===================");
        log.debug("Exception", exception);
        log.debug("========================= ErrorLog Start =========================");
        final ErrorLogSaveDTO errorLog = new ErrorLogSaveDTO();
        errorLog.setUrl(request.getRequestURI());
        errorLog.setErrorContents(exception.getMessage());
        errorLogService.save(errorLog);
        log.debug("========================= ErrorLog End =========================");

        return responseService.getFailResult();
    }

}
