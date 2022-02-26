package methods;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Base {
    public void openNewScene(String window, Button butName)
    {
        butName.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        System.out.println(window);

        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Diary");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.showAndWait();
    }
}
