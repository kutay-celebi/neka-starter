package tr.com.nekasoft.core.jpa.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import tr.com.nekasoft.core.common.data.domain.NekaPage;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author Kutay Celebi
 * @since 24.09.2019
 */
@NoRepositoryBean
public interface NekaRepository<KE extends NekaEntity> extends Repository<KE, String> {
    <T> JPAQuery<T> getJpaQuery();

    EntityManager getEntityManager();

    Optional<KE> findById(String id);

    KE saveFlush(KE entity);

    List<KE> saveAllFlush(Iterable<KE> entities);

    KE update(KE entity);

    KE updateFlush(KE entity);

    void hardDelete(String id);

    void hardDelete(Iterable<String> ids);

    Optional<KE> findOne(Predicate predicate);

    NekaPage<KE> findAll(Predicate predicate, NekaQueryModel pageable);

    <T> NekaPage<T>  applyPagination(NekaQueryModel queryModel, JPAQuery<T> query);

    <T> NekaPage<T>  applyPaginationWithoutSort(NekaQueryModel queryModel, JPAQuery<T> query);

    <T> NekaPage<T> applyCountPagination(NekaQueryModel queryModel, JPAQuery<T> query, JPAQuery<?> countQuery);

    long count(Predicate predicate);

    boolean exists(Predicate predicate);

    List<KE> findAll();

    void deleteAllByIds(String... ids);

    void deleteAllByIdList(Iterable<String> ids);

    void deleteById(String id);

    void delete(KE entity);

    Querydsl getQuerydsl();

    Querydsl getRequiredQuerydsl();
}
