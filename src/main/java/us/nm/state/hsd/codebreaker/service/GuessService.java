package us.nm.state.hsd.codebreaker.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import us.nm.state.hsd.codebreaker.model.dao.GuessRepository;
import us.nm.state.hsd.codebreaker.model.entity.Code;
import us.nm.state.hsd.codebreaker.model.entity.Guess;

@Service
public class GuessService {

  private final GuessRepository guessRepository;
  private final UUIDStringifier stringifier;

  public GuessService(GuessRepository guessRepository, UUIDStringifier stringifier) {
    this.guessRepository = guessRepository;
    this.stringifier = stringifier;
  }

  public Guess add(@NonNull Code code, @NonNull Guess guess) {
    int numCorrect = 0;
    int numClose = 0;
    int[] codeCodePoints = code.codePoints();
    int[] guessCodePoints = guess.codePoints();
    for (int i = 0; i < guessCodePoints.length; i++) {
      if (guessCodePoints[i] == codeCodePoints[i]) {
        numCorrect++;
        codeCodePoints[i] = guessCodePoints[i] = 0;
      }
    }
    for (int codePoint : guessCodePoints) {
      if (codePoint != 0) {
        for (int i = 0; i < codeCodePoints.length; i++) {
          if (codePoint == codeCodePoints[i]) {
            numClose++;
            codeCodePoints[i] = 0;
            break;
          }
        }
      }
    }
    
    guess.setExactMatches(numCorrect);
    guess.setNearMatches(numClose);
    guess.setCode(code);
    return guessRepository.save(guess);
  }

}
