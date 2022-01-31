package tr.com.nekasoft.core.jwt;

import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import tr.com.nekasoft.core.jwt.security.NekaMethodSecurityExpressionHandler;
import tr.com.nekasoft.core.jwt.security.NekaPermissionEvulator;

public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        final NekaMethodSecurityExpressionHandler handler = new NekaMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new NekaPermissionEvulator());
        return handler;
    }
}
