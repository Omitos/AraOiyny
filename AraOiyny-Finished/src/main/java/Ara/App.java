package Ara;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Ara.controllers.MenuController;
import Ara.core.data.DataFilter;
import Ara.core.data.DataReader;
import Ara.core.data.TxtReader;
import Ara.core.data.WordFilter;
import Ara.models.MenuModel;
import Ara.views.MenuView;

public class App extends Application {
    private static App instance = null;
    private Stage stage = null;
    private DataReader dataReader;
    private DataFilter dataFilter;

    @Override
    public void init() {
        instance = this;
        dataReader = new TxtReader();
        dataFilter = new WordFilter();
        dataReader.read();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        MenuModel model = new MenuModel();
        MenuController controller = new MenuController(model);
        MenuView view = new MenuView(model, controller);
        Scene scene = new Scene(view.getAsParent());
        scene.getStylesheets().addAll("style.css");

        stage.setTitle("Spelling Bee v1.0.0");
        stage.getIcons().add(new Image("logo.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static App getInstance() {
        return instance;
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }

    public DataReader getDataReader() {
        return dataReader;
    }

    public DataFilter getDataFilter() {
        return dataFilter;
    }

}