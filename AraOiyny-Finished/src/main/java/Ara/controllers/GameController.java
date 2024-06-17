package Ara.controllers;

import javafx.scene.Scene;
import Ara.App;
import Ara.core.exceptions.*;
import Ara.core.managers.GameService;
import Ara.core.results.PointResult;
import Ara.models.GameModel;
import Ara.models.MenuModel;
import Ara.views.MenuView;

public class GameController {
    private final GameModel model;
    private final GameService gameService;

    public GameController(GameModel model, GameService gameService) {
        this.model = model;
        this.gameService = gameService;
        model.setMaximumPointPropertyValue(this.gameService.getMaximumPoint());
    }

    public void check() {
        try {
            PointResult pointResult = gameService.check(model.getWordPropertyValue());
            model.setPointPropertyValue(pointResult.getPoint());
            model.setResultWordPropertyValue(pointResult.getWord());
            model.setCurrentPointPropertyValue(pointResult.getCurrentPoint());
            model.setStatusPropertyValue(String.format("\"%s\" сөзі табылды!", pointResult.getWord()));
        } catch (DictionaryDoesNotContainWordException | IllegalWordLengthException |
                 WordContainsIllegalLetterException | WordDoesNotContainCenterLetterException |
                 WordAlreadyFoundException e) {
            model.setStatusPropertyValue(e.getMessage());
        }
    }

    public void returnMenu() {
        MenuModel model = new MenuModel();
        MenuController controller = new MenuController(model);
        MenuView view = new MenuView(model, controller);
        Scene scene = new Scene(view.getAsParent());
        scene.getStylesheets().add("style.css");
        App.getInstance().setScene(scene);
    }
}
