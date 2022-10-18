package tr.com.nekasoft.core.jpa.domain;

import org.springframework.stereotype.Repository;
import tr.com.nekasoft.core.jpa.repository.NekaRepository;

@Repository
public interface DummyRepository extends NekaRepository<Dummy> {
}
