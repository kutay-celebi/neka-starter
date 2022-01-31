package tr.com.nekasoft.core.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Exception kodlarinin yonetilecegi enumlarin interface sinifidir. Exceptionlarin birer kodunun olması ve mukerrer hata kodlarinin onune
 * gecmek icin exceptionlar bir enumun altinda toplanmasi icin yapilmistir.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface ExceptionCode {
    @JsonProperty("code")
    String exceptionCode();
}
