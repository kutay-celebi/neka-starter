package tr.com.nekasoft.core.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tr.com.nekasoft.core.common.exception.BaseExceptionType;
import tr.com.nekasoft.core.common.exception.ExceptionMessage;
import tr.com.nekasoft.core.jwt.exception.TokenExpiredException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kutay Celebi
 * @since 20.08.2021
 */
@Slf4j
public class NekaJwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionMessage message = ExceptionMessage.builder().type(BaseExceptionType.AUTHENTICATION_EXCEPTION).build();
        if (authException instanceof BadCredentialsException) {
            message.setType(BaseExceptionType.BAD_CREDENTIAL);
        } else if (authException instanceof TokenExpiredException){
            message.setType(BaseExceptionType.TOKEN_EXPIRED);
        }

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), message);
        response.flushBuffer();
    }
}
