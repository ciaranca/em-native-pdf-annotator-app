package uk.gov.hmcts.reform.em.npa.config.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.em.npa.config.Constants;

import java.util.Optional;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
    }
}
