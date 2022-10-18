package tr.com.nekasoft.core.jpa.repository;

import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import tr.com.nekasoft.core.common.data.domain.NekaPage;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Kutay Celebi
 * @since 24.09.2019
 */
@NoRepositoryBean
public interface NekaRepository<KE extends NekaEntity> extends Repository<KE, String> {

    void flush();

    KE saveFlush(KE entity);

    List<KE> saveAllFlush(Collection<KE> entities);

    void hardDelete(String id);

    void hardDelete(Collection<String> ids);

    void deleteAllByIds(Collection<String> ids);

    void deleteById(String id);

    Optional<KE> findOne(Predicate predicate);

    Optional<KE> findById(String id);

    List<KE> findAll();

    NekaPage<KE> findAll(Predicate predicate, NekaQueryModel pageable);

    List<KE> findAllById(Collection<String> longs);

    long count(Predicate predicate);

    boolean exists(Predicate predicate);

    <T> NekaPage<T> applyPagination(JPAQuery<T> query, NekaQueryModel queryModel);

    <T> JPQLQuery<T> from(EntityPath<T> path);

    <T> JPAQuery<T> getJpaQuery();

    UpdateClause<JPAUpdateClause> updateClause(EntityPath<?> path);

    EntityManager getEntityManager();
}
