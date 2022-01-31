package tr.com.nekasoft.core.jpa.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;
import tr.com.nekasoft.core.common.data.domain.NekaSort;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class PageableConverter {


    public static Pageable toPageable(NekaQueryModel nekaQueryModel) {
        if (CollectionUtils.isNotEmpty(nekaQueryModel.getSort())) {
            List<Sort.Order> orderList = new ArrayList<>();
            for (NekaSort nekaSort : nekaQueryModel.getSort()) {
                Sort.Order order = new Sort.Order(
                        nekaSort.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, nekaSort.getField());

                if (nekaSort.isNullHandling()) {
                    order = order.nullsFirst();
                } else {
                    order = order.nullsLast();
                }


                orderList.add(order);
            }
            return PageRequest.of(nekaQueryModel.getPage() - 1, nekaQueryModel.getPageSize(), Sort.by(orderList));
        }
        return PageRequest.of(nekaQueryModel.getPage() - 1, nekaQueryModel.getPageSize());
    }
}
