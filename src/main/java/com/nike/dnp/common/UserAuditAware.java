package com.nike.dnp.common;

import com.nike.dnp.dto.auth.AuthUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The Class User audit aware.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 30. 오후 4:27:15
 * @Description
 */
@Slf4j
@Service
public class UserAuditAware implements AuditorAware<Long> {

    /*@Override
    public Optional<AuthUserDTO> getCurrentAuditor() {
        log.info("UserAuditAware.getCurrentAuditor");
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(AuthUserDTO.class::cast);
    }*/

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     * @author [오지훈]
     * @CreatedOn 2020. 6. 30. 오후 4:27:15
     * @Description
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        log.info("UserAuditAware.getCurrentAuditor");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return null;
        }
        AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();
        return Optional.of(authUserDTO.getUserSeq());
    }
}
