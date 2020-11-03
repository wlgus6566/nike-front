package com.nike.dnp.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.log.ErrorLogSaveDTO;
import com.nike.dnp.exception.CodeMessageHandleErrorException;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.FileHandleException;
import com.nike.dnp.exception.NotFoundHandleException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.service.log.ErrorLogService;
import com.nike.dnp.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
     * 정의 된 오류 외의 exception
     *
     * @param exception the e
     * @return 상태값 : 500, 코드, 메세지
     * @author [이소정]
     * @implNote 정의 된 오류 외의 exception
     * @since 2020. 7. 30. 오후 4:00:38
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CommonResult globalHandelException(final Exception exception) {
        log.error("==================Global ERROR ===================");
        log.error("global message", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ExceptionError.ERROR.name()));
        return responseService.getFailResult();
    }

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
        log.error("CodeMessageHandleException", exception);
        this.errorLogInsert(exception.getDescription());
        return responseService.getFailResult(
                exception.getCode()
                , exception.getDescription());
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
        log.error("CodeMessageHandleErrorException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ConfigureError.NO_AUTH.name()));
        return responseService.getFailResult(
                FailCode.ConfigureError.NO_AUTH.name()
                , MessageUtil.getMessage(FailCode.ConfigureError.NO_AUTH.name()));
    }

    /**
     * File Exception
     *
     * @param exception the exception
     * @return 상태값 : 500, 코드, 메세지
     * @author [이소정]
     * @implNote File Exception
     * @since 2020. 8. 31. 오후 3:59:34
     */
    @ExceptionHandler(FileHandleException.class)
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected CommonResult fileHandleException(final FileHandleException exception) {
        log.error("FileHandleException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
        return responseService.getFailResult(
                FailCode.ConfigureError.INVALID_FILE.name()
                , MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
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
        log.error("NotFoundHandleException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()));
        return responseService.getFailResult(
                FailCode.ExceptionError.NOT_FOUND.name()
                , MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()));
    }

    /**
     * 정의 된 오류 외의 exception
     *
     * @param exception the e
     * @return 상태값 : 500, 코드, 메세지
     * @author [이소정]
     * @implNote 정의 된 오류 외의 exception
     * @since 2020. 7. 30. 오후 4:00:38
     */
    @ExceptionHandler({InterruptedException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CommonResult interruptedException(final Exception exception) {
        log.error("InterruptedException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
        return responseService.getFailResult(
                FailCode.ConfigureError.INVALID_FILE.name()
                , MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
    }


    /**
     * Custom io exception common result.
     *
     * @param exception the exception
     * @return the common result
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 11. 3. 오전 11:07:21
     */
    @ExceptionHandler({IOException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CommonResult customIoException(final IOException exception) {
        log.error("IOException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ExceptionError.ERROR.name()));
        return responseService.getFailResult(
                FailCode.ExceptionError.ERROR.name()
                , MessageUtil.getMessage(FailCode.ExceptionError.ERROR.name())
        );
    }

    /**
     * Custom method argument type mismatch exception common result.
     *
     * @param exception the exception
     * @return the common result
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 11. 3. 오전 11:07:22
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CommonResult customMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        log.error("MethodArgumentTypeMismatchException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ExceptionError.METHOD.name()));
        return responseService.getFailResult(
                FailCode.ExceptionError.METHOD.name()
                , MessageUtil.getMessage(FailCode.ExceptionError.METHOD.name())
        );
    }


    /**
     * Custom data exception common result.
     *
     * @param exception the exception
     * @return the common result
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 11. 3. 오전 11:07:19
     */
    @ExceptionHandler({DataException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CommonResult customDataException(final DataException exception) {
        log.error("DataException", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name()));
        return responseService.getFailResult(
                FailCode.ExceptionError.NOT_FOUND.name()
                , MessageUtil.getMessage(FailCode.ExceptionError.NOT_FOUND.name())
        );
    }

    /**
     * Custom exception common result.
     *
     * @param exception the exception
     * @return the common result
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 11. 3. 오전 11:07:18
     */
    @ExceptionHandler({NumberFormatException.class, JsonMappingException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CommonResult customException(final Exception exception) {
        log.error("Exception", exception);
        this.errorLogInsert(MessageUtil.getMessage(FailCode.ExceptionError.METHOD.name()));
        return responseService.getFailResult(
                FailCode.ExceptionError.METHOD.name()
                , MessageUtil.getMessage(FailCode.ExceptionError.METHOD.name())
        );
    }

    /**
     * Error log insert.
     *
     * @param message the message
     * @author [오지훈]
     * @implNote [Description 작성]
     * @since 2020. 11. 3. 오전 10:55:33
     */
    @Transactional
    public void errorLogInsert(final String message) {
        final HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication) && authentication.isAuthenticated()) {
            final ErrorLogSaveDTO errorLog = new ErrorLogSaveDTO();
            errorLog.setUrl(request.getRequestURI());
            errorLog.setErrorContents(message);
            errorLogService.save(errorLog);
        }
    }

}
