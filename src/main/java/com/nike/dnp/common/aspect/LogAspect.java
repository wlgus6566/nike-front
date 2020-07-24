package com.nike.dnp.common.aspect;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.log.UserActionLogSaveDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.service.log.UserActionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * LogAspect
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 6:05:00
 * @Description LogAspect 작성
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    /**
     * UserActionLogService
     *
     * @author [오지훈]
     */
    private final UserActionLogService actionLogService;

    private final MessageSource messageSource;

    /**
     * On around action log object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     * @author [오지훈]
     * @CreatedOn 2020. 6. 24. 오후 6:05:00
     * @Description 유저 활동 로그 등록
     */
    //@Around("execution(public * com.nike.dnp.controller..*Controller.*(..)) && args(requestDTO,..)")
    @Around("execution(public * com.nike.dnp.controller..*Controller.*(..))")
    public Object onAroundActionLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication) && authentication.isAuthenticated()) {
            final HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            final UserActionLogSaveDTO actionLog = new UserActionLogSaveDTO();
            for (final Object obj : joinPoint.getArgs()) {
                if (!ObjectUtils.isEmpty(obj) && obj instanceof AuthUserDTO) {
                    actionLog.setUserSeq(((AuthUserDTO) obj).getUserSeq());
                    actionLog.setUrl(request.getRequestURI());
                    actionLog.setParameter(Arrays.toString(joinPoint.getArgs()));
                    actionLog.setMethodTypeName(request.getMethod());
                    actionLog.setMethodSignature(joinPoint.getSignature().getName());
                    actionLogService.save(actionLog);
                }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * On around valid field object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     * @author [오지훈]
     * @CreatedOn 2020. 7. 22. 오후 3:53:47
     * @Description 필드 필수 체크
     */
    @Around("@annotation(ValidField)")
    public Object onAroundValidField(final ProceedingJoinPoint joinPoint) throws Throwable {
        for (final Object obj : joinPoint.getArgs()) {
            if (!ObjectUtils.isEmpty(obj) && obj instanceof BindingResult) {
                final BindingResult result = (BindingResult) obj;
                if (result.hasErrors()) {
                    throw new CodeMessageHandleException(
                            ErrorEnumCode.DataError.INVALID.toString()
                            , messageSource.getMessage(Objects.requireNonNull(result.getAllErrors().get(0).getDefaultMessage()), null, Locale.KOREA));
                }
            }
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

}
