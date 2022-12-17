package Controller;

import Entity.Doctor;
import Entity.Patient;
import Entity.Roles;
import com.example.hospital.BD;
import exception.ControllerException;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import lombok.SneakyThrows;
import methods.Base;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import methods.User;

public class MainMenuController extends Base {

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
    private PasswordField fieldPassword;

    @FXML
    private Label errorLogin;

    BD bd = new BD();

    Roles role = new Roles();

    @FXML
    void initialize() {

        errorLogin.setVisible(false);
        buttonRegist.setOnAction(event -> {
            openModalWindow("registration.fxml", 700, 400, "Регистрация");
        });

        buttonLogin.setOnAction(event -> {
            authentication();
        });
    }

    //авторизация
    @SneakyThrows
    public Roles authentication() {
        if (!fieldLogin.getText().trim().equals("") || !fieldPassword.getText().trim().equals("")) {
            User user = bd.auth(fieldLogin.getText(), fieldPassword.getText());
            if (user.getClass().equals(Patient.class)) {
                openNewScene("main.fxml", buttonLogin, "Запись к врачу");
            } else if (user.getClass().equals(Doctor.class)) {
                openNewScene("doctor-main.fxml", buttonLogin, "Электронная медицинская карта");
            } else {
                new ControllerException("Введенные данные неверны");
            }
        } else {
            new ControllerException("Заполнены не все поля!");
        }

        role.setLogin(fieldLogin.getText());
        return role;
    }
}