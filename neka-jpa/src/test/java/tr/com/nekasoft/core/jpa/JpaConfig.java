package tr.com.nekasoft.core.jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tr.com.nekasoft.core.jpa.bean.NekaRepositoryFactoryBean;

@EnableAutoConfiguration
@ComponentScan("tr.com.nekasoft")
@Configuration
@EntityScan(basePackages = "tr.com.nekasoft")
@EnableJpaRepositories(repositoryFactoryBeanClass = NekaRepositoryFactoryBean.class,
                       basePackages = "tr.com.nekasoft")
public class JpaConfig {
}
