package tr.com.nekasoft.core.common.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NekaSort implements Serializable {

    private static final long serialVersionUID = 5445615637321283944L;

    private String field;
    private boolean asc;
    @Builder.Default
    private boolean nullHandling = false;
}
