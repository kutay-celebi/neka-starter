package tr.com.nekasoft.core.jpa.util;

import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import tr.com.nekasoft.core.common.data.domain.NekaPage;

import java.util.List;

/**
 * @author Kutay Celebi
 * @since 13.11.2020 00:08
 */
public class ExecutionUtils {

    public static <T> NekaPage<T> getPage(List<T> content, Pageable pageable, long count) {

        Assert.notNull(content, "Content must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");


        long currentLast = 0;
        long currentFirst = 0;
        if (pageable.getPageNumber() == 0 && count > 0) {
            // if it is first page
            currentFirst = 1;
            currentLast  = content.size();
        } else if (count > 0) {
            currentLast  = (long) (pageable.getPageNumber() + 1) * pageable.getPageSize();
            currentFirst = currentLast - pageable.getPageSize() + 1;
        }

        int totalPages = pageable.getPageSize() == 0 ? 1 : (int) Math.ceil(
                (double) count / (double) pageable.getPageSize());
        if (pageable.getPageNumber() + 1 == totalPages) {
            // if it is last page
            currentLast = ((long) pageable.getPageNumber() * pageable.getPageSize()) + content.size();
        }
        return new NekaPage<>(content, count, pageable.getPageSize(), totalPages, pageable.getPageNumber() + 1,
                currentLast, currentFirst);
    }

}
