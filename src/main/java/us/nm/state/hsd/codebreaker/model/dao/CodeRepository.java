package us.nm.state.hsd.codebreaker.model.dao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.nm.state.hsd.codebreaker.model.entity.Code;

public interface CodeRepository extends JpaRepository<Code, UUID> {
  
  Iterable<Code> getAllByOrderByCreatedDesc();
  
  @Query("SELECT DISTINCT c FROM Guess AS g JOIN g.code AS c WHERE c.length = g.exactMatches ORDER BY c.created DESC")
  Iterable<Code>getAllBySolvedOrderByCreatedDescIterable();
  
  
}
