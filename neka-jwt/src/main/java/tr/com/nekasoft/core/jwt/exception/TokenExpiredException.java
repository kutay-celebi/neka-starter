package tr.com.nekasoft.core.jwt.exception;


import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

    private static final long serialVersionUID = 7741463432075998638L;

    public TokenExpiredException(String msg) {
        super(msg);
    }

    public TokenExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
