package tr.com.nekasoft.core.jpa.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.nekasoft.core.jpa.configuration.property.NekaAuditProperty;

/**
 * @author Kutay Celebi
 * @since 7.10.2019
 */
@Configuration
@EnableConfigurationProperties({NekaAuditProperty.class})
@EntityScan(basePackages = "tr.com.nekasoft.core.jpa.entity")
public class NekaJpaConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "neka.jpa.audit", name = "enable")
    public NekaAuditProperty nekaAuditorAware() {
        return new NekaAuditProperty();
    }
}

