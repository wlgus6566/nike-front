package com.nike.dnp.exception;

import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ResponseService responseService;

    public GlobalExceptionHandler(ResponseService responseService) {
        this.responseService = responseService;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleExcption(HttpServletRequest request, Exception ex) {
        logger.error("==================ERROR===================");
        logger.error(ex.getLocalizedMessage());
        logger.error(ExceptionUtils.getStackTrace(ex));

        return responseService.getFailResult();
    }
}
