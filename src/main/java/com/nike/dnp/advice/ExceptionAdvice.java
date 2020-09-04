package com.nike.dnp.advice;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.log.ErrorLogSaveDTO;
import com.nike.dnp.exception.CodeMessageHandleErrorException;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.ErrorLogService;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 * Global Exception Handler
 *
 * @author [이소정]
 * @implNote Global Exception Handler 작성
 * @since 2020.06.03 7. 30. 오후 4:00:37
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    /**
     * ResponseService
     *
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
     * @implNote CodeMessageHandleException 에 대한 response셋팅
     * @since 2020. 7. 30. 오후 4:00:37
     */
    @ExceptionHandler(CodeMessageHandleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult codeMessageHandleException(final CodeMessageHandleException exception) {
        log.error("==================Basic ERROR===================");
        log.error("Exception", exception);
        this.errorLogInsert(exception);
        return responseService.getFailResult(exception.getCode(), exception.getMessage());
    }

    /**
     * status 403 Exception
     *
     * @param exception the exception
     * @return 상태값 : 403, 코드, 메세지
     * @author [이소정]
     * @implNote CodeMessageHandleException 에 대한 response셋팅
     * @since 2020. 8. 31. 오후 3:59:34
     */
    @ExceptionHandler(CodeMessageHandleErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult CodeMessageHandleErrorException(final CodeMessageHandleErrorException exception) {
        log.error("==================NoAuth ERROR===================");
        log.error("Exception", exception);
        this.errorLogInsert(exception);
        return responseService.getFailResult(exception.getCode(), exception.getMessage());
    }

    /**
     * status 200 Exception
     *
     * @param exception the exception
     * @return 상태값 : 200, 코드, 메세지
     * @author [이소정]
     * @implNote NotFoundException 에 대한 response셋팅
     */
    @ExceptionHandler(NotFoundHandleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult notFoundException(final NotFoundHandleException exception) {
        log.error("==================NotFoundHandler ERROR===================");
        log.error("Exception", exception);
        this.errorLogInsert(exception);
        return responseService.getFailResult(exception.getCode(), exception.getMessage());
    }

    /**
     * 정의 된 오류 외의 excpetion
     *
     * @param exception the e
     * @return the common result
     * @author [이소정]
     * @implNote 정의 된 오류 외의 excpetion
     * @since 2020. 7. 30. 오후 4:00:38
     */
    @ExceptionHandler({InterruptedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult interruptedException(final Exception exception) {
        log.error("==================Interrupted ERROR===================");
        log.error("Exception", exception);
        this.errorLogInsert(exception);
        return responseService.getFailResult(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
    }

    /**
     * 정의 된 오류 외의 excpetion
     *
     * @param exception the e
     * @return the common result
     * @author [이소정]
     * @implNote 정의 된 오류 외의 excpetion
     * @since 2020. 7. 30. 오후 4:00:38
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult globalHandelException(final Exception exception) {
        log.error("==================Global ERROR===================");
        log.error("Exception", exception);
        this.errorLogInsert(exception);
        return responseService.getFailResult();
    }

    /**
     * Error log insert.
     *
     * @param exception the exception
     */
    @Transactional
    public void errorLogInsert(final Exception exception) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.error("========================= ErrorLog Start =========================");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication) && authentication.isAuthenticated()) {
            final ErrorLogSaveDTO errorLog = new ErrorLogSaveDTO();
            errorLog.setUrl(request.getRequestURI());
            errorLog.setErrorContents(exception.getMessage());
            errorLogService.save(errorLog);
        }
        log.error("========================= ErrorLog End =========================");
    }

}
