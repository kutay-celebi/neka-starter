package tr.com.nekasoft.core.service.restclient;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NekaClientProperties {
    private String baseUrl;
    private Map<String, String> headers = new HashMap<>();

}
