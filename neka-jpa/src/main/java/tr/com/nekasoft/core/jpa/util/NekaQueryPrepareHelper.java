package tr.com.nekasoft.core.jpa.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;

public abstract class NekaQueryPrepareHelper<QM extends NekaQueryModel> {

    protected abstract <EQ> void buildQuery(BooleanBuilder builder, QM queryModel, JPAQuery<EQ> query);

    protected abstract void buildBasicPredicate(BooleanBuilder builder, QM queryModel);


    public BooleanBuilder prepareBasicPredicate(QM querymodel) {
        BooleanBuilder builder = new BooleanBuilder();
        if (querymodel != null) {
            buildBasicPredicate(builder, querymodel);
        }
        return builder;
    }


    public <EQ> void prepareQuery(QM querymodel, JPAQuery<EQ> query) {
        BooleanBuilder builder = new BooleanBuilder();
        if (querymodel != null) {
            buildBasicPredicate(builder, querymodel);
            buildQuery(builder, querymodel, query);
        }
    }

}
