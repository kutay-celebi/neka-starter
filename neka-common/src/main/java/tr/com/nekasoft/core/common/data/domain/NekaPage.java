package tr.com.nekasoft.core.common.data.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class NekaPage<T> implements Serializable {
    private static final long serialVersionUID = -6211776190868815697L;

    private final List<T> content;
    private final Long total;
    private final Long pageSize;
    private final Long totalPages;
    private final Long page;
    private final Long currentLast;
    private final Long currentFirst;

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        return this.content.stream().map(converter).collect(Collectors.toList());
    }

    public <U> NekaPage<U> mapPage(Function<? super T, ? extends U> converter) {
        return new NekaPage<>(getConvertedContent(converter), total, pageSize, totalPages, page, currentLast, currentFirst);
    }

    public NekaPage(List<T> content, long total, long pageSize, long totalPages, long page, long currentLast, Long currentFirst) {
        this.content      = content;
        this.total        = total;
        this.pageSize     = pageSize;
        this.totalPages   = totalPages;
        this.page         = page;
        this.currentLast  = currentLast;
        this.currentFirst = currentFirst;
    }
}
