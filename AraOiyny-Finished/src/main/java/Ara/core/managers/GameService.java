package Ara.core.managers;

import Ara.core.exceptions.*;
import Ara.core.results.PointResult;

public interface GameService {
    PointResult check(String inputWord) throws DictionaryDoesNotContainWordException, IllegalWordLengthException, WordContainsIllegalLetterException, WordDoesNotContainCenterLetterException, WordAlreadyFoundException;

    int getMaximumPoint();
}
