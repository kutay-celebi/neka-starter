package tr.com.nekasoft.core.jpa.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kutay Celebi
 * @since 23.02.2019
 */
@Data
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class NekaEntity implements Serializable {

    public static final String ID_FIELD = "id";
    public static final String DELETED_FIELD = "deleted";
    public static final String CREATED_FIELD = "createdAt";
    public static final String DELETED_AT_FIELD = "deletedAt";
    private static final long serialVersionUID = 402199730764879680L;

    public NekaEntity() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "version")
    @Version
    private Long version;

    @Column(name = "DELETED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name = "DELETED")
    @NotNull
    @Builder.Default
    private Boolean deleted = false;

    @PrePersist
    private void beforePersist() {
        this.prePersist();
    }

    protected void prePersist() {
        // should be override
    }

    @PreRemove
    void preRemove() {
        this.deleted = Boolean.TRUE;
        this.deletedAt = LocalDateTime.now();
    }
}
