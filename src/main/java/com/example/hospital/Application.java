package com.example.hospital;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/hello-view.fxml"));
        System.out.println(fxmlLoader);
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}