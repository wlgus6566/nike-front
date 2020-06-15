package com.nike.dnp.common.aspect;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.dto.log.UserActionLogSaveDTO;
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
 * The type Log aspect.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    /**
     * UserActionLogService
     */
    private final UserActionLogService actionLogService;

    /**
     * On around action log object.
     * 유저 활동 로그 등록
     *
     * @param joinPoint the join point
     * @return the object
     */
    //@Around("execution(public * com.nike.dnp.controller..*Controller.*(..)) && args(requestDTO,..)")
    @Around("execution(public * com.nike.dnp.controller..*Controller.*(..))")
    public Object onAroundActionLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.debug("========================= ActionLog Start =========================");
        final UserActionLogSaveDTO actionLog = new UserActionLogSaveDTO();
        for (final Object obj : joinPoint.getArgs()) {
            if (!ObjectUtils.isEmpty(obj) && obj instanceof AuthUserDTO) {
                actionLog.setUserSeq(((AuthUserDTO) obj).getUserSeq());
                actionLog.setUrl(request.getRequestURI());
                actionLog.setParameter(Arrays.toString(joinPoint.getArgs()));
                actionLog.setMethodTypeName(request.getMethod());
                actionLog.setMethodSignature(joinPoint.getSignature().getName());
                actionLogService.save(actionLog, (AuthUserDTO) obj);
            }
        }
        log.debug("========================= ActionLog End =========================");

        final Object returnObj = joinPoint.proceed();
        return returnObj;
    }

}
