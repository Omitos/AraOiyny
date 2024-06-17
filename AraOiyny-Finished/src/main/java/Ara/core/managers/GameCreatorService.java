package Ara.core.managers;

import Ara.core.data.GameData;
import Ara.core.exceptions.*;

public interface GameCreatorService {
    GameData create(String letters) throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException, IllegalLettersLengthException, NotUniqueLettersException, IllegalLetterException;

    GameData create() throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException;
}
