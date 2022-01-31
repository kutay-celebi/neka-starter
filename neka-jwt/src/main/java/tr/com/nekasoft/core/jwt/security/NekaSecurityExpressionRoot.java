package tr.com.nekasoft.core.jwt.security;


import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import tr.com.nekasoft.core.common.security.NekaPrincipalModel;

import java.util.Set;

public class NekaSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    public NekaSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean checkYetki(String... privileges) {
        NekaPrincipalModel principal = (NekaPrincipalModel) authentication.getPrincipal();
        final Set<String> yetkiList = principal.getAuthorityList();

        if (yetkiList == null) {
            return false;
        }

        for (String userAuth : yetkiList) {
            for (String privilege : privileges) {
                if (privilege.startsWith(userAuth)) {
                    return true;
                }
            }
        }
        return false;
    }

    // todo javadoc.
    public boolean checkRol(String... roles) {
        NekaPrincipalModel principal = (NekaPrincipalModel) authentication.getPrincipal();
        Set<String> userRoles = principal.getRoleList();

        for (String userRole : userRoles) {
            for (String role : roles) {
                if (role.equals(userRole)) {
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
