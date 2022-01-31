package tr.com.nekasoft.core.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Kutay Celebi
 * @since 21.08.2021
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -4659791025141344570L;
    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiryDuration;

    private NekaPrincipalModel principal;
}
