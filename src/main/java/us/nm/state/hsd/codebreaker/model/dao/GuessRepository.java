package us.nm.state.hsd.codebreaker.model.dao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import us.nm.state.hsd.codebreaker.model.entity.Guess;

public interface GuessRepository extends JpaRepository<Guess, UUID> {

}
