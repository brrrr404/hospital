package com.example.hospital;

import javafx.scene.control.PasswordField;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

@Getter
@Setter
public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonRegist;

    @FXML
    private CheckBox checkboxFemale;

    @FXML
    private CheckBox checkboxMale;

    @FXML
    private TextField fieldAdressRegistration;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fieldMiddleNameRegistration;

    @FXML
    private TextField fieldNameRegistration;

    @FXML
    private TextField fieldNumber;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldSurname;

    @FXML
    void initialize() {
        assert buttonRegist != null : "fx:id=\"buttonRegist\" was not injected: check your FXML file 'registration.fxml'.";
        assert checkboxFemale != null : "fx:id=\"checkboxFemale\" was not injected: check your FXML file 'registration.fxml'.";
        assert checkboxMale != null : "fx:id=\"checkboxMale\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldAdressRegistration != null : "fx:id=\"fieldAdressRegistration\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldLogin != null : "fx:id=\"fieldLogin\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldMiddleNameRegistration != null : "fx:id=\"fieldMiddleNameRegistration\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldNameRegistration != null : "fx:id=\"fieldNameRegistration\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldNumber != null : "fx:id=\"fieldNumber\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'registration.fxml'.";
        assert fieldSurname != null : "fx:id=\"fieldSurname\" was not injected: check your FXML file 'registration.fxml'.";

    }

}
