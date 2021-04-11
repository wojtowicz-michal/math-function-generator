package com.mw.mathfunctiongenerator.handler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.mw.mathfunctiongenerator.util.Constants.ICON_PATH;
import static com.mw.mathfunctiongenerator.util.Constants.ROOT_PATH;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML());
        stage.setScene(scene);
        stage.getIcons().add(new Image(ICON_PATH));
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
        stage.setMaxHeight(stage.getHeight());
    }

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(ROOT_PATH));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
