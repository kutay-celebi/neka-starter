package tr.com.nekasoft.core.service.restclient;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NekaRestClientProperties.class})
public class RestClientConfiguration {

}
