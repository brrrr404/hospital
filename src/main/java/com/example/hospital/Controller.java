package com.example.hospital;

import methods.Base;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller extends Base {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegist;

    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fieldPassword;

    @FXML
    void initialize() {
        assert buttonLogin != null : "fx:id=\"buttonLogin\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert buttonRegist != null : "fx:id=\"buttonRegist\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert fieldLogin != null : "fx:id=\"fieldLogin\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'hello-view.fxml'.";

        buttonRegist.setOnAction(event -> {
            openNewScene("registration.fxml", buttonRegist);
            });

        buttonLogin.setOnAction(actionEvent -> {
            openNewScene("main.fxml", buttonLogin);
        });
        }
    }

