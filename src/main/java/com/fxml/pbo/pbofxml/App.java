package com.fxml.pbo.pbofxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        Scene scene = new Scene(fxmlLoader.load(), 650, 420);
        stage.setTitle("Minim Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}