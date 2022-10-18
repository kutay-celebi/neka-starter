package tr.com.nekasoft.core.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dummy")
@Entity
public class Dummy extends NekaEntity {
    private static final long serialVersionUID = -1669046540482697439L;

    @Column(name = "dummy_column")
    private String dummyColumn;
}
