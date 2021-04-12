package us.nm.state.hsd.codebreaker.service;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import us.nm.state.hsd.codebreaker.model.dao.CodeRepository;
import us.nm.state.hsd.codebreaker.model.entity.Code;

@Service
public class CodeService {

  private final CodeRepository codeRepository;
  private final UUIDStringifier stringifier;
  private final Random rng;

  @Autowired
  public CodeService(CodeRepository codeRepository, UUIDStringifier stringifier, Random rng) {
    this.codeRepository = codeRepository;
    this.stringifier = stringifier;
    this.rng = rng;
  }

  public Code add(@NonNull Code code) {
    int[] pool = code.getPool().codePoints().distinct().toArray();
    if (IntStream.of(pool).anyMatch((value) -> value <= 32)) {
      throw new IllegalArgumentException("All characters must be code point 21 or higher.");
    }
    code.setPool(new String(pool, 0, pool.length));
    int[] secret =
        IntStream.generate(() -> pool[rng.nextInt(pool.length)]).limit(code.getLength()).toArray();
    String text = new String(secret, 0,secret.length);
        code.setText(text);
    return(codeRepository.save(code));
  }

}
