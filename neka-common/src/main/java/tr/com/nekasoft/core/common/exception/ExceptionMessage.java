package tr.com.nekasoft.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Servislerden donulecek exception mesajidir.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExceptionMessage extends BaseExceptionMessage implements Serializable {
    private static final long serialVersionUID = -6447606835316666789L;

    private String message;

    private ExceptionCode type;

    private List<Object> messageArgument;
}
