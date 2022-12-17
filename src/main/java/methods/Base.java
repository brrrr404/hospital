package methods;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Base {
    public void openNewScene(String window, Button butName, String nameStage) {
        butName.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/" + window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage primaryStage = new Stage();
        primaryStage.setTitle(nameStage);
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.showAndWait();
    }

    public void openModalWindow(String window, String nameStage) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/" + window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage modalStage = new Stage();
        Stage thisStage = new Stage();
        Scene secondScene = new Scene(root, 546, 289);

        modalStage.setTitle(nameStage);
        modalStage.setScene(secondScene);

        modalStage.initModality(Modality.WINDOW_MODAL);

        modalStage.initOwner(thisStage);

        modalStage.showAndWait();
    }

    public void openModalWindow(String window, int widht, int height, String nameStage) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/" + window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage modalStage = new Stage();
        Stage thisStage = new Stage();
        Scene secondScene = new Scene(root, widht, height);

        modalStage.setTitle(nameStage);
        modalStage.setScene(secondScene);

        modalStage.initModality(Modality.WINDOW_MODAL);

        modalStage.initOwner(thisStage);

        modalStage.showAndWait();
    }

    public void closeModalWindow(Button butName) {
        butName.getScene().getWindow().hide();
    }
}
