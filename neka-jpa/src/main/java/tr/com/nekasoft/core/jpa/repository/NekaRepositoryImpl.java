package tr.com.nekasoft.core.jpa.repository;

import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tr.com.nekasoft.core.common.data.domain.NekaPage;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;
import tr.com.nekasoft.core.jpa.util.ExecutionUtils;
import tr.com.nekasoft.core.jpa.util.PageableConverter;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Kutay Celebi
 * @since 24.09.2019
 */
public class NekaRepositoryImpl<KE extends NekaEntity> implements NekaRepository<KE> {

    private static final SimpleEntityPathResolver PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    protected JpaEntityInformation<KE, ?> entityInformation;
    protected final EntityManager em;
    private final EntityPath<KE> path;
    private final PathBuilder<?> builder;
    private final Querydsl querydsl;

    public NekaRepositoryImpl(JpaEntityInformation<KE, ?> entityInformation, EntityManager em, Class<?> domainClass) {
        Assert.notNull(entityInformation, "JpaEntityInformation must not be null!");
        Assert.notNull(em, "EntityManager must not be null!");
        this.entityInformation = entityInformation;
        this.path              = PATH_RESOLVER.createPath(entityInformation.getJavaType());
        this.builder           = new PathBuilderFactory().create(domainClass);
        this.querydsl          = new Querydsl(em, new PathBuilder<KE>(path.getType(), path.getMetadata()));
        this.em                = em;
    }

    @Transactional
    @Override
    public void flush() {
        em.flush();
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
    public List<KE> saveAllFlush(Collection<KE> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        List<KE> result = new ArrayList<>();
        for (KE entity : entities) {
            result.add(saveFlush(entity));
        }
        return result;
    }

    @Override
    public void hardDelete(String id) {
        Assert.notNull(id, "Id must not be null");
        final KE entity = findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1));

        Assert.notNull(entity, "The entity must not be null!");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void hardDelete(Collection<String> ids) {
        Assert.notNull(ids, "ID must not be null");
        for (String id : ids) {
            hardDelete(id);
        }
    }

    @Override
    public void deleteAllByIds(Collection<String> ids) {
        Assert.notNull(ids, "ID must not be null");
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "ID must not be null");
        KE entity = findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1));
        entity.setDeleted(Boolean.TRUE);
        entity.setDeletedAt(Instant.now());
        saveFlush(entity);
    }

    @Override
    public Optional<KE> findOne(Predicate predicate) {
        return Optional.ofNullable(from(path).where(predicate).select(path).fetchOne());
    }

    @Override
    public NekaPage<KE> findAll(Predicate predicate, NekaQueryModel nekaQueryModel) {
        Assert.notNull(nekaQueryModel, "QueryModel must not be null");
        return applyPagination(from(path).where(predicate), nekaQueryModel);
    }

    @Override
    public List<KE> findAllById(Collection<String> ids) {
        if (ids == null || !ids.iterator().hasNext()) {
            return Collections.emptyList();
        }
        return from(path).where(builder.getString("id").in(ids)).fetch();
    }

    @Override
    public long count(Predicate predicate) {
        return from(path).select(builder.getString("id").count()).where(predicate).fetchFirst();
    }

    @Override
    public boolean exists(Predicate predicate) {
        return count(predicate) > 0;
    }

    @Override
    public List<KE> findAll() {
        return from(path).fetch();
    }

    @Override
    public Optional<KE> findById(String id) {
        Assert.notNull(id, "ID must not be null");
        return Optional.ofNullable(from(path).where(builder.getString(NekaEntity.ID_FIELD).eq(id)).fetchOne());
    }

    @Override
    public <T> NekaPage<T> applyPagination(JPAQuery<T> query, NekaQueryModel queryModel) {
        Pageable pageable = PageableConverter.toPageable(queryModel);
        return ExecutionUtils.getPage(querydsl.applyPagination(pageable, query).fetch(), pageable,
                count(query.getMetadata().getWhere()));
    }


    @Override
    public <T> JPAQuery<T> getJpaQuery() {
        return new JPAQuery<>(em);
    }

    @Override
    public <T> JPAQuery<T> from(EntityPath<T> path) {
        return getJpaQuery().select(path).from(path);
    }

    @Override
    public UpdateClause<JPAUpdateClause> updateClause(EntityPath<?> path) {
        return new JPAUpdateClause(em, path);
    }

    @Override
    public EntityManager getEntityManager() {
        return this.em;
    }
}
