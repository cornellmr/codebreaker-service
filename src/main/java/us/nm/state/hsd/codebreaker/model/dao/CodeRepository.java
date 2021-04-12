package us.nm.state.hsd.codebreaker.model.dao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import us.nm.state.hsd.codebreaker.model.entity.Code;

public interface CodeRepository extends JpaRepository<Code, UUID> {
  
  Iterable<Code> getAllByOrderByCreatedDesc();
  
  
}
