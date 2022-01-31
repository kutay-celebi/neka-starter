package tr.com.nekasoft.core.service.restclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "neka.rest", ignoreInvalidFields = true)
public class NekaRestClientProperties {
    private Map<String, NekaClientProperties> clients = new HashMap<>();
}
