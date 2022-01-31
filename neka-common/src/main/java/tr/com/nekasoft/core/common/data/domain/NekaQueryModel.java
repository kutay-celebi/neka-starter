package tr.com.nekasoft.core.common.data.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class NekaQueryModel {
    @Builder.Default
    protected Boolean deleted = Boolean.FALSE;
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer pageSize = Integer.MAX_VALUE;

    private List<NekaSort> sort;
}
