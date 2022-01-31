package tr.com.nekasoft.core.jwt.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class NekaPermissionEvulator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object domain, Object permission) {
        if (authentication == null || domain == null || !(permission instanceof String)) {
            return false;
        }

        final String domainType = domain.getClass().getSimpleName().toUpperCase();
        return hasPrivilege(authentication, domainType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String domainType, Object permission) {
        if ((authentication == null) || (domainType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(authentication, domainType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String domainType, String permission) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().startsWith(domainType)) {
                return authority.getAuthority().contains(permission);
            }
        }
        return false;
    }
}
