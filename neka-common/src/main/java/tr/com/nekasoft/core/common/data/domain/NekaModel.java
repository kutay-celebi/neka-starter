package tr.com.nekasoft.core.common.data.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class NekaModel implements Serializable {

    private static final long serialVersionUID = -4120451606941319395L;

    private String id;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String lastModifiedBy;

    private Date deletedAt;

    private Long version;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;
}
