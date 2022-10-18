package tr.com.nekasoft.core.jpa.bean;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;
import tr.com.nekasoft.core.jpa.repository.NekaRepository;
import tr.com.nekasoft.core.jpa.repository.NekaRepositoryImpl;

import javax.persistence.EntityManager;

public class NekaJpaRepositoryFactory extends RepositoryFactorySupport {

    private final EntityManager em;

    public NekaJpaRepositoryFactory(EntityManager em) {
        this.em = em;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(domainClass, em);
    }

    @Override
    protected NekaRepository<?> getTargetRepository(RepositoryInformation information) {
        EntityInformation<?, Object> entityInformation = getEntityInformation(information.getDomainType());
        NekaRepositoryImpl<?> repository = getTargetRepositoryViaReflection(information, entityInformation, em,
                information.getDomainType());

        Assert.isInstanceOf(NekaRepository.class, repository);

        return repository;
    }



    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return NekaRepositoryImpl.class;
    }
}
