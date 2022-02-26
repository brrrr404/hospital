package com.example.hospital;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import methods.Base;

public class MainMenuController extends Base {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> Time;

    @FXML
    private DatePicker dateChange;

    @FXML
    private SplitMenuButton doctor;

    @FXML
    private SplitMenuButton special;

    @FXML
    void initialize() {
        assert Time != null : "fx:id=\"Time\" was not injected: check your FXML file 'main.fxml'.";
        assert dateChange != null : "fx:id=\"dateChange\" was not injected: check your FXML file 'main.fxml'.";
        assert doctor != null : "fx:id=\"doctor\" was not injected: check your FXML file 'main.fxml'.";
        assert special != null : "fx:id=\"special\" was not injected: check your FXML file 'main.fxml'.";

    }

}
