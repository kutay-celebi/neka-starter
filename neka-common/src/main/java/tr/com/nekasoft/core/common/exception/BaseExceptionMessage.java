package tr.com.nekasoft.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * @author Kutay Celebi
 * @since 14.12.2020 20:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseExceptionMessage {

    /**
     * Exceptionun tarihidir.
     */
    @Builder.Default
    private final Instant date = Instant.now();
    /**
     * Exceptionun kolay trace edilmesi amaciyla her bir exceptiona tekil bir numara verilir.
     */
    @Builder.Default
    private final String uuid = UUID.randomUUID().toString();

    private Map<String, Object> exceptionArguments;

    public void addArgument(String key, Object argument) {
        this.exceptionArguments.put(key, argument);
    }

    public void deleteArgument(String key) {
        this.exceptionArguments.remove(key);
    }
}
