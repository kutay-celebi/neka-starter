package tr.com.nekasoft.core.jpa.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kutay Celebi
 * @since 17.12.2020 11:52
 */
@ConfigurationProperties(prefix = "neka.jpa.audit")
public class NekaAuditProperty {
    public Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
