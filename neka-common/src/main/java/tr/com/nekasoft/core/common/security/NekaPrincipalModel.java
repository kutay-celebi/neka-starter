package tr.com.nekasoft.core.common.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @author Kutay Celebi
 * @since 9.12.2020 02:20
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NekaPrincipalModel implements NekaPrincipal {

    protected String id;
    protected String username;
    protected Set<String> authorityList;
    protected Set<String> roleList;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Set<String> getAuthorityList() {
        return authorityList;
    }

    @Override
    public Set<String> getRoleList() {
        return roleList;
    }
}
