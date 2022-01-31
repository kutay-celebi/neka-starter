package tr.com.nekasoft.core.jpa.bean;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFragment;
import tr.com.nekasoft.core.jpa.entity.NekaEntity;
import tr.com.nekasoft.core.jpa.repository.NekaRepository;
import tr.com.nekasoft.core.jpa.repository.NekaRepositoryImpl;

import javax.persistence.EntityManager;

/**
 * @author Kutay Celebi
 * @since 27.09.2019
 */
public class NekaJpaRepositoryFactory<NE extends NekaEntity> extends JpaRepositoryFactory {

    private final SimpleEntityPathResolver pathResolver = SimpleEntityPathResolver.INSTANCE;
    private final EntityManager em;

    /**
     * Creates a new {@link JpaRepositoryFactory}.
     *
     * @param entityManager must not be {@literal null}
     */
    public NekaJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.setEntityPathResolver(pathResolver);
        this.em = entityManager;
    }

    @Override
    protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
        RepositoryComposition.RepositoryFragments fragments = RepositoryComposition.RepositoryFragments.empty();

        boolean isNekaRepo = NekaRepository.class.isAssignableFrom(metadata.getRepositoryInterface());
        if (isNekaRepo) {
            JpaEntityInformation<NE, Object> entityInformation = getEntityInformation((Class<NE>) metadata.getDomainType());
            NekaRepositoryImpl<NE> nekaRepository = new NekaRepositoryImpl<NE>(entityInformation, em);
            fragments.append(RepositoryFragment.implemented(nekaRepository));
        } else {
            throw new UnsupportedOperationException("Neka Repository interface bulunamadi");
        }
        return fragments;
    }

    @Override
    protected JpaRepositoryImplementation<?, String> getTargetRepository(RepositoryInformation information,
            EntityManager entityManager) {
        final JpaEntityInformation<NE, Object> entityInformation = getEntityInformation((Class<NE>) information.getDomainType());
        final boolean isNekaRepo = NekaRepositoryImpl.class.isAssignableFrom(information.getRepositoryBaseClass());
        if (isNekaRepo) {
            return new NekaRepositoryImpl(entityInformation, entityManager);
        } else {
            throw new UnsupportedOperationException("Neka Repository interface bulunamadi");
        }
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        final boolean isNekaRepo = NekaRepository.class.isAssignableFrom(metadata.getRepositoryInterface());
        if (isNekaRepo) {
            return NekaRepositoryImpl.class;
        } else {
            throw new UnsupportedOperationException("Neka Repository interface bulunamadi");
        }
    }
}
