package com.example.hospital;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import methods.Base;

public class VievHistoryController extends Base {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> dbPatient;

    @FXML
    private TextField fioPatient;

    @FXML
    void initialize() {
        assert dbPatient != null : "fx:id=\"dbPatient\" was not injected: check your FXML file 'main.fxml'.";
        assert fioPatient != null : "fx:id=\"fioPatient\" was not injected: check your FXML file 'main.fxml'.";

    }

}
