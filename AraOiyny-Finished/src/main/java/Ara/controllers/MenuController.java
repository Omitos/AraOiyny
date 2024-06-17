package Ara.controllers;

import javafx.scene.Scene;
import Ara.App;
import Ara.core.data.GameData;
import Ara.core.exceptions.*;
import Ara.core.managers.GameCreatorManager;
import Ara.core.managers.GameCreatorService;
import Ara.core.managers.GameManager;
import Ara.core.managers.GameService;
import Ara.models.GameModel;
import Ara.models.MenuModel;
import Ara.views.GameView;

import java.util.List;

public class MenuController {
    private final MenuModel model;

    public MenuController(MenuModel model) {
        this.model = model;
    }

    public void startWithLetters() {
        handleStartWithLetters();
    }

    public void start() {
        handleStart();
    }

    private void handleStart() {
        GameCreatorService creatorService = new GameCreatorManager(App.getInstance().getDataFilter(), App.getInstance().getDataReader());
        try {
            GameData data = creatorService.create();
            debug(data.getWords(), data.getPangramWords(), data.getLetters());
            model.setErrorPropertyValue("");
            GameService gameService = new GameManager(data);
            GameModel gameModel = new GameModel(data.getLetters());
            GameController gameController = new GameController(gameModel, gameService);
            GameView gameView = new GameView(gameModel, gameController);
            Scene scene = new Scene(gameView.getAsParent());
            scene.getStylesheets().addAll("style.css");
            App.getInstance().setScene(scene);
        } catch (PangramNotFoundException | IllegalPointRangeException | IllegalWordCountException e) {
            handleStart();
        }

    }

    private void handleStartWithLetters() {
        GameCreatorService creatorService = new GameCreatorManager(App.getInstance().getDataFilter(), App.getInstance().getDataReader());
        try {
            GameData data = creatorService.create(model.getLettersPropertyValue().toLowerCase());
            debug(data.getWords(), data.getPangramWords(), data.getLetters());
            model.setErrorPropertyValue("");
            GameService gameService = new GameManager(data);
            GameModel gameModel = new GameModel(data.getLetters());
            GameController gameController = new GameController(gameModel, gameService);
            GameView gameView = new GameView(gameModel, gameController);
            Scene scene = new Scene(gameView.getAsParent());
            scene.getStylesheets().addAll("style.css");
            App.getInstance().setScene(scene);
        } catch (PangramNotFoundException | IllegalPointRangeException | IllegalWordCountException |
                 NotUniqueLettersException | IllegalLettersLengthException | IllegalLetterException e) {
            model.setErrorPropertyValue(e.getMessage());
        }
    }

    private void debug(List<String> words, List<String> pangrams, String letters) {
        System.out.println("Letters: " + letters);
        System.out.printf("=== Pangrams ( %d ) ===%n", pangrams.size());
        pangrams.forEach(System.out::println);
        System.out.println("================");
        System.out.printf("=== Words ( %d ) ===%n", words.size());
        words.forEach(System.out::println);
        System.out.println("=============");
    }
}
