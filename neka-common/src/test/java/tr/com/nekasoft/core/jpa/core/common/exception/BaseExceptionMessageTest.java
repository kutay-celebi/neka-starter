package tr.com.nekasoft.core.jpa.core.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.com.nekasoft.core.common.exception.BaseExceptionMessage;

import static org.junit.jupiter.api.Assertions.*;

class BaseExceptionMessageTest {

    @Test
    @DisplayName("exception message test")
    void testExceptionMessage() {
        BaseExceptionMessage exceptionMessage = BaseExceptionMessage.builder().build();
        Assertions.assertNotNull(exceptionMessage.getDate());
    }

}
