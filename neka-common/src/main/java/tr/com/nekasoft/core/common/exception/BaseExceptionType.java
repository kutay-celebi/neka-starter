package tr.com.nekasoft.core.common.exception;

import lombok.AllArgsConstructor;

/**
 * @author Kutay Celebi
 * @since 24.08.2021
 */
@AllArgsConstructor
public enum BaseExceptionType implements ExceptionCode {

    AUTHENTICATION_EXCEPTION("NK-0001"),
    BAD_CREDENTIAL("NK-0002"),
    FORBIDDEN("NK-0003"),
    TOKEN_EXPIRED("NK-0004"),
    ;

    private String code;

    @Override
    public String exceptionCode() {
        return code;
    }
}
