package Controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Doctor;
import Entity.Treatment;
import com.example.hospital.BD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import methods.Base;

public class ViewHistoryController extends Base {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Treatment, String> commentColumn;

    @FXML
    private TableColumn<Treatment, Date> dateTimeColumn;

    @FXML
    private TableView<Treatment> dbPatient;

    @FXML
    private TableColumn<Treatment, String> divisionColumn;

    @FXML
    private TableColumn<Treatment, String> doctorColumn;

    @FXML
    private TextField fioPatient;

    @FXML
    private Button searchButton;

    @FXML
    private Button job;

    @FXML
    private Label doctorName;

    BD bd = new BD();

    @FXML
    void initialize() {

        Doctor doctor = bd.getDoctor(BD.user.getID());

        doctorName.setText("Здравствуйте, " + doctor.getName());

        dbPatient.setEditable(true);
        searchButton.setOnAction(actionEvent -> {
            getName();
        });

        job.setDisable(true);

        if (doctor.getAppointment().equals("Заведующий(-ая) отделением")) {
            job.setDisable(false);
        }

        job.setOnAction(event -> {
            openModalWindow("modalWindowDoctor.fxml");
        });
    }




    private void getName() {
        List<Treatment> treatments = bd.getHistory(fioPatient.getText());

        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("nameDepartment"));

        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("fioDoctor"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        commentColumn.setCellFactory(TextFieldTableCell.<Treatment>forTableColumn());
        commentColumn.setMinWidth(200);
        commentColumn.setOnEditCommit((TableColumn.CellEditEvent<Treatment, String> event) -> {
            TablePosition<Treatment, String> pos = event.getTablePosition();

            String newCommment = event.getNewValue();

            int row = pos.getRow();
            Treatment treatment = event.getTableView().getItems().get(row);

            treatment.setComment(newCommment);

            bd.addComment(treatment);
        });

        getTreatmentList(treatments);
    }


    private void getTreatmentList(List<Treatment> treatments) {

        ObservableList<Treatment> list = FXCollections.observableArrayList();

        list.addAll(treatments);
        dbPatient.setItems(list);
    }
}
