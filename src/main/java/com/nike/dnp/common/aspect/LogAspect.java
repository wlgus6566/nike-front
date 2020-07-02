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
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
    public Object onAroundActionLog(final ProceedingJoinPoint joinPoint) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {
            log.debug("========================= ActionLog Start =========================");
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
            log.debug("========================= ActionLog End =========================");
            return joinPoint.proceed();

        } catch (Throwable throwable) {
            throw new CodeMessageHandleException(
                    ErrorEnumCode.ExceptionError.ERROR.toString()
                    , throwable
            );
        }
    }

}
