package tr.com.nekasoft.core.common.security;

import java.util.Set;

/**
 * @author Kutay Celebi
 * @since 11.12.2020 17:17
 */
public interface NekaPrincipal {

    String getId();

    String getUsername();

    Set<String> getAuthorityList();

    Set<String> getRoleList();
}
