package tr.com.nekasoft.core.jpa.repository;

import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tr.com.nekasoft.core.common.data.domain.NekaPage;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;
import tr.com.nekasoft.core.jpa.util.ExecutionUtils;
import tr.com.nekasoft.core.jpa.util.PageableConverter;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kutay Celebi
 * @since 24.09.2019
 */
public class NekaRepositoryImpl<KE extends NekaEntity> extends SimpleJpaRepository<KE, String> implements NekaRepository<KE> {

    private static final SimpleEntityPathResolver PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    protected JpaEntityInformation<KE, ?> entityInformation;
    protected final EntityManager em;
    private final EntityPath<KE> path;
    private final PathBuilder<KE> builder;
    private final Querydsl querydsl;

    public NekaRepositoryImpl(JpaEntityInformation<KE, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityInformation = entityInformation;
        this.path              = PATH_RESOLVER.createPath(entityInformation.getJavaType());
        this.builder           = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl          = new Querydsl(em, builder);
        this.em                = em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public <T> JPAQuery<T> getJpaQuery() {
        return new JPAQuery<>(em);
    }

    @Override
    public <S extends KE> S save(S entity) {
        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    @Override
    public KE saveFlush(KE entity) {
        KE result;
        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            result = entity;
        } else {
            result = em.merge(entity);
        }
        flush();
        return result;
    }

    @Override
    public KE update(KE entity) {
        return this.save(entity);
    }

    @Override
    public KE updateFlush(KE entity) {
        return this.saveFlush(entity);
    }

    @Override
    public <S extends KE> List<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        List<S> result = new ArrayList<S>();
        for (S entity : entities) {
            result.add(this.save(entity));
        }
        return result;
    }

    @Override
    public List<KE> saveAllFlush(Iterable<KE> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        List<KE> result = new ArrayList<KE>();
        for (KE entity : entities) {
            result.add(saveFlush(entity));
        }
        return result;
    }

    @Override
    public void hardDelete(String id) {
        Assert.notNull(id, "ID null olamaz.");
        final KE entity = findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1));

        Assert.notNull(entity, "The entity must not be null!");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void hardDelete(Iterable<String> ids) {
        Assert.notNull(ids, "ID null olamaz.");
        for (String id : ids) {
            hardDelete(id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<KE> findOne(Predicate predicate) {
        try {
            return Optional.ofNullable(createQuery(predicate).select(path).fetchOne());
        } catch (NonUniqueResultException ex) {
            throw new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public NekaPage<KE> findAll(Predicate predicate, NekaQueryModel nekaQueryModel) {

        Assert.notNull(nekaQueryModel, "Pageable must not be null!");

        Pageable pageable = PageableConverter.toPageable(nekaQueryModel);

        final JPAQuery<KE> countQuery = createQuery(predicate);

        JPAQuery<KE> query = (JPAQuery<KE>) querydsl.applyPagination(pageable, countQuery);
        if (pageable.getSort().isUnsorted()) {
            return ExecutionUtils.getPage(executeSorted(query, Sort.by(Sort.Direction.DESC, NekaEntity.CREATED_FIELD)), pageable,
                                          countQuery::fetchCount);
        }
        return ExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<KE> findAllById(Iterable<String> longs) {
        return super.findAllById(longs);
    }

    @Override
    public <T> NekaPage<T> applyPagination(NekaQueryModel queryModel, JPAQuery<T> query) {
        return applyCountPagination(queryModel, query, query);
    }

    @Override
    public <T> NekaPage<T> applyPaginationWithoutSort(NekaQueryModel queryModel, JPAQuery<T> query) {
        return applyCountPaginationWithoutSort(queryModel, query, query);
    }

    @Override
    public <T> NekaPage<T> applyCountPagination(NekaQueryModel queryModel, JPAQuery<T> query, JPAQuery<?> countQuery) {
        if (PageableConverter.toPageable(queryModel).getSort().isUnsorted()) {
            querydsl.applySorting(Sort.by(Sort.Direction.DESC, NekaEntity.CREATED_FIELD), query);
        }
        return applyCountPaginationWithoutSort(queryModel, query, countQuery);
    }

    private <T> NekaPage<T> applyCountPaginationWithoutSort(NekaQueryModel queryModel, JPAQuery<T> query,
            JPAQuery<?> countQuery) {
        return ExecutionUtils.getPage(querydsl.applyPagination(PageableConverter.toPageable(queryModel), query).fetch(),
                                      PageableConverter.toPageable(queryModel), countQuery::fetchCount);
    }

    @Transactional(readOnly = true)
    @Override
    public long count(Predicate predicate) {
        return createQuery(predicate).fetchCount();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(Predicate predicate) {
        return createQuery(predicate).fetchCount() > 0;
    }

    @Override
    public void delete(KE entity) {
        boolean isExists = existsById(entity.getId());
        Assert.isTrue(isExists, "ENTITY VERITABANINDA YOK");
        entity.setDeleted(Boolean.TRUE);
        entity.setDeletedAt(Instant.now());
        this.updateFlush(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends KE> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        findAll().forEach(this::delete);
    }

    @Override
    public void deleteAllByIds(String... ids) {
        Assert.notNull(ids, "SILINECEK ENTITY ID BOS OLAMAZ"); // todo bu hatalari mesaja cekelim
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAllByIdList(Iterable<String> ids) {
        Assert.notNull(ids, "SILINECEK ENTITY ID BOS OLAMAZ");
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "SILINECEK ENTITY ID BOS OLAMAZ"); // todo bu hatalari mesaja cekelim
        Optional<KE> optional = findById(id);
        if (optional.isPresent()) {
            KE entity = optional.get();
            entity.setDeleted(Boolean.TRUE);
            entity.setDeletedAt(Instant.now());
            delete(entity);
        } else {
            throw new EmptyResultDataAccessException(
                    String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1);
        }
    }

    protected JPAQuery<KE> createQuery(Predicate... predicate) {

        DefaultQueryMetadata defaultQueryMetadata = new DefaultQueryMetadata();
        //OrderSpecifier<Date> order = new OrderSpecifier<>(Order.DESC, builder.getDate(NekaEntity.CREATED_FIELD,
        // Date.class),
        //                                                  OrderSpecifier.NullHandling.NullsLast);
        //defaultQueryMetadata.addOrderBy(order);
        defaultQueryMetadata.addWhere(
                builder.getBoolean(NekaEntity.DELETED_FIELD).isNull().or(builder.getBoolean(NekaEntity.DELETED_FIELD).isFalse()));
        JPAQuery<KE> query = new JPAQuery<>(em, defaultQueryMetadata);
        query.from(this.path);
        query.where(predicate);

        CrudMethodMetadata metadata = getRepositoryMethodMetadata();
        if (metadata == null) {
            return query;
        }
        LockModeType type = metadata.getLockModeType();
        return type == null ? query : query.setLockMode(type);
    }

    private List<KE> executeSorted(JPQLQuery<KE> query, OrderSpecifier<?>... orders) {
        return executeSorted(query, new QSort(orders));
    }

    private List<KE> executeSorted(JPQLQuery<KE> query, Sort sort) {
        if (sort.isUnsorted()) {
            Sort.by(Sort.Direction.DESC, NekaEntity.CREATED_FIELD);
        }
        return querydsl.applySorting(sort, query).fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<KE> findById(String s) {

        DefaultQueryMetadata defaultQueryMetadata = new DefaultQueryMetadata();
        //OrderSpecifier<Date> order = new OrderSpecifier<>(Order.DESC, builder.getDate(NekaEntity.CREATED_FIELD,
        // Date.class),
        //                                                  OrderSpecifier.NullHandling.NullsLast);
        //defaultQueryMetadata.addOrderBy(order);
        defaultQueryMetadata.addWhere(
                builder.getBoolean(NekaEntity.DELETED_FIELD).isNull().or(builder.getBoolean(NekaEntity.DELETED_FIELD).isFalse()));
        JPAQuery<KE> query = new JPAQuery<>(em, defaultQueryMetadata);
        query.from(this.path);
        query.where(builder.getString(NekaEntity.ID_FIELD).eq(s));
        return Optional.ofNullable(query.fetchOne());
    }

    /**
     * Returns the underlying Querydsl helper instance.
     *
     * @return
     */
    @Override
    public Querydsl getQuerydsl() {
        return this.querydsl;
    }

    @Override
    public Querydsl getRequiredQuerydsl() {

        if (querydsl == null) {
            throw new IllegalStateException("Querydsl is null!");
        }

        return querydsl;
    }


}
