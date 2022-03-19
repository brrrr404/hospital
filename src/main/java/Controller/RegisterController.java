package Controller;


import com.example.hospital.BD;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;
import exception.ControllerException;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import methods.Base;

@Getter
@Setter

public class RegisterController extends Base {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonReg;

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
    private Label errorMsg;


    BD bd = new BD();

    @FXML
    void initialize() {
        errorMsg.setVisible(false);

        buttonReg.setOnAction(event -> {
            register();
        });
    }

    @SneakyThrows(value = {SQLException.class, ClassNotFoundException.class, NumberFormatException.class})
    private void register() {
        validRegister();
        String gender = validGender();
            if (bd.registNewUser(fieldNameRegistration.getText(), fieldSurname.getText(), fieldMiddleNameRegistration.getText(), fieldEmail.getText(), Integer.parseInt(fieldNumber.getText()),
                    fieldAdressRegistration.getText(), fieldLogin.getText(), fieldPassword.getText(), gender) > 0) {
               closeModalWindow(buttonReg);
            } else {
                new ControllerException("Ошибка регистрации");
            }
    }

    @SneakyThrows(value = {SQLException.class, ClassNotFoundException.class})
    private void validRegister() {
            if (!fieldNameRegistration.getText().trim().equals("") || !fieldSurname.getText().trim().equals("") || !fieldMiddleNameRegistration.getText().trim().equals("") || !fieldEmail.getText().trim().equals("") || !fieldNumber.getText().trim().equals("") || !fieldAdressRegistration.getText().trim().equals("")
                    || !fieldLogin.getText().trim().equals("") || !fieldPassword.getText().trim().equals("")) {
                if (bd.existLogin(fieldLogin.getText())) {
                    new ControllerException("Данный логин уже занят. Укажите другой.");
                }
            } else {
                new ControllerException("Не все поля заполнены");
            }

    }

    private String validGender() {
            if (checkboxFemale.isSelected() && checkboxMale.isSelected()) {
                new ControllerException("Неверно указан пол");
            } else if (checkboxMale.isSelected()) {
                return "Мужской";
            } else if (checkboxFemale.isSelected()) {
                return "Женский";
            } else {
                new ControllerException("Не указан пол");
            }

        return null;
    }

}