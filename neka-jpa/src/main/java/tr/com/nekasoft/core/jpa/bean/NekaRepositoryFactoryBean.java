package tr.com.nekasoft.core.jpa.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;

import javax.persistence.EntityManager;

/**
 * @author Kutay Celebi
 * @since 24.09.2019
 */
public class NekaRepositoryFactoryBean<R extends JpaRepository<KE, I>, KE extends NekaEntity,
        I extends Long> extends JpaRepositoryFactoryBean<R, KE, I> {

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public NekaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new NekaJpaRepositoryFactory(entityManager);
    }


}
