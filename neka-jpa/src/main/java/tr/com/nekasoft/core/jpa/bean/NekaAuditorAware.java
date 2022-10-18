package tr.com.nekasoft.core.jpa.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tr.com.nekasoft.core.common.security.NekaPrincipal;

import java.util.Optional;

/**
 * @author Kutay Celebi
 * @since 17.12.2020 11:41
 */
public class NekaAuditorAware implements AuditorAware<String> {
    private static final Logger LOG = LoggerFactory.getLogger(NekaAuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }

        final Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }

        final Object principal = authentication.getPrincipal();
        if (principal instanceof NekaPrincipal) {
            final String userName = ((NekaPrincipal) principal).getUsername();

            if (userName == null) {
                LOG.warn("Failed to get user information while registering...");
                return Optional.empty();
            }

            return Optional.of(userName);
        }

        if (principal instanceof String) {
            LOG.debug("Registered with Anonymous user.");
            return Optional.empty();
        }


        return Optional.empty();
    }
}
