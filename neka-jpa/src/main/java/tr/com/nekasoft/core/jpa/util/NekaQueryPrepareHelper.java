package tr.com.nekasoft.core.jpa.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;

public abstract class NekaQueryPrepareHelper<EQ, QM extends NekaQueryModel> {

    protected QM querymodel;
    protected JPAQuery<EQ> query;

    public NekaQueryPrepareHelper(QM querymodel) {
        this.querymodel = querymodel;
    }

    protected abstract void buildQuery(BooleanBuilder builder, QM queryModel, JPAQuery<EQ> query);

    protected abstract void buildBasicPredicate(BooleanBuilder builder, QM queryModel);


    public BooleanBuilder prepareBasicPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        if (querymodel != null) {
            buildBasicPredicate(builder, querymodel);
        }
        return builder;
    }


    public void prepareQuery() {
        BooleanBuilder builder = new BooleanBuilder();
        if (querymodel != null) {
            buildBasicPredicate(builder, querymodel);
            buildQuery(builder, querymodel, query);
        }
    }

}
