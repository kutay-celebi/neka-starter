package tr.com.nekasoft.core.jpa.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.repository.core.EntityInformation;
import tr.com.nekasoft.core.jpa.domain.Dummy;
import tr.com.nekasoft.core.jpa.domain.DummyRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Metamodel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class NekaJpaRepositoryFactoryTest {

    private NekaJpaRepositoryFactory instance;

    @Mock
    EntityManager em;
    @Mock
    EntityManagerFactory emf;
    @Mock
    JpaEntityInformation entityInformation;
    @Mock
    Metamodel metamodel;

    @BeforeEach
    void setUp() {
        openMocks(this);
        when(em.getMetamodel()).thenReturn(metamodel);
        when(em.getEntityManagerFactory()).thenReturn(emf);
        when(em.getDelegate()).thenReturn(em);
        when(emf.createEntityManager()).thenReturn(em);

        when(entityInformation.getJavaType()).thenReturn(Dummy.class);

        instance = new NekaJpaRepositoryFactory(em) {
            @Override
            public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
                return entityInformation;
            }
        };
    }

    @Test
    void setupInstance() {
        // given

        // when
        DummyRepository actual = instance.getRepository(DummyRepository.class);

        // then
        assertThat(actual, notNullValue());

    }

}
