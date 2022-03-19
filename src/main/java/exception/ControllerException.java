package exception;

import javafx.scene.control.Alert;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class ControllerException extends RuntimeException {

    String msg;

    public ControllerException(String field, String message) {
        super(message);
    }

    public ControllerException(String message) {
        alert(message);
    }

    public ControllerException() {
    }

    public void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ОШИБКА");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }




}
