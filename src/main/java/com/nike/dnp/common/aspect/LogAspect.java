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

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final UserActionLogService userActionLogService;

    //@Around("execution(public * com.nike.dnp.controller..*Controller.*(..)) && args(requestDTO,..)")
    @Around("execution(public * com.nike.dnp.controller..*Controller.*(..))")
    public Object onAround(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {
            log.debug("========================= ActionLog Start =========================");
            for (Object obj : joinPoint.getArgs()) {
                if (!ObjectUtils.isEmpty(obj)) {
                    if (obj instanceof AuthUserDTO) {
                        UserActionLogSaveDTO actionLog = new UserActionLogSaveDTO();
                        actionLog.setUserSeq(((AuthUserDTO) obj).getUserSeq());
                        actionLog.setUrl(request.getRequestURI());
                        actionLog.setParameter(Arrays.toString(joinPoint.getArgs()));
                        actionLog.setMethodTypeName(request.getMethod());
                        actionLog.setMethodSignature(joinPoint.getSignature().getName());
                        userActionLogService.save(actionLog, (AuthUserDTO) obj);
                    }
                }
            }
            log.debug("========================= ActionLog End =========================");

            Object returnObj = joinPoint.proceed();
            return returnObj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return new Object();
    }

}
