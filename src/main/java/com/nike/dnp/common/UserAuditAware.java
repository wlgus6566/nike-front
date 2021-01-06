package com.nike.dnp.common;

import com.nike.dnp.dto.auth.AuthUserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * The Class User audit aware.
 *
 * @author [오지훈]
 * @since 2020. 6. 30. 오후 4:27:15
 * @implNote
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserAuditAware implements AuditorAware<Long> {

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     * @author [오지훈]
     * @since 2020. 6. 30. 오후 4:27:15
     * @implNote
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        log.info("UserAuditAware.getCurrentAuditor");
        Optional<Long> result = Optional.of(0L);

        log.info("22222 UserAuditAware.getCurrentAuditor");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("3333333333 UserAuditAware.getCurrentAuditor");
        if (!ObjectUtils.isEmpty(authentication) && authentication.isAuthenticated()) {
            final AuthUserDTO authUserDTO = (AuthUserDTO) authentication.getPrincipal();
            log.info("444 : ", authUserDTO.getUserSeq());
            result = Optional.of(authUserDTO.getUserSeq());
        }
        return result;
    }

}
