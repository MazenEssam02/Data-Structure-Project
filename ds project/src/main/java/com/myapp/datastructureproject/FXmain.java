package com.myapp.datastructureproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXmain extends Application {
    private volatile boolean running;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXmain.class.getResource("main_scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("XML Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
