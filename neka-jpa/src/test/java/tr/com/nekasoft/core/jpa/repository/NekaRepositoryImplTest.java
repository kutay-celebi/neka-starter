package tr.com.nekasoft.core.jpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import tr.com.nekasoft.core.common.data.domain.NekaPage;
import tr.com.nekasoft.core.common.data.domain.NekaQueryModel;
import tr.com.nekasoft.core.jpa.JpaConfig;
import tr.com.nekasoft.core.jpa.domain.Dummy;
import tr.com.nekasoft.core.jpa.domain.DummyRepository;
import tr.com.nekasoft.core.jpa.domain.QDummy;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = JpaConfig.class)
class NekaRepositoryImplTest {

    @Autowired
    private DummyRepository dummyRepository;

    @BeforeEach
    void setUp() {
        System.out.println(dummyRepository);
    }

    @Test
    @Transactional
    void testInitialization() {
        // given

        // when

        // then
        assertThat(dummyRepository, notNullValue());

    }

    @Test
    @Transactional
    void save() {
        // given

        // when
        Dummy saved = dummyRepository.saveFlush(Dummy.builder().dummyColumn("test").build());

        // then
        assertThat(saved.getDummyColumn(), equalTo("test"));

    }

    @Test
    @Transactional
    void updateUser() {
        // given
        Dummy saved = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());

        // when
        saved.setDummyColumn("expected");
        Dummy actual = dummyRepository.saveFlush(saved);

        // then
        assertThat(actual.getDummyColumn(), equalTo("expected"));

    }

    @Test
    @Transactional
    void saveAll() {
        // given
        Dummy dummy1 = Dummy.builder().dummyColumn("dummy1").build();
        Dummy dummy2 = Dummy.builder().dummyColumn("dummy2").build();

        // when
        List<Dummy> dummies = dummyRepository.saveAllFlush(List.of(dummy1, dummy2));

        // then
        assertThat(dummies, notNullValue());
        assertThat(dummies, hasSize(2));
    }

    @Test
    @Transactional
    void findOne() {
        // given
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual2").build());

        // when
        Optional<Dummy> actual = dummyRepository.findOne(QDummy.dummy.dummyColumn.eq("actual"));

        // then
        assertThat(actual.isPresent(), equalTo(true));
        assertThat(actual.get().getDummyColumn(), equalTo("actual"));

    }

    @Test
    @Transactional
    void findAll() {
        // given
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual2").build());

        NekaQueryModel queryModel = NekaQueryModel.builder().build();

        // when
        NekaPage<Dummy> actual = dummyRepository.findAll(QDummy.dummy.dummyColumn.eq("actual"), queryModel);

        // then
        assertThat(actual.getContent(), hasSize(1));
        assertThat(actual.getPage(), equalTo(1L));
        assertThat(actual.getTotal(), equalTo(1L));
        assertThat(actual.getTotalPages(), equalTo(1L));
    }

    @Test
    @Transactional
    void findAllByIds() {
        // given
        Dummy saved = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());
        Dummy saved2 = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual2").build());

        // when
        List<Dummy> actual = dummyRepository.findAllById(List.of(saved.getId(), saved2.getId()));

        // then
        assertThat(actual, hasSize(2));

    }

    @Test
    @Transactional
    void findAllByIdsEmptyIds() {
        // given

        // when
        List<Dummy> actual = dummyRepository.findAllById(new ArrayList<>());

        // then
        assertThat(actual, hasSize(0));

    }

    @Test
    @Transactional
    void findAllByIdsNullIds() {
        // given

        // when
        List<Dummy> actual = dummyRepository.findAllById(null);

        // then
        assertThat(actual, hasSize(0));

    }

    @Test
    @Transactional
    void findByIdsIds() {
        // given
        Dummy saved = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());

        // when
        Optional<Dummy> actual = dummyRepository.findById(saved.getId());

        // then
        assertThat(actual, notNullValue());
        assertThat(actual.isPresent(), equalTo(true));

    }

    @Test
    @Transactional
    void count() {
        // given
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual2").build());

        // when
        long actual = dummyRepository.count(QDummy.dummy.dummyColumn.eq("actual"));

        // then
        assertThat(actual, equalTo(1L));

    }

    @Test
    @Transactional
    void exists() {
        // given
        dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());

        // when
        boolean actual = dummyRepository.exists(QDummy.dummy.dummyColumn.eq("actual"));

        // then
        assertThat(actual, equalTo(true));

    }

    @Test
    @Transactional
    void hardDelete() {
        // given
        Dummy saved = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());

        // when
        dummyRepository.hardDelete(saved.getId());

        // then
        Optional<Dummy> actual = dummyRepository.findById(saved.getId());
        assertThat(actual.isPresent(), equalTo(false));
    }

    @Test
    @Transactional
    void hardDeleteNoDataFound() {
        // given

        // when
        EmptyResultDataAccessException actual = assertThrows(EmptyResultDataAccessException.class,
                () -> dummyRepository.hardDelete("wrong"));

        // then
        assertThat(actual, notNullValue());
    }

    @Test
    @Transactional
    void hardDeleteByIds() {
        // given
        Dummy saved = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual").build());
        Dummy saved2 = dummyRepository.saveFlush(Dummy.builder().dummyColumn("actual2").build());

        // when
        dummyRepository.hardDelete(List.of(saved.getId(), saved2.getId()));

        // then
        List<Dummy> actual = dummyRepository.findAllById(List.of(saved.getId(), saved2.getId()));
        assertThat(actual, empty());
    }
}
